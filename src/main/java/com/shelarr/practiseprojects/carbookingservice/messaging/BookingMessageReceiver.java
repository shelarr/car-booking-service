package com.shelarr.practiseprojects.carbookingservice.messaging;

import com.shelarr.practiseprojects.carbookingservice.dto.BookingStatus;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.processor.CarBookingMessageProcessor;
import com.shelarr.practiseprojects.carbookingservice.service.CarBookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class BookingMessageReceiver {

    @Autowired
    private CarBookingMessageProcessor processor;

    @Autowired
    private CarBookingService carBookingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingMessageReceiver.class);

    @JmsListener(destination = "bookingMessagesBuffer", containerFactory = "internalMessageFactory")
    public void receiveMessage(CarBookingMessage bookingMessage) {
        try {
            System.out.println("Received <" + bookingMessage + ">");
            LOGGER.info("Received booking Message for Processing" + bookingMessage);
            processor.processBooking(bookingMessage);
        } catch (Exception ex) {
            carBookingService.changeBookingStatus(bookingMessage.getBookingId(), BookingStatus.FAILED.getStatus());
            LOGGER.error("Booking Request can not be processed at specified time for Driver. Reason : " + ex.getCause() + ex.getMessage() + ex.getStackTrace());
            throw new BookingProcessingExcpetion("Booking Request can not be processed at specified time for Driver. Reason : " + ex.getCause() + ex.getMessage() + ex.getStackTrace());
        }

    }

}
