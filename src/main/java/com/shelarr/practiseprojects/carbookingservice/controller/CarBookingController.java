package com.shelarr.practiseprojects.carbookingservice.controller;

import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;
import com.shelarr.practiseprojects.carbookingservice.request.CarBookingRequest;
import com.shelarr.practiseprojects.carbookingservice.service.CarBookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/v1/carBookings")
@Api(value = "Spring Boot REST API", produces = "Car Booking REST API endpoints ")
public class CarBookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBookingController.class);

    private static final String SUCCESS_RESPONSE = "{\"status\":\"success\"}";

    private static final String FAILURE_RESPONSE = "{\"status\":\"failure\"}";

    private static final String BOOKING__SUCCESS_MESSAGE = "Booking is processed !. BookingId for future reference : ";

    private static final String CHECK_FOR_CONFIRMATION = "Check here for Confirmation status.";
    private static final String baseDomainUrl = "http://localhost:8080/";

    @Autowired
    private CarBookingService carBookingService;

    //    Booking by driverId, since requirement is to book a Driver.
    @RequestMapping(method = RequestMethod.POST, value = "/bookACar/{driverId}")
    @ApiOperation(httpMethod = "POST", value = "Books a Driver for Specified Time")
    public ResponseEntity bookCar(@ApiParam(value = "Driver Id", required = true) @PathVariable("driverId") String driverId,
                                  @ApiParam(value = "UserId Name", required = true) @RequestParam("userIdName") String userIdName,
                                  @ApiParam(value = "Booking From (HH:MM:SS)", defaultValue = "14:00:00", required = true) @RequestParam("bookingFrom") String bookingFrom,
                                  @ApiParam(value = "Booking To (HH:MM:SS)", defaultValue = "16:30:00", required = true) @RequestParam("bookingTo") String bookingTo) {

        CarBookingRequest carBookingRequest = CarBookingRequest.Builder
                .newInstance()
                .setDriverId(driverId)
                .setUserIdName(userIdName)
                .setBookingFrom(bookingFrom)
                .setBookingTo(bookingTo)
                .build();

        String bookingId = carBookingService.createBooking(carBookingRequest);
        Map<String, String> response = new HashMap<>();

        response.put("SUCCESS", BOOKING__SUCCESS_MESSAGE + bookingId);
        String confirmation_Link = createConfirmationLink(bookingId);
        response.put("CHECK_FOR_CONFIRMATION", confirmation_Link);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    @ApiOperation(httpMethod = "GET", value = "fetches all Bookings, if active=false")
    public ResponseEntity getAllBookings(@ApiParam(value = "active") @RequestParam(required = false) boolean active) {
        List<CarBooking> activeBookings;
        if (active) {
            activeBookings = carBookingService.getAllActiveBookings();
        } else {
            activeBookings = carBookingService.getAllBookings();
        }
        return new ResponseEntity<>(activeBookings, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getDetails/{bookingId}")
    @ApiOperation(httpMethod = "GET", value = "fetch details of a Booking (active or inactive)")
    public ResponseEntity getBookingDetails(@ApiParam(value = "Booking Id", required = true) @PathVariable("bookingId") String bookingId) {
        CarBooking bookingDetails = carBookingService.getBookingById(bookingId);
        return new ResponseEntity<>(bookingDetails, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changeBookingStatus/{bookingId}")
    @ApiOperation(httpMethod = "POST", value = "change BookingStatus of Booking ")
    public ResponseEntity changeBookingStatus(@ApiParam(value = "Booking Id", required = true) @PathVariable("bookingId") String bookingId,
                                              @ApiParam(value = "Booking Status", required = true) @RequestParam("bookingStatus") String bookingStatus) {

        boolean statusChangeSuccessful = carBookingService.changeBookingStatus(bookingId, bookingStatus);
        if (statusChangeSuccessful) {
            return ResponseEntity.ok(SUCCESS_RESPONSE);
        } else {
            LOGGER.error("Failed to change a Booking status " + bookingStatus + " for Booing Id :- " + bookingId);
            return ResponseEntity.ok(FAILURE_RESPONSE);
        }
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex) {
        Map<String, String> er = new HashMap<>();
        er.put("FAILURE", ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String createConfirmationLink(String bookingId) {
        return baseDomainUrl + "rest/v1/carBookings/getDetails/" + bookingId;
    }


}