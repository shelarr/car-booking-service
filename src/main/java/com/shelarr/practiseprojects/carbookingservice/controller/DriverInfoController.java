package com.shelarr.practiseprojects.carbookingservice.controller;

import com.shelarr.practiseprojects.carbookingservice.dto.Driver;
import com.shelarr.practiseprojects.carbookingservice.service.DriverDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/v1/drivers")
@Api(value = "Spring Boot REST API", produces = "Driver CRUD REST API endpoints ")
public class DriverInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverInfoController.class);

    private static final String SUCCESS_RESPONSE = "{\"status\":\"success\"}";

    private static final String FAILURE_RESPONSE = "{\"status\":\"failure\"}";

    @Autowired
    private DriverDetailsService driverDetailsService;

    @RequestMapping(method = RequestMethod.GET, value = "/driverDetails/{licenseNumber}")
    @ApiOperation(httpMethod = "GET", value = "fetch driver information from system")
    public ResponseEntity<Driver> getDriverDetails(@ApiParam(value = "Driver License Number", required = true) @PathVariable("licenseNumber") String licenseNumber) {
        Driver driverDetails = driverDetailsService.getDriverDetails(licenseNumber);
        return new ResponseEntity<>(driverDetails, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addDriverDetails")
    @ApiOperation(httpMethod = "POST", value = "add driver information in a system")
    public ResponseEntity addDriverDetails(@RequestBody Driver driver) {
        if (isInValidRequestBody(driver)) {
            return ResponseEntity.ok("License Number is missing in request body.");
        }

        boolean insertionStatus = driverDetailsService.addDriverDetails(driver);
        if (insertionStatus) {
            return ResponseEntity.ok(SUCCESS_RESPONSE);
        } else {
            LOGGER.error("Failed to add a Driver information in a system. :- " + driver.toString());
            return ResponseEntity.ok(FAILURE_RESPONSE);
        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateDriverDetails")
    @ApiOperation(httpMethod = "PUT", value = "update Driver information in a system")
    public ResponseEntity updateDriverDetails(@RequestBody Driver driver) {
        if (isInValidRequestBody(driver)) {
            return ResponseEntity.ok("License Number is missing in request body.");
        }

        boolean updateStatus = driverDetailsService.updateDriverDetails(driver);
        if (updateStatus) {
            return ResponseEntity.ok(SUCCESS_RESPONSE);
        } else {
            LOGGER.error("Failed to update Driver information in a system :- " + driver.toString());
            return ResponseEntity.ok(FAILURE_RESPONSE);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteDriverDetails/{licenseNumber}")
    @ApiOperation(httpMethod = "DELETE", value = "Delete Driver information from a system")
    public ResponseEntity deleteDriverDetails(@ApiParam(value = "licenseNumber of a Driver", required = true) @PathVariable("licenseNumber") String licenseNumber) {
        boolean deleteStatus = driverDetailsService.deleteDriverDetails(licenseNumber);
        if (deleteStatus) {
            return ResponseEntity.ok(SUCCESS_RESPONSE);
        } else {
            LOGGER.error("Failed to delete a driver Information in a system with licenseNumber : " + licenseNumber);
            return ResponseEntity.ok(FAILURE_RESPONSE);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    @ApiOperation(httpMethod = "GET", value = "Get all driver details from a system")
    public ResponseEntity<List<Driver>> getAllDriverDetails() {
        List<Driver> drivers = driverDetailsService.getAllDriversDetails();
        return new ResponseEntity<>(drivers, new HttpHeaders(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex) {
        Map<String, String> er = new HashMap<>();
        er.put("FAILURE", ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isInValidRequestBody(Driver driver) {
        return StringUtils.isEmpty(driver.getName()) || StringUtils.isEmpty(driver.getLicenseNumber());
    }


}