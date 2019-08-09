package com.shelarr.practiseprojects.carbookingservice.dao;

import com.shelarr.practiseprojects.carbookingservice.dao.mapper.CarAllotmentRowMapper;
import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarAllotmentsDao {

    private static final String INSERT_CAR_ALLOTMENT_ENTRY = "insert into car_allotments (driverId, carId , driverName, driverLicenseNumber, carRegNumber, driverAvailableFrom, driverAvailableTo) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_ALL_CAR_ALLOTMENTS = "select * from car_allotments";

    private static final String FIND_CAR_ALLOTMENTS_FOR_CAR = "select * from car_allotments where carId = ?";

    private static final String FIND_CAR_ALLOTMENT_FOR_DRIVER = "select * from car_allotments where driverId = ?";

    private static final String DELETE_ALLOTMENT_BY_DRIVER = "delete from car_allotments where driverLicenseNumber = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insert(CarAllotment carAllotment) {
        return jdbcTemplate.update(INSERT_CAR_ALLOTMENT_ENTRY,
                new Object[]{carAllotment.getDriverId(), carAllotment.getCarId(),
                        carAllotment.getDriverName(), carAllotment.getDriverLicenseNumber(), carAllotment.getCarRegNumber(),
                        carAllotment.getDriverAvailableFrom(), carAllotment.getDriverAvailableTo()});
    }

    public List<CarAllotment> findAll() {
        return jdbcTemplate.query(FIND_ALL_CAR_ALLOTMENTS, new CarAllotmentRowMapper());
    }

    public int deleteByDriverLicenseNumber(String driverLicenseNumber) {
        return jdbcTemplate.update(DELETE_ALLOTMENT_BY_DRIVER, new Object[]{driverLicenseNumber});
    }

    public List<CarAllotment> getAllotmentsForCar(Long carId) {
        return jdbcTemplate.query(FIND_CAR_ALLOTMENTS_FOR_CAR, new Object[]{carId}, new CarAllotmentRowMapper());
    }

    public CarAllotment getAllotmentByDealerId(Long driverId) {
        return jdbcTemplate.queryForObject(FIND_CAR_ALLOTMENT_FOR_DRIVER, new Object[]{driverId},
                new BeanPropertyRowMapper<>(CarAllotment.class));
    }
}
