package com.pavan.hireflow.application.service;

import com.pavan.hireflow.application.dto.ApplicationResponse;
import com.pavan.hireflow.application.dto.ApplicationStatusUpdateRequest;
import com.pavan.hireflow.application.entity.Application;
import com.pavan.hireflow.application.repository.ApplicationRepository;
import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.CandidateProfileResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.entity.Resume;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.candidate.repository.ResumeRepository;
import com.pavan.hireflow.common.enums.ApplicationStatus;
import com.pavan.hireflow.common.enums.JobStatus;
import com.pavan.hireflow.common.exception.*;
import com.pavan.hireflow.company.entity.RecruiterProfile;
import com.pavan.hireflow.company.repository.RecruiterProfileRepository;
import com.pavan.hireflow.job.entity.Job;
import com.pavan.hireflow.job.repository.JobRepository;
import com.pavan.hireflow.notification.serivce.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobRepository jobRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final ResumeRepository resumeRepository;
    private final NotificationService notificationService;

    public ApplicationService(ApplicationRepository applicationRepository, CandidateProfileRepository candidateProfileRepository, JobRepository jobRepository, RecruiterProfileRepository recruiterProfileRepository, ResumeRepository resumeRepository, NotificationService notificationService) {
        this.applicationRepository = applicationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.jobRepository = jobRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.resumeRepository = resumeRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public ApplicationResponse applyForJob(Long jobId, Long resumeId, User user){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Resume resume = resumeRepository
                .findByIdAndCandidateProfile_Id(resumeId, candidateProfile.getId())
                .orElseThrow(() -> new ResumeNotFoundException("Resume Not Found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job Not Found"));

        if(job.getStatus() != JobStatus.OPEN){
            throw new JobClosedException("Cannot apply to a closed job");
        }

        if(applicationRepository.existsByCandidate_IdAndJob_Id(
                candidateProfile.getId(),
                job.getId()
                )){
            throw new ApplicationAlreadyExistsException("Cannot apply to an application that has already been applied");
        }

        Application application = new Application();

        application.setCandidate(candidateProfile);
        application.setJob(job);
        application.setResume(resume);

        application.setStatus(ApplicationStatus.PENDING);

        LocalDateTime now = LocalDateTime.now();

        application.setAppliedAt(now);
        application.setUpdatedAt(now);

        Application savedApplication = applicationRepository.save(application);

        notificationService.createNotification(
                candidateProfile.getUser(),
                "Application Submitted",
                "Your application for " + job.getTitle() + " has been submitted successfully."
        );

        notificationService.createNotification(
                job.getRecruiter().getUser(),
                "New Application Received",
                candidateProfile.getUser().getFirstName()
                        + " " + candidateProfile.getUser().getLastName()
                        + " has applied for your job: "
                        + job.getTitle()
                        + "."

        );

        return mapToResponse(savedApplication);
    }

    public List<ApplicationResponse> getMyApplication(User user){

        CandidateProfile candidate = candidateProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        return applicationRepository
                .findByCandidate_Id(candidate.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ApplicationResponse withdrawApplication(Long applicationId, User user){

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found"));

        CandidateProfile candidate = candidateProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        if(!application.getCandidate().getId().equals(candidate.getId())){
            throw new JobAccessDeniedException("You are not allowed to withdraw this application");
        }

        ApplicationStatus currentStatus = application.getStatus();

        if(currentStatus == ApplicationStatus.HIRED ||
           currentStatus == ApplicationStatus.REJECTED ||
           currentStatus == ApplicationStatus.WITHDRAWN){

            throw new InvalidApplicationStatusTransitionException(
                    "Cannot withdraw this application with status " + currentStatus
            );
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setUpdatedAt(LocalDateTime.now());

        Application savedApplication = applicationRepository.save(application);

//        System.out.println(
//                "WITHDRAWAL NOTIFICATION → User ID: "
//                        + application.getJob().getRecruiter().getUser().getId()
//        );

        notificationService.createNotification(
                application.getJob().getRecruiter().getUser(),
                "Application Withdrawn",
                application.getCandidate().getUser().getFirstName()
                    + " " + application.getCandidate().getUser().getLastName()
                      + " has withdrawn their application for "
                        + application.getJob().getTitle()
                        + "."
        );

        return mapToResponse(savedApplication);
    }

    public List<ApplicationResponse> getRecruiterApplication(User user){

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));

        return applicationRepository
                .findByJob_Recruiter_Id(recruiter.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ApplicationResponse updateApplicationStatus(
            Long application_Id,
            ApplicationStatusUpdateRequest request,
            User user
    ){

        Application application = applicationRepository.findById(application_Id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found"));

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new  RecruiterProfileNotFoundException("Recruiter Profile Not Found"));

        if(!application.getJob().getRecruiter().getId().equals(recruiter.getId())){
            throw new JobAccessDeniedException("You are not allowed to manage this application");
        }

        ApplicationStatus currentStatus = application.getStatus();
        ApplicationStatus newStatus = request.getStatus();

        if(currentStatus == ApplicationStatus.PENDING){

            if(newStatus != ApplicationStatus.UNDER_REVIEW &&
                    newStatus != ApplicationStatus.REJECTED){
                throw new InvalidApplicationStatusTransitionException("Invalid status transition from PENDING");
            }

        } else if(currentStatus == ApplicationStatus.UNDER_REVIEW){

            if(newStatus != ApplicationStatus.SHORTLISTED &&
                    newStatus != ApplicationStatus.REJECTED){
                throw new InvalidApplicationStatusTransitionException("Invalid status transition from UNDER_REVIEW");
            }

        } else if(currentStatus == ApplicationStatus.SHORTLISTED){

            if(newStatus != ApplicationStatus.INTERVIEW_SCHEDULED &&
                    newStatus != ApplicationStatus.REJECTED){
                throw new InvalidApplicationStatusTransitionException("Invalid status transition from SHORTLISTED");
            }
        } else if(currentStatus == ApplicationStatus.INTERVIEW_SCHEDULED){

            if(newStatus != ApplicationStatus.HIRED &&
                    newStatus != ApplicationStatus.REJECTED){
                throw new InvalidApplicationStatusTransitionException("Invalid status transition from INTERVIEW_SCHEDULED");
            }
        } else {
            throw new InvalidApplicationStatusTransitionException(
                    "Invalid status transition from " + currentStatus + " to " + newStatus
            );
        }

        application.setStatus(newStatus);
        application.setUpdatedAt(LocalDateTime.now());

        if(request.getStatus() == ApplicationStatus.UNDER_REVIEW) {
            notificationService.createNotification(
                    application.getCandidate().getUser(),
                    "Application Under Review",
                    "Your application for " + application.getJob().getTitle()
                            + " is under review."
            );
        }

        if(request.getStatus() == ApplicationStatus.SHORTLISTED) {
            notificationService.createNotification(
                    application.getCandidate().getUser(),
                    "Application Shortlisted",
                    "Your application for " + application.getJob().getTitle()
                                 + " is shortlisted."
            );
        }

        if(request.getStatus() == ApplicationStatus.HIRED){
            notificationService.createNotification(
                    application.getCandidate().getUser(),
                    "Application Hired",
                    "Congratulations!! Your application for " + application.getJob().getTitle()
                              + " has been selected."
            );
        }

        if(request.getStatus() == ApplicationStatus.REJECTED){
            notificationService.createNotification(
                    application.getCandidate().getUser(),
                    "Application Rejected",
                    "Your application for " + application.getJob().getTitle()
                               + " has been rejected."
            );
        }

        Application updatedApplication = applicationRepository.save(application);

        return mapToResponse(updatedApplication);
    }

    public CandidateProfileResponse getApplicantProfile(
            Long applicationId,
            User user
    ){
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found"));

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));

        if(!application.getJob().getRecruiter().getId().equals(recruiter.getId())){
            throw new JobAccessDeniedException("You are not allowed to view this candidate profile");
        }

        CandidateProfile candidate = application.getCandidate();

        return new  CandidateProfileResponse(
                candidate.getId(),
                candidate.getSummary(),
                candidate.getHeadline(),
                candidate.getResumeUrl(),
                candidate.getGithubUrl(),
                candidate.getLinkedinUrl(),
                candidate.getPortfolioUrl()
        );
    }

    private ApplicationResponse mapToResponse(Application application){

        CandidateProfile candidate = application.getCandidate();
        Job job = application.getJob();

        String candidateName = candidate.getUser().getFirstName();

        if (candidate.getUser().getLastName() != null &&
                !candidate.getUser().getLastName().isBlank()) {
            candidateName += " " + candidate.getUser().getLastName();
        }

        Long resumeId = null;
        String resumeTitle = null;

        if (application.getResume() != null) {
            resumeId = application.getResume().getId();
            resumeTitle = application.getResume().getTitle();
        }

        return new ApplicationResponse(
                application.getId(),
                candidate.getId(),
                candidateName,
                job.getId(),
                job.getTitle(),
                resumeId,
                resumeTitle,
                job.getCompany().getId(),
                job.getCompany().getName(),
                application.getStatus(),
                application.getAppliedAt(),
                application.getUpdatedAt()
        );

    }
}
