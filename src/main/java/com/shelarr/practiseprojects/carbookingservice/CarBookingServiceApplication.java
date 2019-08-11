package com.shelarr.practiseprojects.carbookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJms
public class CarBookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarBookingServiceApplication.class, args);
    }

}
