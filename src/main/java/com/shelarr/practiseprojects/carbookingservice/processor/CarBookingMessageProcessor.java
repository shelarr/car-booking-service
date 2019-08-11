package com.shelarr.practiseprojects.carbookingservice.processor;

import com.shelarr.practiseprojects.carbookingservice.dao.CarBookingsDao;
import com.shelarr.practiseprojects.carbookingservice.databuilder.BookingDataBuilder;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;
import com.shelarr.practiseprojects.carbookingservice.request.validator.BookingRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CarBookingMessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBookingMessageProcessor.class);

    @Autowired
    CarBookingsDao carBookingsDao;

    @Resource(name = "requestValidators")
    private List<BookingRequestValidator> requestValidators;

    @Resource(name = "bookingDataBuilders")
    private List<BookingDataBuilder> bookingDataBuilders;

    public void processBooking(CarBookingMessage bookingMessage) {

        requestValidators.stream().forEach(
                bookingRequestValidator -> bookingRequestValidator.validate(bookingMessage)
        );

        bookingDataBuilders.stream().forEach(
                dataBuilder -> dataBuilder.populate(bookingMessage)
        );

        LOGGER.info("CarBooking Details: " + bookingMessage.toString());
        carBookingsDao.update(bookingMessage.getCarBooking());

    }
}
