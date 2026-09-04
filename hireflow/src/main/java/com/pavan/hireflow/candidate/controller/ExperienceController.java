package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.ExperienceRequest;
import com.pavan.hireflow.candidate.dto.ExperienceResponse;
import com.pavan.hireflow.candidate.entity.Experience;
import com.pavan.hireflow.candidate.service.ExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate/experience")
@PreAuthorize("hasRole('CANDIDATE')")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<ExperienceResponse> addExperience(
            @AuthenticationPrincipal User user,
            @RequestBody ExperienceRequest request
            ){

        ExperienceResponse response = experienceService.addExperience(user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getExperiences(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(experienceService.getMyExperiences(user.getId()));
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> updateExperience(
            @AuthenticationPrincipal User user,
            @PathVariable Long experienceId,
            @RequestBody ExperienceRequest request
    ){
        ExperienceResponse response = experienceService.updateExperience(user.getId(), experienceId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteExperience(
            @AuthenticationPrincipal User user,
            @PathVariable Long experienceId
    ){

        experienceService.deleteExperience(user.getId(), experienceId);

        return ResponseEntity.noContent().build();
    }
}
