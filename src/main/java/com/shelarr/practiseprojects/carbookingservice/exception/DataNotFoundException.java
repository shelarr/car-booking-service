package com.shelarr.practiseprojects.carbookingservice.exception;

public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public DataNotFoundException(String message) {
        super(message);
    }

}