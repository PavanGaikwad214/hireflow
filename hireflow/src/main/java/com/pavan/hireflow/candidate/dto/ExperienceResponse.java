package com.pavan.hireflow.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExperienceResponse {

    private Long id;
    private String jobTitle;
    private String companyName;
    private String employmentType;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentlyWorking;
    private String description;
}
