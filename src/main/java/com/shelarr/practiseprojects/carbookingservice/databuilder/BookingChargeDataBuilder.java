package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.BookingStatus;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;

import java.math.BigDecimal;

public class BookingChargeDataBuilder implements BookingDataBuilder {

    private static final int perHourCharge = 100;

    private static final String CONFIRMED_STATUS = BookingStatus.CONFIRMED.getStatus();

    private static final boolean ACTIVE_STATUS = BookingStatus.CONFIRMED.getActive();

    @Override
    public void populate(CarBookingMessage bookingMessage) {

        int fromHours = bookingMessage.getCarBooking().getBookingFrom().getHours();
        int toHours = bookingMessage.getCarBooking().getBookingTo().getHours();
        int hours = Math.abs(fromHours - toHours);

        BigDecimal bookingCharge = new BigDecimal(hours * perHourCharge);
        bookingMessage.getCarBooking().setBookingCharge(bookingCharge);

        bookingMessage.getCarBooking().setBookingStatus(CONFIRMED_STATUS);
        bookingMessage.getCarBooking().setActive(ACTIVE_STATUS);
    }

}
