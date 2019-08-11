package com.shelarr.practiseprojects.carbookingservice.messaging;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.*;

public class CarBookingMessageDispatcherTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private CarBookingMessageDispatcher dispatcher = new CarBookingMessageDispatcher();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDispatchForProcessing() {
        CarBookingMessage message = mock(CarBookingMessage.class);
        dispatcher.dispatchForProcessing(message);
        verify(jmsTemplate, times(1)).convertAndSend("bookingMessagesBuffer", message);
    }

}
