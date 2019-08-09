package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.dao.CarDetailsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarDetailsService {

    @Autowired
    private CarDetailsDao carDetailsDao;

    public Car getCarDetails(String regNumber) {
        return carDetailsDao.findByRegNumber(regNumber);
    }

    public Car getCarDetailsById(String regNumber) {
        return carDetailsDao.findByRegNumber(regNumber);
    }

    public List<Car> getAllCarsDetails() {
        return carDetailsDao.findAll();
    }

    public boolean addCarDetails(Car car) {
        return carDetailsDao.insert(car) != 0 ? true : false;
    }

    public boolean updateCarDetails(Car car) {
        return carDetailsDao.update(car) != 0 ? true : false;
    }

    public boolean deleteCarDetails(String regNumber) {
        return carDetailsDao.deleteByRegNumber(regNumber) != 0 ? true : false;
    }

}

