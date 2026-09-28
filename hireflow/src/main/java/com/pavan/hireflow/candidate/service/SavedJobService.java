package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.candidate.dto.SavedJobRequest;
import com.pavan.hireflow.candidate.dto.SavedJobResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.entity.SavedJob;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.candidate.repository.SavedJobRepository;
import com.pavan.hireflow.common.exception.*;
import com.pavan.hireflow.job.entity.Job;
import com.pavan.hireflow.job.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobRepository jobRepository;

    public SavedJobService(SavedJobRepository savedJobRepository, CandidateProfileRepository candidateProfileRepository, JobRepository jobRepository) {
        this.savedJobRepository = savedJobRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.jobRepository = jobRepository;
    }

    public SavedJobResponse saveJob(Long userId, SavedJobRequest request) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CertificationNotFoundException("Candidate Profile Not Found"));


        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new JobNotFoundException("Job Not Found"));

        if (savedJobRepository
                .findByCandidateProfile_IdAndJob_Id(
                        candidateProfile.getId(),
                        job.getId()
                )
                .isPresent()){
            throw new JobAlreadySavedException("Job Already Saved");
        }

        SavedJob savedJob = new SavedJob();

        savedJob.setCandidateProfile(candidateProfile);
        savedJob.setJob(job);
        savedJob.setSavedAt(LocalDateTime.now());

        SavedJob saved = savedJobRepository.save(savedJob);

        return mapToResponse(saved);
    }

    public List<SavedJobResponse> getSavedJobs(Long userId) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CertificationNotFoundException("Candidate Profile Not Found"));

        return savedJobRepository
                .findByCandidateProfile_Id(candidateProfile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void removeSavedJob(Long userId, Long savedJobId) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

//        System.out.println("savedJob Id: " + savedJobId);
//        System.out.println("Candidate Profile Id: " + candidateProfile.getId());

        SavedJob savedJob = savedJobRepository
                .findByIdAndCandidateProfile_Id(savedJobId, candidateProfile.getId())
                .orElseThrow(() -> new SavedJobNotFoundException("SavedJob Not Found"));

        savedJobRepository.delete(savedJob);
    }

    private SavedJobResponse mapToResponse(SavedJob savedJob){

        Job job = savedJob.getJob();

        return new SavedJobResponse(
                savedJob.getId(),
                job.getId(),
                job.getTitle(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                savedJob.getSavedAt()
        );
    }
}
