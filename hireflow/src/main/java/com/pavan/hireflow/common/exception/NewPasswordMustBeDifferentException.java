package com.pavan.hireflow.common.exception;

public class NewPasswordMustBeDifferentException extends RuntimeException{
    public NewPasswordMustBeDifferentException(String message) {
        super(message);
    }
}
