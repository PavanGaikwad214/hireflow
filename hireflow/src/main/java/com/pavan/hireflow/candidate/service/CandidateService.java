package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.auth.repository.UserRepository;
import com.pavan.hireflow.candidate.dto.CandidateProfileRequest;
import com.pavan.hireflow.candidate.dto.CandidateProfileResponse;
import com.pavan.hireflow.candidate.dto.CandidateSkillsRequest;
import com.pavan.hireflow.candidate.dto.CandidateSkillsResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.entity.Skill;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.candidate.repository.SkillRepository;
import com.pavan.hireflow.common.exception.CandidateProfileAlreadyExistsException;
import com.pavan.hireflow.common.exception.CandidateProfileNotFoundException;
import com.pavan.hireflow.common.exception.SkillNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public CandidateService(CandidateProfileRepository candidateProfileRepository, UserRepository userRepository, SkillRepository skillRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
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

    public void addSkill(Long userId, CandidateSkillsRequest request){

        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));


        for(String skillName : request.getSkills()){

            String normalizedSkill =  skillName.trim();

            if(normalizedSkill.isBlank()){
                continue;
            }

            Skill skill = skillRepository.findByNameIgnoreCase(normalizedSkill)
                    .orElseGet(() -> {
                        Skill newSkill = new Skill();
                        newSkill.setName(normalizedSkill);
                        return skillRepository.save(newSkill);
                    });
            profile.getSkills().add(skill);
        }

        candidateProfileRepository.save(profile);

    }

    public CandidateSkillsResponse getSkills(Long userId){

        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Set<String> skillNames = profile.getSkills()
                .stream()
                .map(Skill::getName)
                .collect(Collectors.toSet());

        return  new CandidateSkillsResponse(skillNames);
    }

    public void removeSkill(Long userId, Long skillId){

        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

       boolean removed = profile.getSkills()
                .removeIf(skill -> skill.getId().equals(skillId));

       if(!removed){
           throw new SkillNotFoundException("Skill not Found");
       }

       candidateProfileRepository.save(profile);
    }
}
