package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.CandidateProfileRequest;
import com.pavan.hireflow.candidate.dto.CandidateProfileResponse;
import com.pavan.hireflow.candidate.dto.CandidateSkillsRequest;
import com.pavan.hireflow.candidate.dto.CandidateSkillsResponse;
import com.pavan.hireflow.candidate.service.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @PreAuthorize("hasRole('CANDIDATE)")
    @GetMapping("/profile")
    public ResponseEntity<CandidateProfileResponse> getProfile(
            @AuthenticationPrincipal User user
            ){
        return ResponseEntity.ok(candidateService.getProfile(user.getId()));
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/profile")
    public ResponseEntity<CandidateProfileResponse> createProfile(
            @AuthenticationPrincipal User user,
            @RequestBody CandidateProfileRequest request
    ){

        CandidateProfileResponse response = candidateService.createProfile(user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PutMapping("/profile")
    public ResponseEntity<CandidateProfileResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody CandidateProfileRequest request
    ){
        CandidateProfileResponse response = candidateService.updateProfile(user.getId(), request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("profile/skills")
    public ResponseEntity<Void> addSkills(
            @AuthenticationPrincipal User user,
            @RequestBody CandidateSkillsRequest request
            ){

        candidateService.addSkill(user.getId(), request);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("profile/skills")
    public ResponseEntity<CandidateSkillsResponse> getSkills(
            @AuthenticationPrincipal User user
    ){

        return  ResponseEntity.ok(candidateService.getSkills(user.getId()));

    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @DeleteMapping("profile/skills/{skillId}")
    public  ResponseEntity<Void> removeSkills(
            @AuthenticationPrincipal User user,
            @PathVariable Long skillId
    ){
        candidateService.removeSkill(user.getId(), skillId);

        return ResponseEntity.noContent().build();
    }
}
