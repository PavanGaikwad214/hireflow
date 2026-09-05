package com.pavan.hireflow.application.dto;

import com.pavan.hireflow.common.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;

    private Long candidateId;
    private String candidateName;

    private Long jobId;
    private String jobTitle;

    private Long resumeId;
    private String resumeTitle;

    private Long companyId;
    private String companyName;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
}
