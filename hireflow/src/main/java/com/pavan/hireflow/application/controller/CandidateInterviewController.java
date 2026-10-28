package com.pavan.hireflow.application.controller;

import com.pavan.hireflow.application.dto.InterviewResponse;
import com.pavan.hireflow.application.service.InterviewService;
import com.pavan.hireflow.auth.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate/applications")
@PreAuthorize("hasRole('CANDIDATE')")
public class CandidateInterviewController {

    private final InterviewService interviewService;

    public CandidateInterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping("/{applicationId}/interview")
    public ResponseEntity<InterviewResponse> getCandidateInterview(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicationId
            ){
        InterviewResponse response = interviewService.getCandidateInterview(
                user.getId(),
                applicationId
        );
        return ResponseEntity.ok(response);
    }
}
