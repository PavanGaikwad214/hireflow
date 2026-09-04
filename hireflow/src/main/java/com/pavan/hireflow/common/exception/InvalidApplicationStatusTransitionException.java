package com.pavan.hireflow.common.exception;

public class InvalidApplicationStatusTransitionException extends RuntimeException{
    public InvalidApplicationStatusTransitionException(String message) {
        super(message);
    }
}
