package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dao.CarBookingsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DriverAllocationValidator implements BookingRequestValidator {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DriverAllocationValidator.class);

    private static final String INVALID_DRIVER_MESSAGE = "Requested driver dont have any alloted a Car.Please Try with another driver";

    private static final String DRIVER_ALREADY_BOOKED_MESSAGE = "Requested driver already Booked. Please Try with another driver";

    @Autowired
    private CarAllotmentService carAllotmentService;

    @Autowired
    private CarBookingsDao carBookingsDao;

    @Override
    public void validate(CarBookingRequest carBookingRequest) {
        String driverId = carBookingRequest.getDriverId();

        CarAllotment carAllotment = carAllotmentService.getAllotmentDetails(driverId);

        if (carAllotment == null) {
            LOGGER.info("Error while processing Booking request.  " + INVALID_DRIVER_MESSAGE);
            throw new BookingProcessingExcpetion(INVALID_DRIVER_MESSAGE);
        }

        CarBooking carBooking = carBookingsDao.findActiveBookingForDriver(Long.valueOf(driverId));

        if (carBooking != null && String.valueOf(carBooking.getDriverId()).equals(driverId)
                && carBooking.getActive()) {
            LOGGER.info("Error while processing Booking request.  " + DRIVER_ALREADY_BOOKED_MESSAGE);
            throw new BookingProcessingExcpetion(DRIVER_ALREADY_BOOKED_MESSAGE);
        }
    }

}
