package com.pavan.hireflow.job.dto;

import com.pavan.hireflow.common.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class JobResponse {

    private Long id;

    private String title;

    private String description;

    private String location;

    private String employmentType;

    private Double minSalary;

    private Double maxSalary;

    private Long companyId;

    private String companyName;

    private Long recruiterId;

    private String recruiterName;

    private Set<String> requiredSkills;

    private JobStatus status;
}
