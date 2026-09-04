package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.candidate.dto.ExperienceRequest;
import com.pavan.hireflow.candidate.dto.ExperienceResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.entity.Experience;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.candidate.repository.ExperienceRepository;
import com.pavan.hireflow.common.exception.CandidateProfileNotFoundException;
import com.pavan.hireflow.common.exception.ExperienceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public ExperienceService(ExperienceRepository experienceRepository, CandidateProfileRepository candidateProfileRepository) {
        this.experienceRepository = experienceRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public ExperienceResponse addExperience(Long userId, ExperienceRequest request){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Experience experience = new Experience();

        experience.setCandidateProfile(candidateProfile);
        experience.setJobTitle(request.getJobTitle());
        experience.setCompanyName(request.getCompanyName());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrentlyWorking(request.isCurrentlyWorking());
        experience.setDescription(request.getDescription());

        Experience savedExperience = experienceRepository.save(experience);

        return mapToResponse(savedExperience);
    }

    public List<ExperienceResponse> getMyExperiences(Long userId) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        return experienceRepository
                .findByCandidateProfile_Id(candidateProfile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ExperienceResponse updateExperience(Long userId, Long experienceId, ExperienceRequest request){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Experience experience = experienceRepository
                .findByIdAndCandidateProfile_Id(experienceId, candidateProfile.getId())
                .orElseThrow(() -> new ExperienceNotFoundException("Experience Not Found"));

        experience.setJobTitle(request.getJobTitle());
        experience.setCompanyName(request.getCompanyName());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrentlyWorking(request.isCurrentlyWorking());
        experience.setDescription(request.getDescription());

        Experience savedExperience = experienceRepository.save(experience);

        return mapToResponse(savedExperience);
    }

    public void deleteExperience(Long userId, Long experienceId){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Experience experience = experienceRepository
                .findByIdAndCandidateProfile_Id(experienceId, candidateProfile.getId())
                .orElseThrow(() -> new ExperienceNotFoundException("Experience Not Found"));

        experienceRepository.delete(experience);
    }

    private ExperienceResponse mapToResponse(Experience experience){

        return new ExperienceResponse(
                experience.getId(),
                experience.getJobTitle(),
                experience.getCompanyName(),
                experience.getEmploymentType(),
                experience.getStartDate(),
                experience.getEndDate(),
                experience.isCurrentlyWorking(),
                experience.getDescription()
        );
    }
}
