package com.pavan.hireflow.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RecruiterNoteResponse {

    private Long id;

    private Long applicationId;

    private Long recruiterId;
    private String recruiterName;

    private String note;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
