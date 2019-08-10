package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.dao.CarAllotmentsDao;
import com.shelarr.practiseprojects.carbookingservice.databuilder.CarAllotmentBuilder;
import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import com.shelarr.practiseprojects.carbookingservice.request.validator.CarAvailabilityValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CarAllotmentServiceTest {

    @Mock
    private CarAllotmentsDao carAllotmentsDao;

    @Mock
    private CarDetailsService carDetailsService;

    @Mock
    private DriverDetailsService driverDetailsService;

    @Mock
    private CarAvailabilityValidator carAvailabilityValidator;

    @Mock
    private CarAllotmentBuilder carAllotmentBuilder;

    @InjectMocks
    private CarAllotmentService carAllotmentService = new CarAllotmentService();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAllotCarToDriver() {
        Car car = mock(Car.class);
        Driver driver = mock(Driver.class);
        CarAllotment carAllotment = mock(CarAllotment.class);
        when(carDetailsService.getCarDetails("MH45LK9876")).thenReturn(car);
        when(driverDetailsService.getDriverDetails("21321444")).thenReturn(driver);
        when(carAvailabilityValidator.isCarAvailableInTimeSlot(car, driver)).thenReturn(true);
        when(carAllotmentBuilder.createCarAllotment(car, driver)).thenReturn(carAllotment);

        carAllotmentService.allotCarToDriver("MH45LK9876", "21321444");

        verify(carDetailsService, times(1)).getCarDetails("MH45LK9876");
        verify(driverDetailsService, times(1)).getDriverDetails("21321444");
        verify(carAvailabilityValidator, times(1)).isCarAvailableInTimeSlot(car, driver);
        verify(carAllotmentBuilder, times(1)).createCarAllotment(car, driver);
        verify(carAllotmentsDao, times(1)).insert(carAllotment);
    }

    @Test
    public void testAllotCarToDriver_WhenCarIsNotAvailableInDriverSlot() {
        Car car = mock(Car.class);
        Driver driver = mock(Driver.class);
        CarAllotment carAllotment = mock(CarAllotment.class);
        when(carDetailsService.getCarDetails("MH45LK9876")).thenReturn(car);
        when(driverDetailsService.getDriverDetails("21321444")).thenReturn(driver);
        when(carAvailabilityValidator.isCarAvailableInTimeSlot(car, driver)).thenReturn(false);

        carAllotmentService.allotCarToDriver("MH45LK9876", "21321444");

        verify(carDetailsService, times(1)).getCarDetails("MH45LK9876");
        verify(driverDetailsService, times(1)).getDriverDetails("21321444");
        verify(carAvailabilityValidator, times(1)).isCarAvailableInTimeSlot(car, driver);
        verify(carAllotmentBuilder, times(0)).createCarAllotment(car, driver);
        verify(carAllotmentsDao, times(0)).insert(carAllotment);
    }

    @Test
    public void testGetAllAllotmentDetails() {
        List<CarAllotment> allotments = mock(List.class);
        when(carAllotmentsDao.findAll()).thenReturn(allotments);

        List<CarAllotment> fetchedAllotments = carAllotmentService.getAllAllotmentDetails();

        assertEquals(allotments, fetchedAllotments);
        verify(carAllotmentsDao, times(1)).findAll();
    }

    @Test
    public void testGetAllotmentDetailsForDriver() {
        CarAllotment carAllotment = mock(CarAllotment.class);
        when(carAllotmentsDao.getAllotmentByDealerId(anyLong())).thenReturn(carAllotment);

        CarAllotment fetchedCarAllotment = carAllotmentService.getAllotmentDetails("1123");

        assertEquals(carAllotment, fetchedCarAllotment);
        verify(carAllotmentsDao, times(1)).getAllotmentByDealerId(anyLong());
    }

    @Test
    public void testDeleteAllotmentForDriver() {
        when(carAllotmentsDao.deleteByDriverLicenseNumber("131313")).thenReturn(1);

        boolean success = carAllotmentService.deleteCarAllotmentForDriver("131313");

        assertTrue(success);
        verify(carAllotmentsDao, times(1)).deleteByDriverLicenseNumber("131313");
    }

}
