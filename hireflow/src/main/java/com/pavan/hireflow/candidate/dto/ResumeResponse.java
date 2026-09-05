package com.pavan.hireflow.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResumeResponse {

    private Long id;
    private String title;
    private String fileUrl;
    private boolean primaryResume;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
