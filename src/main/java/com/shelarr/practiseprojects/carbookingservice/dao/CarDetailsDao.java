package com.shelarr.practiseprojects.carbookingservice.dao;

import com.shelarr.practiseprojects.carbookingservice.dao.mapper.CarRowMapper;
import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDetailsDao {

    private static final String FIND_BY_ID = "select * from cars where id=?";

    private static final String FIND_BY_REG_NUMBER = "select * from cars where regNumber=?";

    private static final String FIND_ALL_CARS = "select * from cars";

    private static final String DELETE_BY_ID = "delete from cars where id=?";

    private static final String DELETE_BY_REG_NUMBER = "delete from cars where regNumber=?";

    private static final String INSERT_CAR = "insert into cars (make, model, modelYear, regNumber) values (?, ?, ?, ?)";

    private static final String UPDATE_CAR = "update cars  set make = ?, model = ?, modelYear = ? where regNumber = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Car> findAll() {
        return jdbcTemplate.query(FIND_ALL_CARS, new CarRowMapper());
    }

    public Car findById(long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id},
                new BeanPropertyRowMapper<>(Car.class));
    }

    public Car findByRegNumber(String regNumber) {
        return jdbcTemplate.queryForObject(FIND_BY_REG_NUMBER, new Object[]{regNumber},
                new BeanPropertyRowMapper<>(Car.class));
    }

    public int deleteById(long id) {
        return jdbcTemplate.update(DELETE_BY_ID, new Object[]{id});
    }

    public int deleteByRegNumber(String regNumber) {
        return jdbcTemplate.update(DELETE_BY_REG_NUMBER, new Object[]{regNumber});
    }

    public int insert(Car car) {
        return jdbcTemplate.update(INSERT_CAR,
                new Object[]{car.getMake(), car.getModel(), car.getModelYear(), car.getRegNumber()});
    }

    public int update(Car car) {
        return jdbcTemplate.update(UPDATE_CAR,
                new Object[]{car.getMake(), car.getModel(), car.getModelYear(), car.getRegNumber()});
    }

}
