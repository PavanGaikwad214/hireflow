package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CandidateProfileRepository candidateProfileRepository;

    public CandidateService(CandidateProfileRepository candidateProfileRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
    }


    public CandidateProfile getProfile(Long userId){
        return candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("candidate profile not found"));
    }
}
