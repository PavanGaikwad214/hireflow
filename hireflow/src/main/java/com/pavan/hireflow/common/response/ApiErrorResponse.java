package com.pavan.hireflow.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timeStamp;
    private Map<String, String> errors;

}
