package com.pavan.hireflow.common.exception;

public class ApplicationAlreadyExistsException extends RuntimeException{
    public ApplicationAlreadyExistsException(String message){
        super(message);
    }
}
