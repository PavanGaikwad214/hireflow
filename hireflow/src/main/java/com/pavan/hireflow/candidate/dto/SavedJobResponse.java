package com.pavan.hireflow.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SavedJobResponse {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private Long companyId;

    private String companyName;

    private LocalDateTime savedAt;
}
