package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.candidate.dto.EducationRequest;
import com.pavan.hireflow.candidate.dto.EducationResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.entity.Education;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.candidate.repository.EducationRepository;
import com.pavan.hireflow.common.exception.CandidateProfileNotFoundException;
import com.pavan.hireflow.common.exception.EducationNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final CandidateProfileRepository  candidateProfileRepository;


    public EducationService(EducationRepository educationRepository, CandidateProfileRepository candidateProfileRepository) {
        this.educationRepository = educationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public EducationResponse addEducation(
            Long userId,
            EducationRequest request){

        CandidateProfile candidate = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Education education = new Education();

        education.setCandidateProfile(candidate);
        education.setDegree(request.getDegree());
        education.setInstitution(request.getInstitution());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartYear(request.getStartYear());
        education.setEndYear(request.getEndYear());
        education.setPercentage(request.getPercentage());
        education.setGrade(request.getGrade());

        Education savedEducation = educationRepository.save(education);

        return mapToResponse(savedEducation);
    }

    public List<EducationResponse> getMyEducation(Long userId){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        return educationRepository
                .findByCandidateProfile_Id(candidateProfile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public EducationResponse updateEducation(
            Long userId,
            Long educationId,
            EducationRequest request
    ){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Education education = educationRepository
                .findByIdAndCandidateProfile_Id(educationId, candidateProfile.getId())
                .orElseThrow(() -> new EducationNotFoundException("Education not found"));

        education.setDegree(request.getDegree());
        education.setInstitution(request.getInstitution());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartYear(request.getStartYear());
        education.setEndYear(request.getEndYear());
        education.setPercentage(request.getPercentage());
        education.setGrade(request.getGrade());

        Education savedEducation = educationRepository.save(education);

        return mapToResponse(savedEducation);
    }

    public void deleteEducation(Long userId, Long educationId){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Education education = educationRepository
                .findByIdAndCandidateProfile_Id(educationId, candidateProfile.getId())
                .orElseThrow(() -> new EducationNotFoundException("Education not found"));

        educationRepository.delete(education);
    }

    private EducationResponse mapToResponse(Education education){

        return new EducationResponse(
                education.getId(),
                education.getDegree(),
                education.getInstitution(),
                education.getFieldOfStudy(),
                education.getStartYear(),
                education.getEndYear(),
                education.getPercentage(),
                education.getGrade()
        );
    }
}
