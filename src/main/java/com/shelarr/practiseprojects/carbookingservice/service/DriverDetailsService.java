package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.dao.DriverDetailsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DriverDetailsService {

    @Autowired
    private DriverDetailsDao driverDetailsDao;

    public Driver getDriverDetails(String regNumber) {
        return driverDetailsDao.findByLicenseNumber(regNumber);
    }

    public Driver getDriverDetailsById(String id) {
        return driverDetailsDao.findById(id);
    }

    public List<Driver> getAllDriversDetails() {
        return driverDetailsDao.findAll();
    }

    public boolean addDriverDetails(Driver driver) {
        return driverDetailsDao.insert(driver) != 0 ? true : false;
    }

    public boolean updateDriverDetails(Driver driver) {
        return driverDetailsDao.update(driver) != 0 ? true : false;
    }

    public boolean deleteDriverDetails(String regNumber) {
        return driverDetailsDao.deleteByLicenseNumber(regNumber) != 0 ? true : false;
    }


}

