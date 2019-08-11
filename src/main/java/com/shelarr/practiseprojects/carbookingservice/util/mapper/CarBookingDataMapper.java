package com.shelarr.practiseprojects.carbookingservice.util.mapper;

import com.shelarr.practiseprojects.carbookingservice.dto.BookingStatus;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import org.springframework.stereotype.Component;

@Component
public class CarBookingDataMapper {

    private static final String INITIAL_BOOKING_STATUS = BookingStatus.IN_PROGRESS.getStatus();

    private static final boolean INITIAL_ACTIVE_STATUS = BookingStatus.IN_PROGRESS.getActive();

    public CarBooking mapToDto(CarBookingRequest request) {
        CarBooking carBooking = new CarBooking();
        carBooking.setDriverId(Long.valueOf(request.getDriverId()));
        carBooking.setUserIdName(request.getUserIdName());
        carBooking.setBookingFrom(request.getBookingFrom());
        carBooking.setBookingTo(request.getBookingTo());
        carBooking.setActive(INITIAL_ACTIVE_STATUS);
        carBooking.setBookingStatus(INITIAL_BOOKING_STATUS);
        return carBooking;
    }

}
