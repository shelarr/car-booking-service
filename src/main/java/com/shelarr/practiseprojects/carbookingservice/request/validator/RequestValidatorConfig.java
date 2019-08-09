package com.shelarr.practiseprojects.carbookingservice.request.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RequestValidatorConfig {

    @Bean
    public List<BookingRequestValidator> requestValidators() {
        List<BookingRequestValidator> requestValidators = new ArrayList<>();
        requestValidators.add(driverValidator());
        requestValidators.add(bookingTimeAvailabilityValidator());
        return requestValidators;
    }

    @Bean
    public BookingRequestValidator driverValidator() {
        return new DriverAllocationValidator();
    }

    @Bean
    public BookingRequestValidator bookingTimeAvailabilityValidator() {
        return new BookingTimeAvailabilityValidator();
    }

}
