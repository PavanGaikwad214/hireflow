package com.pavan.hireflow.candidate.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.dto.CertificationRequest;
import com.pavan.hireflow.candidate.dto.CertificationResponse;
import com.pavan.hireflow.candidate.service.CertificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate/certification")
@PreAuthorize("hasRole('CANDIDATE')")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping
    public ResponseEntity<CertificationResponse> addCertification(
            @AuthenticationPrincipal User user,
            @RequestBody CertificationRequest certificationRequest
            ){
        CertificationResponse response = certificationService.addCertification(user.getId(), certificationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CertificationResponse>> getCertification(@AuthenticationPrincipal User user){

        return ResponseEntity.ok(certificationService.getCertifications(user.getId()));
    }

    @PutMapping("/{certificationId}")
    public ResponseEntity<CertificationResponse> updateCertification(
            @AuthenticationPrincipal User user,
            @PathVariable Long certificationId,
            @RequestBody CertificationRequest certificationRequest
    ){
        CertificationResponse response = certificationService.updateCertification(user.getId(), certificationId, certificationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{certificationId}")
    public ResponseEntity<Void>  deleteCertification(
            @AuthenticationPrincipal User user,
            @PathVariable Long certificationId){

        certificationService.deleteCertification(user.getId(), certificationId);
        return ResponseEntity.noContent().build();
    }
}
