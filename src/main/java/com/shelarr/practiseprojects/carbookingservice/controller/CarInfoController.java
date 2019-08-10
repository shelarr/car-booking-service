package com.shelarr.practiseprojects.carbookingservice.controller;

import com.shelarr.practiseprojects.carbookingservice.dto.Car;
import com.shelarr.practiseprojects.carbookingservice.service.CarDetailsService;
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

import static org.apache.logging.log4j.util.Strings.isEmpty;

@RestController
@RequestMapping(value = "/rest/v1/cars")
@Api(value = "Spring Boot REST API", produces = "Car CRUD REST API endpoints ")
public class CarInfoController {

    private static final String SUCCESS_RESPONSE = "{\"status\":\"success\"}";

    private static final String FAILURE_RESPONSE = "{\"status\":\"failure\"}";
    private static final Logger LOGGER = LoggerFactory.getLogger(CarInfoController.class);
    @Autowired
    private CarDetailsService carDetailsService;

    @RequestMapping(method = RequestMethod.GET, value = "/carDetails/{regNumber}")
    @ApiOperation(httpMethod = "GET", value = "fetch Car information from system")
    public ResponseEntity<Car> getCarDetails(@ApiParam(value = "Car Registration Number", required = true) @PathVariable("regNumber") String regNumber) {
        Car carDetails = carDetailsService.getCarDetails(regNumber);
        return new ResponseEntity<>(carDetails, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addCarDetails")
    @ApiOperation(httpMethod = "POST", value = "add Car information in a system")
    public ResponseEntity addCarDetails(@RequestBody Car car) {
        if (isInValidRequestBody(car)) {
            return ResponseEntity.ok("Registration Number is missing in request body.");
        }

        boolean insertionStatus = carDetailsService.addCarDetails(car);
        if (insertionStatus) {
            return ResponseEntity.ok(SUCCESS_RESPONSE);
        } else {
            LOGGER.error("Failed to add a car information in a system. :- " + car.toString());
            return ResponseEntity.ok(FAILURE_RESPONSE);
        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateCarDetails")
    @ApiOperation(httpMethod = "PUT", value = "update Car information in a system")
    public ResponseEntity updateCarDetails(@RequestBody Car car) {
        if (isInValidRequestBody(car)) {
            return ResponseEntity.ok("Registration Number is missing in request body.");
        }

        boolean updateStatus = carDetailsService.updateCarDetails(car);
        if (updateStatus) {
            return ResponseEntity.ok(SUCCESS_RESPONSE);
        } else {
            LOGGER.error("Failed to update car information in a system :- " + car.toString());
            return ResponseEntity.ok(FAILURE_RESPONSE);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteCarDetails/{regNumber}")
    @ApiOperation(httpMethod = "DELETE", value = "Delete Car information from a system")
    public ResponseEntity deleteCarDetails(@ApiParam(value = "Registration No. of a Car", required = true) @PathVariable("regNumber") String regNumber) {
        boolean deleteStatus = carDetailsService.deleteCarDetails(regNumber);
        if (deleteStatus) {
            return ResponseEntity.ok(SUCCESS_RESPONSE);
        } else {
            LOGGER.error("Failed to delete a car information in a system with Registration Number : " + regNumber);
            return ResponseEntity.ok(FAILURE_RESPONSE);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    @ApiOperation(httpMethod = "GET", value = "Get all car details from a system")
    public ResponseEntity<List<Car>> getAllCarDetails() {
        List<Car> cars = carDetailsService.getAllCarsDetails();
        return new ResponseEntity<>(cars, new HttpHeaders(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex) {
        Map<String, String> er = new HashMap<>();
        er.put("FAILURE", ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isInValidRequestBody(Car car) {
        return isEmpty(car.getRegNumber());
    }

}