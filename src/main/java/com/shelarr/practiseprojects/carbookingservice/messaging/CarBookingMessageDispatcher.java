package com.shelarr.practiseprojects.carbookingservice.messaging;

import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class CarBookingMessageDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBookingMessageDispatcher.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void dispatchForProcessing(CarBookingMessage bookingMessage) {
        try {

            jmsTemplate.convertAndSend("bookingMessagesBuffer", bookingMessage);
            LOGGER.info("published a car booking message. + ");

        } catch (Exception ex) {
            throw new BookingProcessingExcpetion("Exception while publishing message from Dispatcher " + ex.getMessage());
        }

    }
}
