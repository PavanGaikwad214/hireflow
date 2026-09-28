package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.SavedJobRequest;
import com.pavan.hireflow.candidate.dto.SavedJobResponse;
import com.pavan.hireflow.candidate.service.SavedJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate/saved-jobs")
@PreAuthorize("hasRole('CANDIDATE')")
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    @PostMapping
    public ResponseEntity<SavedJobResponse> saveJob(
            @AuthenticationPrincipal User user,
            @RequestBody SavedJobRequest savedJobRequest
            ){
        SavedJobResponse response = savedJobService.saveJob(user.getId(), savedJobRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SavedJobResponse>> getSavedJobs(
            @AuthenticationPrincipal User user
    ){
        List<SavedJobResponse> response = savedJobService.getSavedJobs(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{savedJobId}")
    public ResponseEntity<Void> removeSavedJob(
            @AuthenticationPrincipal User user,
            @PathVariable Long savedJobId
    ){
        savedJobService.removeSavedJob(user.getId(), savedJobId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
