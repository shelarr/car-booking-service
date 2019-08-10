package com.shelarr.practiseprojects.carbookingservice.dao.mapper;

import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarRowMapperTest {

    private CarRowMapper rowMapper;

    @Before
    public void setup() {
        rowMapper = new CarRowMapper();
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getLong("id")).thenReturn(2231l);
        when(resultSet.getString("regNumber")).thenReturn("MH21LO879");
        when(resultSet.getString("make")).thenReturn("Chevrolet");
        when(resultSet.getString("model")).thenReturn("Cruz");
        when(resultSet.getString("modelYear")).thenReturn("2019");

        Car car = rowMapper.mapRow(resultSet, 3);

        assertEquals(2231l, car.getId().longValue());
        assertEquals("MH21LO879", car.getRegNumber());
        assertEquals("Chevrolet", car.getMake());
        assertEquals("Cruz", car.getModel());
        assertEquals("2019", car.getModelYear());
    }
}
