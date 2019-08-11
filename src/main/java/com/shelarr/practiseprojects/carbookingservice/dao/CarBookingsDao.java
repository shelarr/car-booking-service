package com.shelarr.practiseprojects.carbookingservice.dao;

import com.shelarr.practiseprojects.carbookingservice.dao.mapper.CarBookingRowMapper;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarBookingsDao {

    private static final String CREATE_CAR_BOOKING = "insert into car_bookings " +
            "(userIdName, carId , carRegNumber, driverId, driverName, driverLicenseNumber, bookingFrom, bookingTo, " +
            "bookingCharge, isActive, bookingStatus) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CONFIRMED_CAR_BOOKING = "update car_bookings set " +
            "userIdName = ? , carId = ?, carRegNumber = ?, driverId = ?, driverName = ?, driverLicenseNumber = ?, bookingFrom = ?, bookingTo = ?," +
            "bookingCharge = ?, isActive= ?, bookingStatus = ? where id = ?";

    private static final String FIND_CAR_BOOKING = "select * from car_bookings where userIdName = ? and  isActive = ?";

    private static final String FIND_CAR_BOOKING_BY_ID = "select * from car_bookings where id = ?";

    private static final String FIND_ALL_ACTIVE_BOOKINGS = "select * from car_bookings where isActive = true";

    private static final String FIND_ALL_ACTIVE_BOOKINGS_FOR_DRIVER = "select * from car_bookings where driverId = ? and isActive = true";

    private static final String FIND_ALL_BOOKINGS = "select * from car_bookings";

    private static final String UPDATE_BOOKING_STATUS = "update car_bookings set bookingStatus = ?, isActive = ? where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insert(CarBooking carBooking) {
        return jdbcTemplate.update(CREATE_CAR_BOOKING,
                new Object[]{carBooking.getUserIdName(), carBooking.getCarId(), carBooking.getCarRegNumber(),
                        carBooking.getDriverId(), carBooking.getDriverName(), carBooking.getDriverLicenseNumber(),
                        carBooking.getBookingFrom(), carBooking.getBookingTo(), carBooking.getBookingCharge(),
                        carBooking.getActive(), carBooking.getBookingStatus()});
    }

    public int update(CarBooking carBooking) {
        return jdbcTemplate.update(UPDATE_CONFIRMED_CAR_BOOKING,
                new Object[]{carBooking.getUserIdName(), carBooking.getCarId(), carBooking.getCarRegNumber(),
                        carBooking.getDriverId(), carBooking.getDriverName(), carBooking.getDriverLicenseNumber(),
                        carBooking.getBookingFrom(), carBooking.getBookingTo(), carBooking.getBookingCharge(),
                        carBooking.getActive(), carBooking.getBookingStatus(), carBooking.getId()});
    }


    public CarBooking fetchBookingId(CarBooking carBooking) {
        return jdbcTemplate.queryForObject(FIND_CAR_BOOKING, new Object[]{carBooking.getUserIdName(), carBooking.getActive()},
                new BeanPropertyRowMapper<>(CarBooking.class));
    }

    public CarBooking findBookingById(Long bookingId) {
        return jdbcTemplate.queryForObject(FIND_CAR_BOOKING_BY_ID, new Object[]{bookingId},
                new BeanPropertyRowMapper<>(CarBooking.class));
    }

    public List<CarBooking> findAllActive() {
        return jdbcTemplate.query(FIND_ALL_ACTIVE_BOOKINGS, new CarBookingRowMapper());
    }

    public List<CarBooking> findAllActiveForDriver(Long driverId) {
        List<CarBooking> carBookings;
        try {
            carBookings =  jdbcTemplate.query(FIND_ALL_ACTIVE_BOOKINGS_FOR_DRIVER, new Object[]{driverId}, new CarBookingRowMapper());
        } catch (Exception ex) {
            carBookings = new ArrayList<>();
        }
        return carBookings;
    }

    public List<CarBooking> findAll() {
        return jdbcTemplate.query(FIND_ALL_BOOKINGS, new CarBookingRowMapper());
    }

    public int changeBookingStatus(String bookingId, String bookingStatus, boolean isActive) {
        return jdbcTemplate.update(UPDATE_BOOKING_STATUS, new Object[]{bookingStatus, isActive, bookingId});
    }

}
