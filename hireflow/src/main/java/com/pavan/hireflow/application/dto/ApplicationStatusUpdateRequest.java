package com.pavan.hireflow.application.dto;

import com.pavan.hireflow.common.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationStatusUpdateRequest {

    private ApplicationStatus status;
}
