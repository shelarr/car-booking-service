package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;

public interface BookingDataBuilder {

    public void populate(CarBookingRequest request, CarBooking bookingData);

}
