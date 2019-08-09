package com.shelarr.practiseprojects.carbookingservice.controller;

import com.shelarr.practiseprojects.carbookingservice.dto.CarAllotment;
import com.shelarr.practiseprojects.carbookingservice.service.CarAllotmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/v1/carAllotments")
@Api(value = "Spring Boot REST API", produces = "Car allotments REST API endpoints ")
public class CarAllotmentController {

    private static final String SUCCESS_RESPONSE = "{\"status\":\"success\"}";

    private static final String FAILURE_RESPONSE = "{\"status\":\"failure\"}";

    @Resource
    private CarAllotmentService carAllotmentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CarAllotmentController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/allot/{regNumber}")
    @ApiOperation(httpMethod = "POST", value = "Allot a car present in a system to registered driver")
    public ResponseEntity allotCar(@ApiParam(value = "Registration Number of a Car", required = true) @PathVariable("regNumber") String regNumber,
                                   @ApiParam(value = "License Number of a Driver", required = true) @RequestParam("driverLicenseNo") String driverLicenseNo) {

        carAllotmentService.allotCarToDriver(regNumber, driverLicenseNo);
        return ResponseEntity.ok(SUCCESS_RESPONSE);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    @ApiOperation(httpMethod = "GET", value = "Get all car allotments details")
    public ResponseEntity getAllAllotmentDetails() {
        List<CarAllotment> carAllotments = carAllotmentService.getAllDetails();
        return new ResponseEntity<>(carAllotments, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAllotment/{driverLicenseNumber}")
    @ApiOperation(httpMethod = "DELETE", value = "Delete allotment for a Driver")
    public ResponseEntity deleteAllotmentDetails(@ApiParam(value = "delete allotment for a Driver", required = true) @PathVariable("driverLicenseNumber") String driverLicenseNumber) {
        boolean deleteStatus = carAllotmentService.deleteCarAllotmentForDriver(driverLicenseNumber);

        if (deleteStatus) {
            return ResponseEntity.ok(SUCCESS_RESPONSE);
        } else {
            LOGGER.error("Failed to delete a car allotment information in a system for  Driver with  license Id : " + driverLicenseNumber);
            return ResponseEntity.ok(FAILURE_RESPONSE);
        }
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex) {
        Map<String, String> er = new HashMap<>();
        er.put("FAILURE", ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}