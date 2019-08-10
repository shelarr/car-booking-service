package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;

import static org.mockito.Mockito.*;

public class BookingTimeAvailabilityValidatorTest {

    @Mock
    private CarAllotmentService carAllotmentService;

    @InjectMocks
    private BookingTimeAvailabilityValidator validator = new BookingTimeAvailabilityValidator();

    private CarAllotment carAllotment;

    private CarBookingRequest carBookingRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        carAllotment = new CarAllotment();
        carAllotment.setDriverAvailableFrom(new Time(12, 00, 00));
        carAllotment.setDriverAvailableTo(new Time(21, 00, 00));

        carBookingRequest = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("1213")
                .setUserIdName("User1")
                .setBookingFrom("14:00")
                .setBookingTo("16:00")
                .build();
    }

    @Test
    public void testValidate() {

        when(carAllotmentService.getAllotmentDetails("1213")).thenReturn(carAllotment);

        validator.validate(carBookingRequest);
        verify(carAllotmentService, times(1)).getAllotmentDetails("1213");

        CarBookingRequest carBookingRequest2 = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("1213")
                .setUserIdName("User1")
                .setBookingFrom("12:00")
                .setBookingTo("14:00")
                .build();

        validator.validate(carBookingRequest2);
        verify(carAllotmentService, times(2)).getAllotmentDetails("1213");
    }

    @Test(expected = BookingProcessingExcpetion.class)
    public void testValidate_WhenDriverIsNotAvailable() {
        CarBookingRequest carBookingRequest = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("1213")
                .setUserIdName("User1")
                .setBookingFrom("10:00")
                .setBookingTo("16:00")
                .build();

        when(carAllotmentService.getAllotmentDetails("1213")).thenReturn(carAllotment);

        validator.validate(carBookingRequest);
        verify(carAllotmentService, times(1)).getAllotmentDetails("1213");

    }

}
