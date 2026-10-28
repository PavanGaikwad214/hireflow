package com.pavan.hireflow.application.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class InterviewRequest {

    private LocalDateTime scheduledAt;

    private Integer durationMinutes;

    private String meetingLink;

    private String instructions;
}
