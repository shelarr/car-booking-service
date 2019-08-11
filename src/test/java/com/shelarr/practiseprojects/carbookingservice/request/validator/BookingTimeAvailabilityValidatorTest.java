package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import com.shelarr.practiseprojects.carbookingservice.service.CarBookingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BookingTimeAvailabilityValidatorTest {

    @Mock
    private CarAllotmentService carAllotmentService;

    @Mock
    private CarBookingService carBookingService;

    @InjectMocks
    private BookingTimeAvailabilityValidator validator = new BookingTimeAvailabilityValidator();

    private CarAllotment carAllotment;

    private CarBookingMessage bookingMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        carAllotment = new CarAllotment();
        carAllotment.setDriverAvailableFrom(new Time(12, 00, 00));
        carAllotment.setDriverAvailableTo(new Time(21, 00, 00));
        bookingMessage = new CarBookingMessage();
        bookingMessage.getCarBooking().setDriverId(1213l);
        bookingMessage.getCarBooking().setUserIdName("User1");
        bookingMessage.getCarBooking().setBookingFrom(new Time(14, 00, 00));
        bookingMessage.getCarBooking().setBookingTo(new Time(16, 00, 00));

    }

    @Test
    public void testValidate() {
        when(carAllotmentService.getAllotmentDetails("1213")).thenReturn(carAllotment);
        when(carBookingService.getAllActiveBookingsForDriver(anyLong())).thenReturn(new ArrayList<CarBooking>());

        validator.validate(bookingMessage);

        verify(carAllotmentService, times(1)).getAllotmentDetails("1213");
        validator.validate(bookingMessage);
        verify(carAllotmentService, times(2)).getAllotmentDetails("1213");
        verify(carBookingService, times(2)).getAllActiveBookingsForDriver(anyLong());

    }

    @Test(expected = BookingProcessingExcpetion.class)
    public void testValidate_WhenDriverIsNotAvailable() {
        bookingMessage.getCarBooking().setBookingFrom(new Time(10, 00, 00));
        bookingMessage.getCarBooking().setBookingTo(new Time(16, 00, 00));

        when(carAllotmentService.getAllotmentDetails("1213")).thenReturn(carAllotment);

        validator.validate(bookingMessage);
    }

    @Test(expected = BookingProcessingExcpetion.class)
    public void testValidate_WhenDriverIsAlreadyBookedInRequestedTimeSlot() {
        CarBooking carBooking = new CarBooking();
        carBooking.setBookingFrom(new Time(13, 00, 00));
        carBooking.setBookingTo(new Time(15, 00, 00));
        List<CarBooking> carBookings = new ArrayList<>();
        carBookings.add(carBooking);
        when(carAllotmentService.getAllotmentDetails("1213")).thenReturn(carAllotment);
        when(carBookingService.getAllActiveBookingsForDriver(anyLong())).thenReturn(carBookings);

        validator.validate(bookingMessage);
    }


}
