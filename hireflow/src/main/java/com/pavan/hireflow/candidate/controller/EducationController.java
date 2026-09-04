package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.EducationRequest;
import com.pavan.hireflow.candidate.dto.EducationResponse;
import com.pavan.hireflow.candidate.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate/education")
@PreAuthorize("hasRole('CANDIDATE')")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping
    public ResponseEntity<EducationResponse> addEducation(
            @AuthenticationPrincipal User user,
            @RequestBody EducationRequest educationRequest
            ){

        EducationResponse response = educationService.addEducation(user.getId(), educationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getMyEducations(@AuthenticationPrincipal User user){

        return ResponseEntity.ok(
                educationService.getMyEducation(user.getId())
        );
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponse> updateEducation(
            @AuthenticationPrincipal User user,
            @PathVariable Long educationId,
            @RequestBody EducationRequest educationRequest
    ){
        return ResponseEntity.ok(
                educationService.updateEducation(
                        user.getId(),
                        educationId,
                        educationRequest
                )
        );
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<Void> deleteEducation(
            @PathVariable Long educationId,
            @AuthenticationPrincipal User user){

        educationService.deleteEducation(user.getId(),  educationId);

        return ResponseEntity.noContent().build();
    }
}
