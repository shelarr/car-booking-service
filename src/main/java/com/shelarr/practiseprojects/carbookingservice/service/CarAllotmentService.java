package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.dao.CarAllotmentsDao;
import com.shelarr.practiseprojects.carbookingservice.databuilder.CarAllotmentBuilder;
import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import com.shelarr.practiseprojects.carbookingservice.exception.DBServiceException;
import com.shelarr.practiseprojects.carbookingservice.request.validator.CarAvailabilityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarAllotmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarAllotmentService.class);

    @Autowired
    private CarAllotmentsDao carAllotmentsDao;

    @Autowired
    private CarDetailsService carDetailsService;

    @Autowired
    private DriverDetailsService driverDetailsService;

    @Autowired
    private CarAvailabilityValidator carAvailabilityValidator;

    @Autowired
    private CarAllotmentBuilder carAllotmentBuilder;

    public void allotCarToDriver(String regNumber, String driverLicenseNo) {
        try {

            Car car = carDetailsService.getCarDetails(regNumber);
            Driver driver = driverDetailsService.getDriverDetails(driverLicenseNo);
            if (carAvailabilityValidator.isCarAvailableInTimeSlot(car, driver)) {
                CarAllotment carAllotment = carAllotmentBuilder.createCarAllotment(car, driver);
                carAllotmentsDao.insert(carAllotment);
            }

        } catch (Exception ex) {
            String message = "Failure while updating allotment information in a system for regNumber : " + regNumber + " and driver License Number : " + driverLicenseNo;
            LOGGER.error(message + ex.getStackTrace());
            throw new DBServiceException(ex.getMessage());
        }

    }

    public List<CarAllotment> getAllAllotmentDetails() {
        return carAllotmentsDao.findAll();
    }

    public boolean deleteCarAllotmentForDriver(String driverLicenseNumber) {
        return carAllotmentsDao.deleteByDriverLicenseNumber(driverLicenseNumber) != 0 ? true : false;
    }

    public CarAllotment getAllotmentDetails(String driverId) {
        CarAllotment carAllotment = null;
        try {
            carAllotment = carAllotmentsDao.getAllotmentByDealerId(Long.valueOf(driverId));
        } catch (Exception e) {
        }
        return carAllotment;
    }

}
