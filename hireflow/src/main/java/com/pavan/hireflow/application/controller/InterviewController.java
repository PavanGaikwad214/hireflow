package com.pavan.hireflow.application.controller;

import com.pavan.hireflow.application.dto.InterviewRequest;
import com.pavan.hireflow.application.dto.InterviewResponse;
import com.pavan.hireflow.application.service.InterviewService;
import com.pavan.hireflow.auth.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruiter/applications")
@PreAuthorize("hasRole('RECRUITER')")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("/{applicationId}/interview")
    public ResponseEntity<InterviewResponse> scheduleInterview(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicationId,
            @RequestBody InterviewRequest request
            ){

        InterviewResponse response = interviewService.scheduleInterview(
                user,
                applicationId,
                request
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
