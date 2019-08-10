package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.dao.DriverDetailsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class DriverDetailsServiceTest {

    @Mock
    private DriverDetailsDao driverDetailsDao;

    @InjectMocks
    private DriverDetailsService driverDetailsService = new DriverDetailsService();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDriverDetails() {
        Driver driver = mock(Driver.class);
        when(driverDetailsDao.findByLicenseNumber("1213434")).thenReturn(driver);

        Driver driverResponse = driverDetailsService.getDriverDetails("1213434");

        Assert.assertEquals(driver, driverResponse);
        verify(driverDetailsDao, times(1)).findByLicenseNumber("1213434");
    }

    @Test
    public void testGetDriverDetailsById() {
        Driver driver = mock(Driver.class);
        when(driverDetailsDao.findById("121")).thenReturn(driver);

        Driver driverResponse = driverDetailsService.getDriverDetailsById("121");

        Assert.assertEquals(driver, driverResponse);
        verify(driverDetailsDao, times(1)).findById("121");
    }

    @Test
    public void testGetAllDriversDetails() {
        List<Driver> drivers = mock(List.class);
        when(driverDetailsDao.findAll()).thenReturn(drivers);

        List<Driver> fetchedDrivers = driverDetailsService.getAllDriversDetails();

        Assert.assertEquals(drivers, fetchedDrivers);
        verify(driverDetailsDao, times(1)).findAll();
    }

    @Test
    public void testAddDriverDetails() {
        Driver driver = mock(Driver.class);
        when(driverDetailsDao.insert(driver)).thenReturn(1);

        boolean success = driverDetailsService.addDriverDetails(driver);

        assertTrue(success);
        verify(driverDetailsDao, times(1)).insert(driver);
    }

    @Test
    public void testUpdateDriverDetails() {
        Driver driver = mock(Driver.class);
        when(driverDetailsDao.update(driver)).thenReturn(1);

        boolean success = driverDetailsService.updateDriverDetails(driver);

        assertTrue(success);
        verify(driverDetailsDao, times(1)).update(driver);
    }

    @Test
    public void testDeleteDriverDetails() {
        when(driverDetailsDao.deleteByLicenseNumber("123")).thenReturn(1);

        boolean success = driverDetailsService.deleteDriverDetails("123");

        assertTrue(success);
        verify(driverDetailsDao, times(1)).deleteByLicenseNumber("123");
    }

}
