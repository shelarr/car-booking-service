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
        bookingDataBuilders.add(bookingChargeDataBuilder());
        bookingDataBuilders.add(dealerAndCarDataBuilder());
        return bookingDataBuilders;
    }

    @Bean
    public BookingDataBuilder bookingChargeDataBuilder() {
        return new BookingChargeDataBuilder();
    }

    @Bean
    public BookingDataBuilder dealerAndCarDataBuilder() {
        return new DealerAndCarDataBuilder();
    }


}
