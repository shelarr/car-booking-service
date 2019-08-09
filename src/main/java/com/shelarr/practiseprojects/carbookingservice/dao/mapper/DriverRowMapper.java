package com.shelarr.practiseprojects.carbookingservice.dao.mapper;

import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverRowMapper implements RowMapper<Driver> {

    @Override
    public Driver mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Driver driver = new Driver();
        driver.setId(resultSet.getLong("id"));
        driver.setName(resultSet.getString("name"));
        driver.setAddress(resultSet.getString("address"));
        driver.setLicenseNumber(resultSet.getString("licenseNumber"));
        driver.setAvailableFrom(resultSet.getTime("availableFrom"));
        driver.setAvailableTo(resultSet.getTime("availableTo"));
        return driver;
    }

}
