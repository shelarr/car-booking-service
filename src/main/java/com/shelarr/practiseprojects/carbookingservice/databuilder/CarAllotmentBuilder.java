package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import org.springframework.stereotype.Component;

@Component
public class CarAllotmentBuilder {

    public CarAllotment createCarAllotment(Car car, Driver driver) {
        CarAllotment carAllotment = new CarAllotment();
        carAllotment.setCarId(car.getId());
        carAllotment.setDriverId(driver.getId());
        carAllotment.setDriverName(driver.getName());
        carAllotment.setDriverLicenseNumber(driver.getLicenseNumber());
        carAllotment.setDriverAvailableFrom(driver.getAvailableFrom());
        carAllotment.setDriverAvailableTo(driver.getAvailableTo());
        carAllotment.setCarRegNumber(car.getRegNumber());
        return carAllotment;
    }

}
