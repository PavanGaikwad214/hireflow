package com.pavan.hireflow.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InterviewResponse {

    private Long id;

    private Long applicationId;

    private Long candidateId;
    private String candidateName;

    private Long jobId;
    private String jobTitle;

    private LocalDateTime scheduledAt;
    private Integer durationMinutes;
    private String meetingLink;
    private String instructions;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
