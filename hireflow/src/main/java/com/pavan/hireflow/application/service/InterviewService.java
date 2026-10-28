package com.pavan.hireflow.application.service;

import com.pavan.hireflow.application.dto.ApplicationStatusUpdateRequest;
import com.pavan.hireflow.application.dto.InterviewRequest;
import com.pavan.hireflow.application.dto.InterviewResponse;
import com.pavan.hireflow.application.entity.Application;
import com.pavan.hireflow.application.entity.Interview;
import com.pavan.hireflow.application.repository.ApplicationRepository;
import com.pavan.hireflow.application.repository.InterviewRepository;
import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.common.enums.ApplicationStatus;
import com.pavan.hireflow.common.exception.*;
import com.pavan.hireflow.company.entity.RecruiterProfile;
import com.pavan.hireflow.company.repository.RecruiterProfileRepository;
import com.pavan.hireflow.notification.serivce.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final ApplicationService applicationService;
    private final CandidateProfileRepository candidateProfileRepository;
    private final NotificationService notificationService;

    public InterviewService(InterviewRepository interviewRepository, ApplicationRepository applicationRepository, RecruiterProfileRepository recruiterProfileRepository, ApplicationService applicationService, CandidateProfileRepository candidateProfileRepository, NotificationService notificationService) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.applicationService = applicationService;
        this.candidateProfileRepository = candidateProfileRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public InterviewResponse scheduleInterview(
            User user,
            Long applicationId,
            InterviewRequest request
    ) {

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found"));

        if (!application.getJob()
                .getRecruiter()
                .getId()
                .equals(recruiter.getId())
        ) {
            throw new JobAccessDeniedException("You are not allowed to schedule an interview for this application");
        }

        if(interviewRepository.findByApplication_Id(applicationId).isPresent()){

            throw new InterviewAlreadyExistsException("Interview already scheduled for this application");
        }


        Interview interview = new Interview();

        interview.setApplication(application);
        interview.setScheduledAt(request.getScheduledAt());
        interview.setDurationMinutes(request.getDurationMinutes());
        interview.setMeetingLink(request.getMeetingLink());
        interview.setInstructions(request.getInstructions());

        LocalDateTime now = LocalDateTime.now();

        interview.setCreatedAt(now);
        interview.setUpdatedAt(now);

        Interview savedInterview = interviewRepository.save(interview);

        notificationService.createNotification(
                application.getCandidate().getUser(),
                "Interview Scheduled",
                "Your application for " + application.getJob().getTitle()
                          + " has been scheduled for " + request.getDurationMinutes() + " minutes."
        );

        ApplicationStatusUpdateRequest statusRequest = new ApplicationStatusUpdateRequest();
        statusRequest.setStatus(ApplicationStatus.INTERVIEW_SCHEDULED);

        applicationService.updateApplicationStatus(
                applicationId,
                statusRequest,
                user
        );

        return mapToResponse(savedInterview);
    }

    public InterviewResponse getCandidateInterview(
            Long userId,
            Long applicationId
    ){
        CandidateProfile candidate = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found"));

        if(!application.getCandidate()
                .getId()
                .equals(candidate.getId())
        ){
            throw new JobAccessDeniedException("You are not allowed to view this interview");
        }

        Interview interview = interviewRepository.findByApplication_Id(applicationId)
                .orElseThrow(() -> new InterviewNotFoundException("Interview Not Found"));

        return mapToResponse(interview);
    }

    private InterviewResponse mapToResponse(Interview interview) {

        Application application = interview.getApplication();

        String candidateName = application.getCandidate().getUser().getFirstName();

        if(application.getCandidate().getUser().getLastName() != null &&
        !application.getCandidate().getUser().getLastName().isBlank()){

            candidateName+=" "+application.getCandidate().getUser().getLastName();
        }

        return new InterviewResponse(
                interview.getId(),
                application.getId(),
                application.getCandidate().getId(),
                candidateName,
                application.getJob().getId(),
                application.getJob().getTitle(),
                interview.getScheduledAt(),
                interview.getDurationMinutes(),
                interview.getMeetingLink(),
                interview.getInstructions(),
                interview.getCreatedAt(),
                interview.getUpdatedAt()
        );
    }
}
