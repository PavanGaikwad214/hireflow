package com.pavan.hireflow.job.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.job.dto.JobRequest;
import com.pavan.hireflow.job.dto.JobResponse;
import com.pavan.hireflow.job.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody JobRequest request){

        System.out.println("===== JOB CONTROLLER REACHED =====");
        System.out.println("Authenticated user: " + user.getEmail());
        System.out.println("Role: " + user.getRole());

        JobResponse response = jobService.createJob(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long jobId){

        return ResponseEntity.ok(jobService.getJob(jobId));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs(){

        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long jobId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody JobRequest request
    ){

        JobResponse response = jobService.updateJob(jobId, user, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{jobId}/close")
    public ResponseEntity<JobResponse> closeJob(
            @PathVariable Long jobId,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(jobService.closeJob(jobId, user));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<List<JobResponse>> getMyJobs(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(jobService.getMyJobs(user));
    }
}
