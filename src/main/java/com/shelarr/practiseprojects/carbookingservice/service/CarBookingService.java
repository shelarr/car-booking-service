package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.controller.CarAllotmentController;
import com.shelarr.practiseprojects.carbookingservice.dao.CarBookingsDao;
import com.shelarr.practiseprojects.carbookingservice.databuilder.BookingDataBuilder;
import com.shelarr.practiseprojects.carbookingservice.dto.BookingStatus;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.request.validator.BookingRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CarBookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarAllotmentController.class);

    @Autowired
    private CarBookingsDao carBookingsDao;

    @Resource(name = "requestValidators")
    private List<BookingRequestValidator> requestValidators;

    @Resource(name = "bookingDataBuilders")
    private List<BookingDataBuilder> bookingDataBuilders;

    public String createBooking(CarBookingRequest request) {
        try {
            requestValidators.stream().forEach(
                    bookingRequestValidator -> bookingRequestValidator.validate(request)
            );

            CarBooking carBooking = new CarBooking();

            bookingDataBuilders.stream().forEach(
                    dataBuilder -> dataBuilder.populate(request, carBooking)
            );

            LOGGER.info("CarBooking Details: " + carBooking.toString());
            carBookingsDao.insert(carBooking);
            return String.valueOf(carBookingsDao.fetchBookingId(carBooking).getId());
        } catch (Exception ex) {
            LOGGER.error("Booking Request can not be processed at specified time for Driver. Reason : " + ex.getMessage());
            throw new BookingProcessingExcpetion("Booking Request can not be processed at specified time for Driver. Reason : " + ex.getMessage());
        }
    }

    public List<CarBooking> getAllBookings() {
        return carBookingsDao.findAll();
    }

    public boolean changeBookingStatus(String bookingId, String bookingStatus) {
        if (!StringUtils.isEmpty(BookingStatus.getStatus(bookingStatus))) {
            return carBookingsDao.changeBookingStatus(bookingId, bookingStatus) != 0 ? true : false;
        } else {
            throw new BookingProcessingExcpetion("Invalid Status !");
        }
    }

}
