package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.dao.CarDetailsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CarDetailsServiceTest {

    @Mock
    private CarDetailsDao carDetailsDao;

    @InjectMocks
    private CarDetailsService carDetailsService = new CarDetailsService();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCarDetails() {
        Car car = mock(Car.class);
        when(carDetailsDao.findByRegNumber("MH21LD3434")).thenReturn(car);

        Car carResponse = carDetailsService.getCarDetails("MH21LD3434");

        Assert.assertEquals(car, carResponse);
        verify(carDetailsDao, times(1)).findByRegNumber("MH21LD3434");
    }

    @Test
    public void testGetDetailsById() {
        Car car = mock(Car.class);
        when(carDetailsDao.findById(anyLong())).thenReturn(car);

        Car carResponse = carDetailsService.getCarDetailsById("121");

        Assert.assertEquals(car, carResponse);
        verify(carDetailsDao, times(1)).findById(anyLong());
    }

    @Test
    public void testGetAllCarsDetails() {
        List<Car> cars = mock(List.class);
        when(carDetailsDao.findAll()).thenReturn(cars);

        List<Car> fetchedCars = carDetailsService.getAllCarsDetails();

        Assert.assertEquals(cars, fetchedCars);
        verify(carDetailsDao, times(1)).findAll();
    }

    @Test
    public void testAddCarDetails() {
        Car car = mock(Car.class);
        when(carDetailsDao.insert(car)).thenReturn(1);

        boolean success = carDetailsService.addCarDetails(car);

        assertTrue(success);
        verify(carDetailsDao, times(1)).insert(car);
    }

    @Test
    public void testUpdateCarDetails() {
        Car car = mock(Car.class);
        when(carDetailsDao.update(car)).thenReturn(1);

        boolean success = carDetailsService.updateCarDetails(car);

        assertTrue(success);
        verify(carDetailsDao, times(1)).update(car);
    }

    @Test
    public void testDeleteCarDetails() {
        when(carDetailsDao.deleteByRegNumber("MH12LG9808")).thenReturn(1);

        boolean success = carDetailsService.deleteCarDetails("MH12LG9808");

        assertTrue(success);
        verify(carDetailsDao, times(1)).deleteByRegNumber("MH12LG9808");
    }

}
