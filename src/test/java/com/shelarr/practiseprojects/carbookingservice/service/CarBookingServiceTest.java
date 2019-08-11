package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.dao.CarBookingsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessageDispatcher;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.util.mapper.CarBookingDataMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CarBookingServiceTest {

    @Mock
    private CarBookingsDao carBookingsDao;

    @Mock
    private CarBookingMessageDispatcher dispatcher;

    private CarBookingDataMapper carBookingDataMapper = new CarBookingDataMapper();

    @InjectMocks
    private CarBookingService carBookingService = new CarBookingService();

    private CarBookingRequest carBookingRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(carBookingService, "carBookingDataMapper", carBookingDataMapper);

        carBookingRequest = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("13131")
                .setUserIdName("user2Id")
                .setBookingFrom("14:00:00")
                .setBookingTo("16:00:00")
                .build();
    }

    @Test
    public void testCreateBooking() {
        Long bookingId = new Long("543");
        CarBooking carBooking = mock(CarBooking.class);
        when(carBooking.getId()).thenReturn(bookingId);
        when(carBookingsDao.fetchBookingId(any(CarBooking.class))).thenReturn(carBooking);

        String bookingIdResponse = carBookingService.createBooking(carBookingRequest);
        assertEquals("543", bookingIdResponse);
    }

    @Test
    public void testGetAllBookings() {
        List<CarBooking> carBookings = mock(List.class);
        when(carBookingsDao.findAll()).thenReturn(carBookings);

        List<CarBooking> fetchedCarBookings = carBookingService.getAllBookings();

        assertEquals(carBookings, fetchedCarBookings);
    }

    @Test
    public void testChangeBookingStatus() {
        when(carBookingsDao.changeBookingStatus("1233", "CONFIRMED", true)).thenReturn(1);

        boolean success = carBookingService.changeBookingStatus("1233", "CONFIRMED");

        assertTrue(success);
        verify(carBookingsDao, times(1)).changeBookingStatus("1233", "CONFIRMED", true);
    }

    @Test(expected = BookingProcessingExcpetion.class)
    public void testChangeBookingStatus_whenInvalidStatus() {
        carBookingService.changeBookingStatus("1233", "SOME_INVALID_STATUS");

        boolean success = carBookingService.changeBookingStatus("1233", "CONFIRMED");
    }


}
