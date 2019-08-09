package com.shelarr.practiseprojects.carbookingservice.databuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BookingDataBuilderConfig {

    @Bean
    public List<BookingDataBuilder> bookingDataBuilders() {
        List<BookingDataBuilder> bookingDataBuilders = new ArrayList<>();
        bookingDataBuilders.add(bookingTimingDataBuilder());
        bookingDataBuilders.add(dealerAndCarDataBuilder());
        bookingDataBuilders.add(defaultBookingDataBuilder());
        return bookingDataBuilders;
    }

    @Bean
    public BookingDataBuilder bookingTimingDataBuilder() {
        return new BookingTimingDataBuilder();
    }

    @Bean
    public BookingDataBuilder defaultBookingDataBuilder() {
        return new DefaultBookingDataBuilder();
    }

    @Bean
    public BookingDataBuilder dealerAndCarDataBuilder() {
        return new DealerAndCarDataBuilder();
    }


}
