package com.pavan.hireflow.common.exception;

public class SavedJobNotFoundException extends RuntimeException
{
    public SavedJobNotFoundException(String message)
    {
        super(message);
    }
}
