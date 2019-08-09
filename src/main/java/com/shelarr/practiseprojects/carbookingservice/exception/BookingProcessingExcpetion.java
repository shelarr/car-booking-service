package com.shelarr.practiseprojects.carbookingservice.exception;

public class BookingProcessingExcpetion extends RuntimeException {

    private static final long serialVersionUID = 1;

    public BookingProcessingExcpetion(String message) {
        super(message);
    }

}