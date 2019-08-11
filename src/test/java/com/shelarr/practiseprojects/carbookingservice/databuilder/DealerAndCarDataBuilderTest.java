package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Time;

import static org.junit.Assert.assertEquals;

public class DealerAndCarDataBuilderTest {

    @Mock
    private CarAllotmentService carAllotmentService;

    @InjectMocks
    private DealerAndCarDataBuilder builder = new DealerAndCarDataBuilder();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarAllotment carAllotment = createCarAllotment();
        Mockito.when(carAllotmentService.getAllotmentDetails("31313")).thenReturn(carAllotment);
    }

    @Test
    public void testPopulate() {

        CarBookingMessage bookingMessage = new CarBookingMessage();

        bookingMessage.getCarBooking().setDriverId(31313l);
        bookingMessage.getCarBooking().setUserIdName("user2");
        bookingMessage.getCarBooking().setBookingFrom(new Time(14, 00, 00));
        bookingMessage.getCarBooking().setBookingTo(new Time(17, 00, 00));

        builder.populate(bookingMessage);

        assertEquals(212l, bookingMessage.getCarBooking().getCarId().longValue());
        assertEquals("MH23LK32323", bookingMessage.getCarBooking().getCarRegNumber());
        assertEquals(31313l, bookingMessage.getCarBooking().getDriverId().longValue());
        assertEquals("John Doe", bookingMessage.getCarBooking().getDriverName());
        assertEquals("212113311", bookingMessage.getCarBooking().getDriverLicenseNumber());
    }

    private CarAllotment createCarAllotment() {
        CarAllotment carAllotment = new CarAllotment();
        carAllotment.setId(212l);
        carAllotment.setCarId(212l);
        carAllotment.setCarRegNumber("MH23LK32323");
        carAllotment.setDriverLicenseNumber("212113311");
        carAllotment.setDriverName("John Doe");
        carAllotment.setDriverId(31313l);
        return carAllotment;
    }

}
