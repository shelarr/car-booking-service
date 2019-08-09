package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.BookingStatus;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;

public class DefaultBookingDataBuilder implements BookingDataBuilder {

    private static final boolean initialAcitveState = true;

    private static final BookingStatus INITIAL_BOOKING_STATUS = BookingStatus.CONFIRMED;

    @Override
    public void populate(CarBookingRequest request, CarBooking bookingData) {

        bookingData.setUserIdName(request.getUserIdName());
        bookingData.setActive(initialAcitveState);
        bookingData.setBookingStatus(INITIAL_BOOKING_STATUS.toString());

    }

}
