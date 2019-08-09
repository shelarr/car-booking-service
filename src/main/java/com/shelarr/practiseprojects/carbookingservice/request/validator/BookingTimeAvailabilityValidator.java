package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.time.LocalTime;

public class BookingTimeAvailabilityValidator implements BookingRequestValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingTimeAvailabilityValidator.class);

    private static final String SLOT_NOT_AVAILABLE = "time slot is not available for request ";

    @Resource
    private CarAllotmentService carAllotmentService;

    @Override
    public void validate(CarBookingRequest carBookingRequest) {

        LocalTime bookingfromTime = carBookingRequest.getBookingFrom().toLocalTime();
        LocalTime bookingToTime = carBookingRequest.getBookingTo().toLocalTime();

        CarAllotment carAllotment = carAllotmentService.getAllotMentDetails(carBookingRequest.getDriverId());


        LocalTime driverFromTime = carAllotment.getDriverAvailableFrom().toLocalTime();
        LocalTime driverToTime = carAllotment.getDriverAvailableTo().toLocalTime();

        System.out.println("Checking !");
        System.out.println("bookingfromTime" + bookingfromTime);
        System.out.println("driverFromTime" + driverFromTime);
        System.out.println("bookingToTime" + bookingToTime);
        System.out.println("driverToTime" + driverToTime);

        if (bookingfromTime.isAfter(driverFromTime) && bookingToTime.isBefore(driverToTime)) {
            LOGGER.info("Time Slot is available for carBookingRequest " + carBookingRequest.toString());
            System.out.println("We are Good");
        } else {
            LOGGER.info(SLOT_NOT_AVAILABLE + carBookingRequest.toString());
            System.out.println("CHECK HERE !");
            throw new BookingProcessingExcpetion(SLOT_NOT_AVAILABLE + carBookingRequest.toString());
        }

    }

}
