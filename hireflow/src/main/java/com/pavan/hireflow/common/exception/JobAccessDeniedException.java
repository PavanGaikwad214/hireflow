package com.pavan.hireflow.common.exception;

public class JobAccessDeniedException extends RuntimeException{

    public JobAccessDeniedException(String message) {
        super(message);
    }
}
