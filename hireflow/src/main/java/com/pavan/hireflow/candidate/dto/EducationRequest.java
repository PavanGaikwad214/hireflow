package com.pavan.hireflow.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationRequest {

    private String degree;
    private String institution;
    private String fieldOfStudy;
    private Integer startYear;
    private Integer endYear;
    private Double percentage;
    private String grade;
}
