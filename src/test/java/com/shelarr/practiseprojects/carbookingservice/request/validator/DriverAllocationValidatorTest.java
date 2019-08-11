package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dao.CarBookingsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;

import static org.mockito.Mockito.*;

public class DriverAllocationValidatorTest {

    @Mock
    private CarAllotmentService carAllotmentService;

    @Mock
    private CarBookingsDao carBookingsDao;

    @InjectMocks
    private DriverAllocationValidator validator = new DriverAllocationValidator();

    private CarBookingMessage bookingMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bookingMessage = new CarBookingMessage();
    }

    @Test
    public void testValidate_whenDriverIsValidForBooking() {

        bookingMessage.getCarBooking().setDriverId(1331l);
        bookingMessage.getCarBooking().setUserIdName("user2");
        bookingMessage.getCarBooking().setBookingFrom(new Time(14, 00, 00));
        bookingMessage.getCarBooking().setBookingTo(new Time(16, 00, 00));
        CarAllotment carAllotment = getAllotment(1331l, new Time(12, 00, 00),
                new Time(18, 00, 00));
        when(carAllotmentService.getAllotmentDetails("1331")).thenReturn(carAllotment);

        validator.validate(bookingMessage);

        verify(carAllotmentService, times(1)).getAllotmentDetails("1331");
    }

    @Test(expected = BookingProcessingExcpetion.class)
    public void testValidate_whenDriverNotAllocatedAnyCar() {
        bookingMessage.getCarBooking().setDriverId(1331l);
        bookingMessage.getCarBooking().setUserIdName("user2");
        bookingMessage.getCarBooking().setBookingFrom(new Time(14, 00, 00));
        bookingMessage.getCarBooking().setBookingTo(new Time(16, 00, 00));
        when(carAllotmentService.getAllotmentDetails("1331")).thenReturn(null);
        validator.validate(bookingMessage);

    }

    private CarAllotment getAllotment(Long id, Time allotmentFrom, Time allotmentFromTo) {
        CarAllotment carAllotment = new CarAllotment();
        carAllotment.setDriverId(id);
        carAllotment.setDriverAvailableFrom(allotmentFrom);
        carAllotment.setDriverAvailableTo(allotmentFromTo);
        return carAllotment;
    }

}
