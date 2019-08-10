package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.springframework.beans.factory.annotation.Autowired;

public class DealerAndCarDataBuilder implements BookingDataBuilder {

    @Autowired
    private CarAllotmentService carAllotmentService;

    @Override
    public void populate(CarBookingRequest request, CarBooking bookingData) {

        CarAllotment carAllotment = carAllotmentService.getAllotmentDetails(request.getDriverId());

        bookingData.setDriverId(carAllotment.getDriverId());
        bookingData.setDriverName(carAllotment.getDriverName());
        bookingData.setDriverLicenseNumber(carAllotment.getDriverLicenseNumber());
        bookingData.setCarId(carAllotment.getCarId());
        bookingData.setCarRegNumber(carAllotment.getCarRegNumber());

    }

}
