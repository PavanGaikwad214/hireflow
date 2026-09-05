package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.candidate.dto.ResumeRequest;
import com.pavan.hireflow.candidate.dto.ResumeResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.entity.Resume;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.candidate.repository.ResumeRepository;
import com.pavan.hireflow.common.exception.CandidateProfileNotFoundException;
import com.pavan.hireflow.common.exception.ResumeNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public ResumeService(ResumeRepository resumeRepository, CandidateProfileRepository candidateProfileRepository) {
        this.resumeRepository = resumeRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public ResumeResponse addResume(Long userId, ResumeRequest resumeRequest){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        if(resumeRequest.isPrimaryResume()){
            resumeRepository.findByCandidateProfile_IdAndPrimaryResumeTrue(candidateProfile.getId())
                    .ifPresent(existingPrimary ->{
                        existingPrimary.setPrimaryResume(false);
                        existingPrimary.setUpdatedAt(LocalDateTime.now());
                        resumeRepository.save(existingPrimary);
                    });
        }

        Resume resume = new Resume();

        resume.setCandidateProfile(candidateProfile);
        resume.setTitle(resumeRequest.getTitle());
        resume.setFileUrl(resumeRequest.getFileUrl());
        resume.setPrimaryResume(resumeRequest.isPrimaryResume());

        LocalDateTime now = LocalDateTime.now();

        resume.setCreatedAt(now);
        resume.setUpdatedAt(now);

        Resume response = resumeRepository.save(resume);

        return mapToResponse(response);
    }

    public List<ResumeResponse> getResumes(Long userId) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        return resumeRepository.findByCandidateProfile_Id(candidateProfile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ResumeResponse updateResume(Long userId, Long resumeId, ResumeRequest resumeRequest){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Resume resume = resumeRepository
                .findByIdAndCandidateProfile_Id(resumeId, candidateProfile.getId())
                .orElseThrow(() -> new ResumeNotFoundException("Resume Not Found"));

        if(resumeRequest.isPrimaryResume()){
            resumeRepository.findByCandidateProfile_IdAndPrimaryResumeTrue(candidateProfile.getId())
                    .ifPresent(existingPrimary ->{
                        if(!existingPrimary.getId().equals(resume.getId())){
                            existingPrimary.setPrimaryResume(false);
                            existingPrimary.setUpdatedAt(LocalDateTime.now());
                            resumeRepository.save(existingPrimary);
                        }
                    });
        }

        resume.setTitle(resumeRequest.getTitle());
        resume.setFileUrl(resumeRequest.getFileUrl());
        resume.setPrimaryResume(resumeRequest.isPrimaryResume());
        resume.setUpdatedAt(LocalDateTime.now());

        Resume updatedResume = resumeRepository.save(resume);
        return mapToResponse(updatedResume);
    }

    public void deleteResume(Long userId, Long resumeId){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Resume resume = resumeRepository.findByIdAndCandidateProfile_Id(resumeId, candidateProfile.getId())
                .orElseThrow(() -> new ResumeNotFoundException("Resume Not Found"));

        resumeRepository.delete(resume);
    }

    private ResumeResponse mapToResponse(Resume resume){

        return new ResumeResponse(
                resume.getId(),
                resume.getTitle(),
                resume.getFileUrl(),
                resume.isPrimaryResume(),
                resume.getCreatedAt(),
                resume.getUpdatedAt()
        );
    }

}
