package com.shelarr.practiseprojects.carbookingservice.service;

import com.shelarr.practiseprojects.carbookingservice.controller.CarAllotmentController;
import com.shelarr.practiseprojects.carbookingservice.dao.CarBookingsDao;
import com.shelarr.practiseprojects.carbookingservice.dto.BookingStatus;
import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.exception.BookingProcessingExcpetion;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessage;
import com.shelarr.practiseprojects.carbookingservice.messaging.CarBookingMessageDispatcher;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.util.mapper.CarBookingDataMapper;
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

    @Resource
    private CarBookingDataMapper carBookingDataMapper;

    @Resource(name = "carBookingMessageDispatcher")
    private CarBookingMessageDispatcher dispatcher;

    public String createBooking(CarBookingRequest request) {
        CarBooking carBooking = carBookingDataMapper.mapToDto(request);
        String bookingId = saveBookingRequestData(carBooking);
        CarBookingMessage bookingMessage = new CarBookingMessage(bookingId, carBooking);
        dispatcher.dispatchForProcessing(bookingMessage);
        return bookingId;
    }

    public List<CarBooking> getAllActiveBookings() {
        return carBookingsDao.findAllActive();
    }

    public List<CarBooking> getAllActiveBookingsForDriver(Long driverId) {
        return carBookingsDao.findAllActiveForDriver(driverId);
    }

    public List<CarBooking> getAllBookings() {
        return carBookingsDao.findAll();
    }

    public CarBooking getBookingById(String bookingId) {
        return carBookingsDao.findBookingById(Long.valueOf(bookingId));
    }

    public boolean changeBookingStatus(String bookingId, String bookingStatus) {

        if (!StringUtils.isEmpty(BookingStatus.getStatus(bookingStatus))) {
            boolean isActive = BookingStatus.getActive(bookingStatus);
            return carBookingsDao.changeBookingStatus(bookingId, bookingStatus, isActive) != 0 ? true : false;
        } else {
            throw new BookingProcessingExcpetion("Invalid Status !");
        }
    }

    private String saveBookingRequestData(CarBooking carBooking) {
        carBookingsDao.insert(carBooking);
        return String.valueOf(carBookingsDao.fetchBookingId(carBooking).getId());
    }

}
