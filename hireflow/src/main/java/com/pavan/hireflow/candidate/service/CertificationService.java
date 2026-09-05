package com.pavan.hireflow.candidate.service;

import com.pavan.hireflow.candidate.dto.CertificationRequest;
import com.pavan.hireflow.candidate.dto.CertificationResponse;
import com.pavan.hireflow.candidate.entity.CandidateProfile;
import com.pavan.hireflow.candidate.entity.Certification;
import com.pavan.hireflow.candidate.repository.CandidateProfileRepository;
import com.pavan.hireflow.candidate.repository.CertificationRepository;
import com.pavan.hireflow.common.exception.CandidateProfileNotFoundException;
import com.pavan.hireflow.common.exception.CertificationNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public CertificationService(CertificationRepository certificationRepository, CandidateProfileRepository candidateProfileRepository) {
        this.certificationRepository = certificationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public CertificationResponse addCertification(Long userId, CertificationRequest certificationRequest) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Certification certification = new Certification();

        certification.setCandidateProfile(candidateProfile);
        certification.setName(certificationRequest.getName());
        certification.setIssuingOrganization(
                certificationRequest.getIssuingOrganization()
        );
        certification.setIssueDate(certificationRequest.getIssueDate());
        certification.setExpiryDate(certificationRequest.getExpiryDate());
        certification.setCredentialId(certificationRequest.getCredentialId());
        certification.setCredentialUrl(certificationRequest.getCredentialUrl());

        Certification savedCertification = certificationRepository.save(certification);

        return mapToResponse(savedCertification);

    }

    public List<CertificationResponse> getCertifications(Long userId) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));


        return certificationRepository
                .findByCandidateProfile_Id(candidateProfile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CertificationResponse updateCertification(
            Long userId,
            Long certificationId,
            CertificationRequest certificationRequest) {

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Certification certification = certificationRepository
                .findByIdAndCandidateProfile_Id(certificationId, candidateProfile.getId())
                .orElseThrow(() -> new CertificationNotFoundException("Certification Not Found"));

        certification.setName(certificationRequest.getName());
        certification.setIssuingOrganization(certificationRequest.getIssuingOrganization());
        certification.setIssueDate(certificationRequest.getIssueDate());
        certification.setExpiryDate(certificationRequest.getExpiryDate());
        certification.setCredentialId(certificationRequest.getCredentialId());
        certification.setCredentialUrl(certificationRequest.getCredentialUrl());

        Certification savedCertification = certificationRepository.save(certification);
        return mapToResponse(savedCertification);
    }

    public void deleteCertification(Long userId, Long certificationId){

        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateProfileNotFoundException("Candidate Profile Not Found"));

        Certification certification = certificationRepository
                .findByIdAndCandidateProfile_Id(certificationId, candidateProfile.getId())
                .orElseThrow(() -> new CertificationNotFoundException("Certification Not Found"));

        certificationRepository.delete(certification);
    }

    private CertificationResponse mapToResponse(Certification certification) {

        return new CertificationResponse(
                certification.getId(),
                certification.getName(),
                certification.getIssuingOrganization(),
                certification.getIssueDate(),
                certification.getExpiryDate(),
                certification.getCredentialId(),
                certification.getCredentialUrl()
        );
    }
}
