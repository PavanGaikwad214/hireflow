package com.pavan.hireflow.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateProfileRequest {

    private String summary;

    private String headline;

    private String resumeUrl;

    private String githubUrl;

    private String linkedinUrl;

    private String portfolioUrl;

}
