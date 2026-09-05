package com.pavan.hireflow.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private String projectUrl;
    private String githubUrl;
    private String startDate;
    private String endDate;

}
