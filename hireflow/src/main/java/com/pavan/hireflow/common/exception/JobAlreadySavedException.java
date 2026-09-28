package com.pavan.hireflow.common.exception;

public class JobAlreadySavedException extends RuntimeException{

    public JobAlreadySavedException(String message){
        super(message);
    }
}
