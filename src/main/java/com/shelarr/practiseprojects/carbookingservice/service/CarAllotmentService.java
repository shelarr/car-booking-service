package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.dao.CarAllotmentsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import com.shelarr.practiseprojects.carbookingservice.exception.DBServiceException;
import com.shelarr.practiseprojects.carbookingservice.databuilder.CarAllotmentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private CarAvailabilityValidator carAvailabilityValidator;

    @Resource
    private CarAllotmentBuilder carAllotmentBuilder;

    public void allotCarToDriver(String regNumber, String driverLicenseNo) {
        try {

            Car car = carDetailsService.getCarDetails(regNumber);
            Driver driver = driverDetailsService.getDriverDetails(driverLicenseNo);

            if (carAvailabilityValidator.isCarAvailableInTimeSlot(car, driver)) {
                CarAllotment carAllotment = carAllotmentBuilder.createCarAllotment(car, driver);
                carAllotmentsDao.insert(carAllotment);
            } else {
                throw new Exception("Driver's work time slot doesn't match with Car available time");
            }

        } catch (Exception ex) {
            String message = "Failure while updating allotment information in a system for regNumber : " + regNumber + " and driver License Number : " + driverLicenseNo;
            LOGGER.error(message + ex.getStackTrace());
            throw new DBServiceException(ex.getMessage());
        }

    }

    public List<CarAllotment> getAllDetails() {
        return carAllotmentsDao.findAll();
    }

    public boolean deleteCarAllotmentForDriver(String driverLicenseNumber) {
        return carAllotmentsDao.deleteByDriverLicenseNumber(driverLicenseNumber) != 0 ? true : false;
    }

    public CarAllotment getAllotMentDetails(String driverId) {
        return carAllotmentsDao.getAllotmentByDealerId(Long.valueOf(driverId));
    }
}
