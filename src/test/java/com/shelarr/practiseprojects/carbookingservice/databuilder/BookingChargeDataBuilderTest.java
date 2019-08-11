package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.assertEquals;

public class BookingChargeDataBuilderTest {

    private BookingChargeDataBuilder builder;

    @Before
    public void setup() {
        builder = new BookingChargeDataBuilder();
    }

    @Test
    public void testPopulate() {

        CarBookingMessage bookingMessage = new CarBookingMessage();
        bookingMessage.getCarBooking().setDriverId(121313l);
        bookingMessage.getCarBooking().setUserIdName("user2");
        bookingMessage.getCarBooking().setBookingFrom(new Time(14, 00, 00));
        bookingMessage.getCarBooking().setBookingTo(new Time(17, 00, 00));


        builder.populate(bookingMessage);

        assertEquals(14, bookingMessage.getCarBooking().getBookingFrom().getHours());
        assertEquals(0, bookingMessage.getCarBooking().getBookingFrom().getMinutes());
        assertEquals(17, bookingMessage.getCarBooking().getBookingTo().getHours());
        assertEquals(0, bookingMessage.getCarBooking().getBookingTo().getMinutes());
        assertEquals(300, bookingMessage.getCarBooking().getBookingCharge().intValue());
    }

}
