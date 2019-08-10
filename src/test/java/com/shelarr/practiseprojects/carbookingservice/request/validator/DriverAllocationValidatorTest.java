package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dao.CarBookingsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DriverAllocationValidatorTest {

    @Mock
    private CarAllotmentService carAllotmentService;

    @Mock
    private CarBookingsDao carBookingsDao;

    @InjectMocks
    private DriverAllocationValidator validator = new DriverAllocationValidator();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidate_whenDriverIsValidForBooking() {
        CarBookingRequest bookingRequest = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("1331")
                .setUserIdName("user2")
                .setBookingFrom("14:00")
                .setBookingTo("16:00")
                .build();
        CarAllotment carAllotment = getAllotment(1331l, new Time(12, 00, 00),
                new Time(18, 00, 00));
        when(carAllotmentService.getAllotmentDetails("1331")).thenReturn(carAllotment);
        when(carBookingsDao.findActiveBookingForDriver(new Long("1331"))).thenReturn(null);

        validator.validate(bookingRequest);

        verify(carAllotmentService, times(1)).getAllotmentDetails("1331");
        verify(carBookingsDao, times(1)).findActiveBookingForDriver(anyLong());
    }

    @Test(expected = BookingProcessingExcpetion.class)
    public void testValidate_whenDriverNotAllocatedAnyCar() {
        CarBookingRequest bookingRequest = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("1331")
                .setUserIdName("user2")
                .setBookingFrom("14:00")
                .setBookingTo("16:00")
                .build();
        when(carAllotmentService.getAllotmentDetails("1331")).thenReturn(null);
        when(carBookingsDao.findActiveBookingForDriver(new Long("1331"))).thenReturn(null);

        validator.validate(bookingRequest);

    }

    @Test(expected = BookingProcessingExcpetion.class)
    public void testValidate_whenDriverIsAlreadyBooked() {
        CarBookingRequest bookingRequest = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("1331")
                .setUserIdName("user2")
                .setBookingFrom("14:00")
                .setBookingTo("16:00")
                .build();
        CarBooking carBooking = getCarBooking("1331");
        CarAllotment carAllotment = getAllotment(1331l, new Time(12, 00, 00),
                new Time(18, 00, 00));
        when(carAllotmentService.getAllotmentDetails("1331")).thenReturn(carAllotment);
        when(carBookingsDao.findActiveBookingForDriver(new Long("1331"))).thenReturn(carBooking);

        validator.validate(bookingRequest);

    }

    private CarAllotment getAllotment(Long id, Time allotmentFrom, Time allotmentFromTo) {
        CarAllotment carAllotment = new CarAllotment();
        carAllotment.setDriverId(id);
        carAllotment.setDriverAvailableFrom(allotmentFrom);
        carAllotment.setDriverAvailableTo(allotmentFromTo);
        return carAllotment;
    }

    private CarBooking getCarBooking(String driverId) {
        CarBooking carBooking = new CarBooking();
        carBooking.setDriverId(Long.valueOf(driverId));
        carBooking.setActive(true);
        return carBooking;
    }

}
