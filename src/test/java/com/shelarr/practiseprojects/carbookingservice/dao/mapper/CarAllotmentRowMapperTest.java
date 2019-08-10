package com.shelarr.practiseprojects.carbookingservice.dao.mapper;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarAllotmentRowMapperTest {

    private CarAllotmentRowMapper rowMapper;


    @Before
    public void setup() {
        rowMapper = new CarAllotmentRowMapper();
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        Time fromTime = mock(Time.class);
        Time toTime = mock(Time.class);

        when(resultSet.getLong("id")).thenReturn(2231l);
        when(resultSet.getLong("driverId")).thenReturn(432231l);
        when(resultSet.getString("driverName")).thenReturn("John Doe");
        when(resultSet.getString("driverLicenseNumber")).thenReturn("11242412");
        when(resultSet.getLong("carId")).thenReturn(9231l);
        when(resultSet.getString("carRegNumber")).thenReturn("MH21LO879");
        when(resultSet.getTime("driverAvailableFrom")).thenReturn(fromTime);
        when(resultSet.getTime("driverAvailableTo")).thenReturn(toTime);

        CarAllotment carAllotment = rowMapper.mapRow(resultSet, 3);

        assertEquals(2231l, carAllotment.getId().longValue());
        assertEquals(432231l, carAllotment.getDriverId().longValue());
        assertEquals("John Doe", carAllotment.getDriverName());
        assertEquals("11242412", carAllotment.getDriverLicenseNumber());
        assertEquals(9231l, carAllotment.getCarId().longValue());
        assertEquals("MH21LO879", carAllotment.getCarRegNumber());
        assertEquals(fromTime, carAllotment.getDriverAvailableFrom());
        assertEquals(toTime, carAllotment.getDriverAvailableTo());
    }


}
