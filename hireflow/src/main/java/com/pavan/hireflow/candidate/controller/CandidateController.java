package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.CandidateProfileRequest;
import com.pavan.hireflow.candidate.dto.CandidateProfileResponse;
import com.pavan.hireflow.candidate.service.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @GetMapping("/profile")
    public ResponseEntity<CandidateProfileResponse> getProfile(
            @AuthenticationPrincipal User user
            ){
        return ResponseEntity.ok(candidateService.getProfile(user.getId()));
    }

    @PostMapping("/profile")
    public ResponseEntity<CandidateProfileResponse> createProfile(
            @AuthenticationPrincipal User user,
            @RequestBody CandidateProfileRequest request
    ){

        CandidateProfileResponse response = candidateService.createProfile(user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
