package com.shelarr.practiseprojects.carbookingservice.dao.mapper;

import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DriverRowMapperTest {


    private DriverRowMapper rowMapper;

    @Before
    public void setup() {
        rowMapper = new DriverRowMapper();
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        Time fromTime = mock(Time.class);
        Time toTime = mock(Time.class);

        when(resultSet.getLong("id")).thenReturn(2231l);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("address")).thenReturn("Bay Area, Pune");
        when(resultSet.getString("licenseNumber")).thenReturn("11242412");
        when(resultSet.getTime("availableFrom")).thenReturn(fromTime);
        when(resultSet.getTime("availableTo")).thenReturn(toTime);

        Driver driver = rowMapper.mapRow(resultSet, 3);

        assertEquals(2231l, driver.getId().longValue());
        assertEquals("John Doe", driver.getName());
        assertEquals("Bay Area, Pune", driver.getAddress());
        assertEquals("11242412", driver.getLicenseNumber());
        assertEquals(fromTime, driver.getAvailableFrom());
        assertEquals(toTime, driver.getAvailableTo());
    }

}
