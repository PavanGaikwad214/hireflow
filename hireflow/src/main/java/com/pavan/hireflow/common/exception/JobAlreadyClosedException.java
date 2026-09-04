package com.pavan.hireflow.common.exception;

public class JobAlreadyClosedException extends RuntimeException{
    public JobAlreadyClosedException(String message){
        super(message);
    }
}
