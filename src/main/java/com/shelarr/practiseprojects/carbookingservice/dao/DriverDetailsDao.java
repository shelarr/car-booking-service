package com.shelarr.practiseprojects.carbookingservice.dao;

import com.shelarr.practiseprojects.carbookingservice.dao.mapper.DriverRowMapper;
import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverDetailsDao {

    private static final String FIND_BY_LICENSE_NUMBER = "select * from drivers where licenseNumber=?";

    private static final String FIND_BY_ID = "select * from drivers where id=?";

    private static final String FIND_ALL_DRIVERS = "select * from drivers";

    private static final String INSERT_DRIVER = "insert into drivers (name, address, licenseNumber, availableFrom, availableTo) values (?, ?, ?, ?, ?)";

    private static final String UPDATE_DRIVER = "update drivers set name = ?, address = ?, availableFrom=?, availableTo=?  where licenseNumber = ?";

    private static final String DELETE_BY_LICENSE_NUMBER = "delete from drivers where licenseNumber=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Driver findByLicenseNumber(String licenseNumber) {
        return jdbcTemplate.queryForObject(FIND_BY_LICENSE_NUMBER, new Object[]{licenseNumber},
                new BeanPropertyRowMapper<>(Driver.class));
    }

    public List<Driver> findAll() {
        return jdbcTemplate.query(FIND_ALL_DRIVERS, new DriverRowMapper());
    }

    public int insert(Driver driver) {
        return jdbcTemplate.update(INSERT_DRIVER,
                new Object[]{driver.getName(), driver.getAddress(), driver.getLicenseNumber(), driver.getAvailableFrom(), driver.getAvailableTo()});
    }

    public int update(Driver driver) {
        return jdbcTemplate.update(UPDATE_DRIVER,
                new Object[]{driver.getName(), driver.getAddress(), driver.getAvailableFrom(), driver.getAvailableTo(), driver.getLicenseNumber()});

    }

    public int deleteByLicenseNumber(String licenseNumber) {
        return jdbcTemplate.update(DELETE_BY_LICENSE_NUMBER, new Object[]{licenseNumber});
    }


    public Driver findById(String id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{Long.valueOf(id)},
                new BeanPropertyRowMapper<>(Driver.class));
    }
}
