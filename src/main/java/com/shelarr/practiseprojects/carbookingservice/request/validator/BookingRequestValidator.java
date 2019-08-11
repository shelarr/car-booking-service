package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;

public interface BookingRequestValidator {

    public void validate(CarBookingMessage carBookingMessage);

}
