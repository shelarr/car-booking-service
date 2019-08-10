package com.shelarr.practiseprojects.carbookingservice.request.validator;

import com.shelarr.practiseprojects.carbookingservice.dao.CarAllotmentsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import com.shelarr.practiseprojects.carbookingservice.exception.AllocationProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CarAvailabilityValidatorTest {

    @Mock
    private CarAllotmentsDao carAllotmentsDao;

    @InjectMocks
    private CarAvailabilityValidator validator = new CarAvailabilityValidator();

    private Car car;

    private Driver driver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        car = new Car();
        car.setId(123l);
        driver = new Driver();
        driver.setAvailableFrom(new Time(12, 00, 00));
        driver.setAvailableTo(new Time(18, 00, 00));
    }

    @Test
    public void testIsCarAvailableInTimeSlot() {
        List<CarAllotment> carAllotments = new ArrayList<>();
        CarAllotment carAllotment1 = getCarAllotment(new Long(123l), new Time(06, 00, 00),
                new Time(12, 00, 00));
        CarAllotment carAllotment2 = getCarAllotment(new Long(123l), new Time(01, 00, 00),
                new Time(05, 30, 00));
        carAllotments.add(carAllotment1);
        carAllotments.add(carAllotment2);
        when(carAllotmentsDao.getAllotmentsForCar(anyLong())).thenReturn(carAllotments);

        boolean isAvailable = validator.isCarAvailableInTimeSlot(car, driver);

        verify(carAllotmentsDao, times(1)).getAllotmentsForCar(anyLong());
        assertTrue(isAvailable);

    }

    @Test(expected = AllocationProcessingException.class)
    public void testIsCarAvailableInTimeSlot_WhenCarIsAllotedInRequestedTimeSlot() {
        List<CarAllotment> carAllotments = new ArrayList<>();
        CarAllotment carAllotment1 = getCarAllotment(123l, new Time(14, 00, 00),
                new Time(22, 00, 00));
        CarAllotment carAllotment2 = getCarAllotment(123l, new Time(01, 00, 00),
                new Time(05, 30, 00));
        carAllotments.add(carAllotment1);
        carAllotments.add(carAllotment2);
        when(carAllotmentsDao.getAllotmentsForCar(anyLong())).thenReturn(carAllotments);

        validator.isCarAvailableInTimeSlot(car, driver);
    }

    @Test
    public void testIsCarAvailableInTimeSlot_WhenCarNotAllotedEarlier() {
        List<CarAllotment> carAllotments = new ArrayList<>();
        when(carAllotmentsDao.getAllotmentsForCar(anyLong())).thenReturn(carAllotments);

        boolean isAvailable = validator.isCarAvailableInTimeSlot(car, driver);

        verify(carAllotmentsDao, times(1)).getAllotmentsForCar(anyLong());
        assertTrue(isAvailable);
    }

    private CarAllotment getCarAllotment(Long allotmentId, Time allotmentFrom, Time allotmentTo) {
        CarAllotment carAllotment = new CarAllotment();
        carAllotment.setId(allotmentId);
        carAllotment.setDriverAvailableFrom(allotmentFrom);
        carAllotment.setDriverAvailableTo(allotmentTo);
        return carAllotment;
    }

}
