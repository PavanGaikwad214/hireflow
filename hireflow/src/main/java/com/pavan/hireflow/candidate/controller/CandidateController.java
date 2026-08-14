package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.service.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @GetMapping("/profile")
    public ResponseEntity<CandidateProfile> getProfile(
            @AuthenticationPrincipal User user
            ){
        return ResponseEntity.ok(candidateService.getProfile(user.getId()));
    }
}
