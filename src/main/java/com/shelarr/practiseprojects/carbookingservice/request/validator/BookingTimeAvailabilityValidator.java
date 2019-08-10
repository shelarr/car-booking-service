package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

public class BookingTimeAvailabilityValidator implements BookingRequestValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingTimeAvailabilityValidator.class);

    private static final String SLOT_NOT_AVAILABLE = "time slot is not available for request ";

    @Autowired
    private CarAllotmentService carAllotmentService;

    @Override
    public void validate(CarBookingRequest carBookingRequest) {

        LocalTime bookingFromTime = carBookingRequest.getBookingFrom().toLocalTime();
        LocalTime bookingToTime = carBookingRequest.getBookingTo().toLocalTime();

        CarAllotment carAllotment = carAllotmentService.getAllotmentDetails(carBookingRequest.getDriverId());
        LocalTime driverFromTime = carAllotment.getDriverAvailableFrom().toLocalTime();
        LocalTime driverToTime = carAllotment.getDriverAvailableTo().toLocalTime();

        if (isTimeSlotAvailable(bookingFromTime, bookingToTime,
                driverFromTime, driverToTime)) {
            LOGGER.info("Time Slot is available for carBookingRequest " + carBookingRequest.toString());

        } else {
            LOGGER.info(SLOT_NOT_AVAILABLE + carBookingRequest.toString());
            throw new BookingProcessingExcpetion(SLOT_NOT_AVAILABLE + carBookingRequest.toString());
        }

    }

    private boolean isTimeSlotAvailable(LocalTime bookingFromTime, LocalTime bookingToTime,
                                        LocalTime driverFromTime, LocalTime driverToTime) {

        return (bookingFromTime.isAfter(driverFromTime) || bookingFromTime.equals(driverFromTime)) &&
                bookingToTime.isBefore(driverToTime) || bookingToTime.equals(driverToTime);
    }

}
