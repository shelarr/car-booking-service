package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.springframework.beans.factory.annotation.Autowired;

public class DealerAndCarDataBuilder implements BookingDataBuilder {

    @Autowired
    private CarAllotmentService carAllotmentService;

    @Override
    public void populate(CarBookingMessage bookingMessage) {
        String driverId = String.valueOf(bookingMessage.getCarBooking().getDriverId());

        CarAllotment carAllotment = carAllotmentService.getAllotmentDetails(driverId);

        bookingMessage.getCarBooking().setDriverName(carAllotment.getDriverName());
        bookingMessage.getCarBooking().setDriverLicenseNumber(carAllotment.getDriverLicenseNumber());
        bookingMessage.getCarBooking().setCarId(carAllotment.getCarId());
        bookingMessage.getCarBooking().setCarRegNumber(carAllotment.getCarRegNumber());

    }

}
