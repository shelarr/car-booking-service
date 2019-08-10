package com.shelarr.practiseprojects.carbookingservice.databuilder;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class DealerAndCarDataBuilderTest {

    @Mock
    private CarAllotmentService carAllotmentService;

    @InjectMocks
    private DealerAndCarDataBuilder builder = new DealerAndCarDataBuilder();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarAllotment carAllotment = createCarAllotment();
        Mockito.when(carAllotmentService.getAllotmentDetails("121313")).thenReturn(carAllotment);
    }

    @Test
    public void testPopulate() {
        CarBookingRequest request = CarBookingRequest.Builder
                .newInstance()
                .setDriverId("121313")
                .setUserIdName("534121")
                .setBookingFrom("14:00")
                .setBookingTo("17:00")
                .build();

        CarBooking bookingData = new CarBooking();
        builder.populate(request, bookingData);

        assertEquals(212l, bookingData.getCarId().longValue());
        assertEquals("MH23LK32323", bookingData.getCarRegNumber());
        assertEquals(31313l, bookingData.getDriverId().longValue());
        assertEquals("John Doe", bookingData.getDriverName());
        assertEquals("212113311", bookingData.getDriverLicenseNumber());
    }

    private CarAllotment createCarAllotment() {
        CarAllotment carAllotment = new CarAllotment();
        carAllotment.setId(212l);
        carAllotment.setCarId(212l);
        carAllotment.setCarRegNumber("MH23LK32323");
        carAllotment.setDriverLicenseNumber("212113311");
        carAllotment.setDriverName("John Doe");
        carAllotment.setDriverId(31313l);
        return carAllotment;
    }

}
