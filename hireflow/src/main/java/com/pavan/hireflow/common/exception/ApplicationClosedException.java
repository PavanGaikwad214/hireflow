package com.pavan.hireflow.common.exception;

public class ApplicationClosedException extends RuntimeException{

    public ApplicationClosedException(String message) {
        super(message);
    }
}
