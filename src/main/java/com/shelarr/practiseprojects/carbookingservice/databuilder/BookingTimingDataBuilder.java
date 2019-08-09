package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;

import java.math.BigDecimal;

public class BookingTimingDataBuilder implements BookingDataBuilder {

    private int perHourCharge = 100;


    @Override
    public void populate(CarBookingRequest request, CarBooking bookingData) {

        bookingData.setBookingFrom(request.getBookingFrom());
        bookingData.setBookingTo(request.getBookingTo());

        int hours = Math.abs(request.getBookingFrom().getHours() - request.getBookingTo().getHours());

        bookingData.setBookingCharge(new BigDecimal(hours * perHourCharge));

    }

}
