package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;

public interface BookingDataBuilder {

    public void populate(CarBookingMessage carBookingMessage);

}
