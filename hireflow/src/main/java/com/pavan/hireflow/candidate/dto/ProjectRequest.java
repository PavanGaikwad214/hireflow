package com.pavan.hireflow.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {

    private String title;
    private String description;
    private String projectUrl;
    private String githubUrl;
    private String startDate;
    private String endDate;
}
