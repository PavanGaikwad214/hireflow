package com.pavan.hireflow.application.controller;

import com.pavan.hireflow.application.dto.ApplicationRequest;
import com.pavan.hireflow.application.dto.ApplicationResponse;
import com.pavan.hireflow.application.dto.ApplicationStatusUpdateRequest;
import com.pavan.hireflow.application.entity.Application;
import com.pavan.hireflow.application.service.ApplicationService;
import com.pavan.hireflow.auth.entity.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    @PostMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ApplicationResponse> applyForJob(
           @RequestBody ApplicationRequest request,
            @AuthenticationPrincipal User user
            ){

        ApplicationResponse response = applicationService.applyForJob(request.getJobId(), request.getResumeId(), user);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            @AuthenticationPrincipal User user
    ){
        List<ApplicationResponse> response = applicationService.getMyApplication(user);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/recruiter")
    public ResponseEntity<List<ApplicationResponse>> getRecruiterApplications(
            @AuthenticationPrincipal User user
    ){
        List<ApplicationResponse> response = applicationService.getRecruiterApplication(user);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody ApplicationStatusUpdateRequest request,
            @AuthenticationPrincipal User user
            ){

        ApplicationResponse response = applicationService.updateApplicationStatus(
                applicationId,
                request,
                user
        );

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PatchMapping("/{applicationId}/withdraw")
    public ResponseEntity<ApplicationResponse> withdrawApplication(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal User user
    )
        {
              ApplicationResponse response = applicationService.withdrawApplication(applicationId, user);

              return ResponseEntity.ok(response);
        }
}
