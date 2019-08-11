package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.exception.AllocationProcessingException;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import com.shelarr.practiseprojects.carbookingservice.service.CarBookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.List;

public class BookingTimeAvailabilityValidator implements BookingRequestValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingTimeAvailabilityValidator.class);

    private static final String DRIVER_ALREADY_BOOKED_MESSAGE = "Requested driver already Booked. Please Try with another driver or different Time Slot";

    private static final String DRIVER_NOT_AVAILABLE = "Requested driver is not available in requested time slot. Try with another driver or different Time Slot";

    @Autowired
    private CarAllotmentService carAllotmentService;

    @Autowired
    private CarBookingService carBookingService;

    @Override
    public void validate(CarBookingMessage bookingMessage) {

        LocalTime requestedFromTime = bookingMessage.getCarBooking().getBookingFrom().toLocalTime();
        LocalTime requestedToTime = bookingMessage.getCarBooking().getBookingTo().toLocalTime();

        Long driverId = bookingMessage.getCarBooking().getDriverId();

        CarAllotment carAllotment = carAllotmentService.getAllotmentDetails(String.valueOf(driverId));

        LocalTime driverFromTime = carAllotment.getDriverAvailableFrom().toLocalTime();
        LocalTime driverToTime = carAllotment.getDriverAvailableTo().toLocalTime();

        if (isTimeSlotAvailable(requestedFromTime, requestedToTime,
                driverFromTime, driverToTime)) {
            LOGGER.info("Time Slot is available for carBooking request " + bookingMessage.toString());
        } else {
            LOGGER.info(DRIVER_NOT_AVAILABLE + bookingMessage.toString());
            throw new BookingProcessingExcpetion(DRIVER_NOT_AVAILABLE + bookingMessage.toString());
        }

        List<CarBooking> carBookings = carBookingService.getAllActiveBookingsForDriver(driverId);

        if (carBookings.size() == 0) {
            return;
        }

        carBookings.stream().forEach( existingBooking -> {
            LocalTime driverBookedFrom = existingBooking.getBookingFrom().toLocalTime();
            LocalTime driverBookedTo = existingBooking.getBookingTo().toLocalTime();
            boolean isOverLapping = checKForOverlap(driverBookedFrom, driverBookedTo,
                    requestedFromTime, requestedToTime);
            if (isOverLapping) {
                throw new BookingProcessingExcpetion(DRIVER_ALREADY_BOOKED_MESSAGE);
            }
        });

    }

    private boolean isTimeSlotAvailable(LocalTime bookingFromTime, LocalTime bookingToTime,
                                        LocalTime driverFromTime, LocalTime driverToTime) {

        return (bookingFromTime.isAfter(driverFromTime) || bookingFromTime.equals(driverFromTime)) &&
                bookingToTime.isBefore(driverToTime) || bookingToTime.equals(driverToTime);
    }

    private boolean checKForOverlap(LocalTime driverBookedFrom, LocalTime driverBookedTo,
                                    LocalTime requestedFromTime, LocalTime requestedToTime) {
        return (requestedFromTime.isAfter(driverBookedFrom) && requestedFromTime.isBefore(driverBookedTo)) ||
                (requestedToTime.isAfter(driverBookedFrom) && requestedToTime.isBefore(driverBookedTo));

    }

}
