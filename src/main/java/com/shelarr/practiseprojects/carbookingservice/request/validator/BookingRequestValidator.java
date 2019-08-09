package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;

public interface BookingRequestValidator {

    public void validate(CarBookingRequest carBookingRequest);

}
