package com.shelarr.practiseprojects.carbookingservice.dao.mapper;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarAllotmentRowMapper implements RowMapper<CarAllotment> {

    @Override
    public CarAllotment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        CarAllotment carAllotment = new CarAllotment();
        carAllotment.setId(resultSet.getLong("id"));
        carAllotment.setDriverId(resultSet.getLong("driverId"));
        carAllotment.setDriverName(resultSet.getString("driverName"));
        carAllotment.setDriverLicenseNumber(resultSet.getString("driverLicenseNumber"));
        carAllotment.setCarId(resultSet.getLong("carId"));
        carAllotment.setCarRegNumber(resultSet.getString("carRegNumber"));
        carAllotment.setDriverAvailableFrom(resultSet.getTime("driverAvailableFrom"));
        carAllotment.setDriverAvailableTo(resultSet.getTime("driverAvailableTo"));
        return carAllotment;
    }

}