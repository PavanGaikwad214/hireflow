package com.pavan.hireflow.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeRequest {

    private String title;
    private String fileUrl;
    private boolean primaryResume;
}
