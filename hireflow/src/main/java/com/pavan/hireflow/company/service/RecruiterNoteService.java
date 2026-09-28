package com.pavan.hireflow.company.service;

import com.pavan.hireflow.application.entity.Application;
import com.pavan.hireflow.application.repository.ApplicationRepository;
import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.common.exception.ApplicationNotFoundException;
import com.pavan.hireflow.common.exception.JobAccessDeniedException;
import com.pavan.hireflow.common.exception.RecruiterNoteNotFoundException;
import com.pavan.hireflow.common.exception.RecruiterProfileNotFoundException;
import com.pavan.hireflow.company.dto.RecruiterNoteRequest;
import com.pavan.hireflow.company.dto.RecruiterNoteResponse;
import com.pavan.hireflow.company.entity.RecruiterNote;
import com.pavan.hireflow.company.entity.RecruiterProfile;
import com.pavan.hireflow.company.repository.RecruiterNoteRepository;
import com.pavan.hireflow.company.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecruiterNoteService {

    private final RecruiterNoteRepository recruiterNoteRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final ApplicationRepository applicationRepository;

    public RecruiterNoteService(RecruiterNoteRepository recruiterNoteRepository, RecruiterProfileRepository recruiterProfileRepository, ApplicationRepository applicationRepository) {
        this.recruiterNoteRepository = recruiterNoteRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.applicationRepository = applicationRepository;
    }

    public RecruiterNoteResponse addNote(
            Long userId,
            Long applicationId,
            RecruiterNoteRequest recruiterNoteRequest){

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found"));

        if(!application.getJob()
                .getRecruiter()
                .getId()
                .equals(recruiter.getId())){
            throw new JobAccessDeniedException("You are not allowed to add a note to this application");
        }

        RecruiterNote recruiterNote = new RecruiterNote();

        recruiterNote.setApplication(application);
        recruiterNote.setRecruiter(recruiter);
        recruiterNote.setNote(recruiterNoteRequest.getNote());

        LocalDateTime now = LocalDateTime.now();

        recruiterNote.setCreatedAt(now);
        recruiterNote.setUpdatedAt(now);

        RecruiterNote savedNote  = recruiterNoteRepository.save(recruiterNote);

        return mapToResponse(savedNote);

    }

    public List<RecruiterNoteResponse> getNotes(Long userId, Long applicationId){

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found"));


        if(!application.getJob()
                .getRecruiter()
                .getId()
                .equals(recruiter.getId())
        ){
            throw new JobAccessDeniedException("You are not allowed to view a note of this application");
        }


        return recruiterNoteRepository
                .findByApplication_Id(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RecruiterNoteResponse updateNotes(
            Long userId,
            Long noteId,
            RecruiterNoteRequest recruiterNoteRequest
    ){

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));

        RecruiterNote recruiterNote = recruiterNoteRepository
                .findByIdAndRecruiter_Id(noteId, recruiter.getId())
                .orElseThrow(() -> new RecruiterNoteNotFoundException("Recruiter Note Not Found"));

        recruiterNote.setNote(recruiterNoteRequest.getNote());
        recruiterNote.setUpdatedAt(LocalDateTime.now());

        RecruiterNote updatedNote = recruiterNoteRepository.save(recruiterNote);
        return mapToResponse(updatedNote);
    }

    public void deleteNote(Long userId, Long noteId){

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));

        RecruiterNote note = recruiterNoteRepository
                .findByIdAndRecruiter_Id(noteId, recruiter.getId())
                .orElseThrow(() -> new RecruiterNoteNotFoundException("Recruiter Note Not Found"));

        recruiterNoteRepository.delete(note);
    }

    private RecruiterNoteResponse mapToResponse(RecruiterNote recruiterNote){

        RecruiterProfile recruiter = recruiterNote.getRecruiter();

        User user = recruiter.getUser();

        String recruiterName = user.getFirstName();

        if(user.getLastName() != null &&
                !user.getLastName().isBlank()
        ){
            recruiterName+= " " +user.getLastName();
        }

        return new RecruiterNoteResponse(
                recruiterNote.getId(),
                recruiterNote.getApplication().getId(),
                recruiter.getId(),
                recruiterName,
                recruiterNote.getNote(),
                recruiterNote.getCreatedAt(),
                recruiterNote.getUpdatedAt()
        );
    }
}
