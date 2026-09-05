package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.ResumeRequest;
import com.pavan.hireflow.candidate.dto.ResumeResponse;
import com.pavan.hireflow.candidate.service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate/resume")
@PreAuthorize("hasRole('CANDIDATE')")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<ResumeResponse> addResume(
            @AuthenticationPrincipal User user,
           @RequestBody ResumeRequest resumeRequest) {

        ResumeResponse response = resumeService.addResume(user.getId(), resumeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getResumes(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(resumeService.getResumes(user.getId()));
    }

    @PutMapping("/{resumeId}")
    public ResponseEntity<ResumeResponse> updateResume(
            @AuthenticationPrincipal User user,
            @PathVariable Long resumeId,
           @RequestBody ResumeRequest resumeRequest
    ){
        ResumeResponse response = resumeService.updateResume(user.getId(), resumeId, resumeRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(
            @AuthenticationPrincipal User user,
            @PathVariable Long resumeId
    ){
        resumeService.deleteResume(user.getId(), resumeId);

        return ResponseEntity.noContent().build();
    }
}
