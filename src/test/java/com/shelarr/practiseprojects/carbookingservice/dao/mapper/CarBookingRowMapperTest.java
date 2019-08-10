package com.shelarr.practiseprojects.carbookingservice.dao.mapper;

import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarBookingRowMapperTest {

    private CarBookingRowMapper rowMapper;

    @Before
    public void setup() {
        rowMapper = new CarBookingRowMapper();
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        Time fromTime = mock(Time.class);
        Time toTime = mock(Time.class);
        BigDecimal bookingCharge = mock(BigDecimal.class);

        when(resultSet.getLong("id")).thenReturn(2231l);
        when(resultSet.getString("userIdName")).thenReturn("User1");
        when(resultSet.getLong("driverId")).thenReturn(432231l);
        when(resultSet.getString("driverName")).thenReturn("John Doe");
        when(resultSet.getString("driverLicenseNumber")).thenReturn("11242412");
        when(resultSet.getLong("carId")).thenReturn(9231l);
        when(resultSet.getString("carRegNumber")).thenReturn("MH21LO879");
        when(resultSet.getTime("bookingFrom")).thenReturn(fromTime);
        when(resultSet.getTime("bookingTo")).thenReturn(toTime);
        when(resultSet.getBigDecimal("bookingCharge")).thenReturn(bookingCharge);
        when(resultSet.getString("bookingStatus")).thenReturn("CONFIRMED");
        when(resultSet.getBoolean("isActive")).thenReturn(true);

        CarBooking carBooking = rowMapper.mapRow(resultSet, 3);

        assertEquals(2231l, carBooking.getId().longValue());
        assertEquals(432231l, carBooking.getDriverId().longValue());
        assertEquals("John Doe", carBooking.getDriverName());
        assertEquals("11242412", carBooking.getDriverLicenseNumber());
        assertEquals(9231l, carBooking.getCarId().longValue());
        assertEquals("MH21LO879", carBooking.getCarRegNumber());
        assertEquals(fromTime, carBooking.getBookingFrom());
        assertEquals(toTime, carBooking.getBookingTo());
        assertEquals(bookingCharge, carBooking.getBookingCharge());
        assertEquals("CONFIRMED", carBooking.getBookingStatus());
        assertTrue(carBooking.getActive());
    }

}