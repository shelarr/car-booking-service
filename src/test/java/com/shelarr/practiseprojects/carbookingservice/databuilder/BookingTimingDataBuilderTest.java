package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookingTimingDataBuilderTest {

    private BookingTimingDataBuilder builder;

    @Before
    public void setup() {
        builder = new BookingTimingDataBuilder();
    }

    @Test
    public void testPopulate() {

        CarBookingRequest request = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("121313")
                .setUserIdName("user2")
                .setBookingFrom("14:00")
                .setBookingTo("17:00")
                .build();

        CarBooking bookingData = new CarBooking();
        builder.populate(request, bookingData);
        assertEquals(14, bookingData.getBookingFrom().getHours());
        assertEquals(0, bookingData.getBookingFrom().getMinutes());
        assertEquals(17, bookingData.getBookingTo().getHours());
        assertEquals(0, bookingData.getBookingTo().getMinutes());
        assertEquals(300, bookingData.getBookingCharge().intValue());
    }

}
