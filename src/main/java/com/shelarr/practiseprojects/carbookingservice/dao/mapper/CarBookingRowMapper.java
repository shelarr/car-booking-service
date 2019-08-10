package com.shelarr.practiseprojects.carbookingservice.dao.mapper;

import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarBookingRowMapper implements RowMapper<CarBooking> {

    @Override
    public CarBooking mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        CarBooking carbooking = new CarBooking();
        carbooking.setId(resultSet.getLong("id"));
        carbooking.setUserIdName(resultSet.getString("userIdName"));
        carbooking.setCarId(resultSet.getLong("carId"));
        carbooking.setCarRegNumber(resultSet.getString("carRegNumber"));
        carbooking.setDriverId(resultSet.getLong("driverId"));
        carbooking.setDriverName(resultSet.getString("driverName"));
        carbooking.setDriverLicenseNumber(resultSet.getString("driverLicenseNumber"));
        carbooking.setBookingFrom(resultSet.getTime("bookingFrom"));
        carbooking.setBookingTo(resultSet.getTime("bookingTo"));
        carbooking.setBookingCharge(resultSet.getBigDecimal("bookingCharge"));
        carbooking.setActive(resultSet.getBoolean("isActive"));
        carbooking.setBookingStatus(resultSet.getString("bookingStatus"));
        return carbooking;
    }

}