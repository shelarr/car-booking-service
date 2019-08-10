package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dao.CarAllotmentsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import com.shelarr.practiseprojects.carbookingservice.exception.AllocationProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class CarAvailabilityValidator {

    private static final String ALLOCATION_SLOT_NOT_AVAILABLE = "Car is already allocated to another driver in available time slot";

    @Autowired
    private CarAllotmentsDao carAllotmentsDao;

    public boolean isCarAvailableInTimeSlot(Car car, Driver driver) {

        List<CarAllotment> carAllotments = carAllotmentsDao.getAllotmentsForCar(car.getId());

        if (carAllotments.size() == 0) {
            return true;
        }

        for (CarAllotment carAllotment : carAllotments) {

            LocalTime carAllotmentFrom = carAllotment.getDriverAvailableFrom().toLocalTime();
            LocalTime carAllotmentTo = carAllotment.getDriverAvailableTo().toLocalTime();
            LocalTime driverAvailableFrom = driver.getAvailableFrom().toLocalTime();
            LocalTime driverAvailableTo = driver.getAvailableTo().toLocalTime();

            boolean isOverLapping = checKForOverlap(carAllotmentFrom, carAllotmentTo,
                    driverAvailableFrom, driverAvailableTo);

            if (isOverLapping) {
                throw new AllocationProcessingException(ALLOCATION_SLOT_NOT_AVAILABLE);
            }

        }

        return true;
    }

    private boolean checKForOverlap(LocalTime carAllotmentFrom, LocalTime carAllotmentTo,
                                    LocalTime driverAvailableFrom, LocalTime driverAvailableTo) {
        return (driverAvailableFrom.isAfter(carAllotmentFrom) && driverAvailableFrom.isBefore(carAllotmentTo)) ||
                (driverAvailableTo.isAfter(carAllotmentFrom) && driverAvailableTo.isBefore(carAllotmentTo));

    }
}
