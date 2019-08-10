package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.assertEquals;

public class CarAllotmentBuilderTest {

    private CarAllotmentBuilder carAllotmentBuilder;

    @Before
    public void setup() {
        carAllotmentBuilder = new CarAllotmentBuilder();
    }

    @Test
    public void testCreateCarAllotment() {
        Car car = getCar();
        Driver driver = getDriver();
        CarAllotment carAllotment = carAllotmentBuilder.createCarAllotment(car, driver);
        assertEquals(12, carAllotment.getDriverAvailableFrom().getHours());
        assertEquals(15, carAllotment.getDriverAvailableTo().getHours());
        assertEquals("John Doe", carAllotment.getDriverName());
        assertEquals("32324242", carAllotment.getDriverLicenseNumber());
        assertEquals(423l, carAllotment.getDriverId().longValue());
        assertEquals(1234l, carAllotment.getCarId().longValue());
        assertEquals("MH12LX6232", carAllotment.getCarRegNumber());
    }

    private Driver getDriver() {
        Driver driver = new Driver();
        driver.setName("John Doe");
        driver.setAvailableFrom(new Time(12, 00, 00));
        driver.setAvailableTo(new Time(15, 00, 00));
        driver.setLicenseNumber("32324242");
        driver.setId(423l);
        return driver;
    }

    private Car getCar() {
        Car car = new Car();
        car.setId(1234l);
        car.setMake("Chevrolet");
        car.setRegNumber("MH12LX6232");
        return car;
    }

}
