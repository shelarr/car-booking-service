package com.shelarr.practiseprojects.carbookingservice.messaging;

import com.shelarr.practiseprojects.carbookingservice.processor.CarBookingMessageProcessor;
import com.shelarr.practiseprojects.carbookingservice.service.CarBookingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class BookingMessageReceiverTest {

    @Mock
    private CarBookingMessageProcessor processor;

    @Mock
    private CarBookingService carBookingService;

    @InjectMocks
    private BookingMessageReceiver receiver = new BookingMessageReceiver();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReceiveMessage() {
        CarBookingMessage message = mock(CarBookingMessage.class);
        receiver.receiveMessage(message);
        verify(processor, times(1)).processBooking(message);
    }


}
