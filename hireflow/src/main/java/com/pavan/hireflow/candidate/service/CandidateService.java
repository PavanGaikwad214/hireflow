package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.auth.repository.UserRepository;
import com.pavan.hireflow.candidate.dto.CandidateProfileRequest;
import com.pavan.hireflow.candidate.dto.CandidateProfileResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.common.exception.CandidateProfileAlreadyExistsException;
import com.pavan.hireflow.common.exception.CandidateProfileNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;

    public CandidateService(CandidateProfileRepository candidateProfileRepository, UserRepository userRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.userRepository = userRepository;
    }

    private CandidateProfileResponse mapToResponse(CandidateProfile profile){

        return new CandidateProfileResponse(
                profile.getId(),
                profile.getSummary(),
                profile.getHeadline(),
                profile.getResumeUrl(),
                profile.getGithubUrl(),
                profile.getLinkedinUrl(),
                profile.getPortfolioUrl()
        );
    }

    public CandidateProfileResponse getProfile(Long userId){

        CandidateProfile profile  =  candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("candidate profile not found"));

        return mapToResponse(profile);
    }


    public CandidateProfileResponse createProfile(
            Long userId,
            CandidateProfileRequest request
    ){
        if(candidateProfileRepository.findByUserId(userId).isPresent()){
            throw new CandidateProfileAlreadyExistsException("candidate profile already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        CandidateProfile profile = new CandidateProfile();

        profile.setUser(user);
        profile.setSummary(request.getSummary());
        profile.setHeadline(request.getHeadline());
        profile.setResumeUrl(request.getResumeUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setPortfolioUrl(request.getPortfolioUrl());

        CandidateProfile savedProfile =  candidateProfileRepository.save(profile);

        return  mapToResponse(savedProfile);
    }

    public CandidateProfileResponse updateProfile(
            Long userId,
            CandidateProfileRequest request
    ){
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("candidate profile not found"));

        profile.setSummary(request.getSummary());
        profile.setHeadline(request.getHeadline());
        profile.setResumeUrl(request.getResumeUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setPortfolioUrl(request.getPortfolioUrl());

        CandidateProfile savedProfile =  candidateProfileRepository.save(profile);

        return  mapToResponse(savedProfile);
    }
}
