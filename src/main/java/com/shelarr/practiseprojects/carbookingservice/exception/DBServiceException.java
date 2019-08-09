package com.shelarr.practiseprojects.carbookingservice.exception;

public class DBServiceException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public DBServiceException(String message) {
        super(message);
    }

}