package com.pavan.hireflow.job.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class JobRequest {

    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Job description is required")
    private String description;

    @NotBlank(message = "Job location is required")
    private String location;

    @NotBlank(message = "Employment type is required")
    private String employmentType;

    private Double minSalary;

    private Double maxSalary;

    private Set<String> requiredSkills = new HashSet<>();

}
