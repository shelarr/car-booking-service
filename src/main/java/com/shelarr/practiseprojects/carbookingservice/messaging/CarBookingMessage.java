package com.shelarr.practiseprojects.carbookingservice.messaging;

import com.shelarr.practiseprojects.carbookingservice.dto.CarBooking;

public class CarBookingMessage {

    private String bookingId;

    private CarBooking carBooking;

    public CarBookingMessage(){
        this.carBooking = new CarBooking();
    }

    public CarBookingMessage(String bookingId, CarBooking carBooking) {
        this.bookingId = bookingId;
        this.carBooking = carBooking;
        this.getCarBooking().setId(Long.valueOf(bookingId));
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public CarBooking getCarBooking() {
        return carBooking;
    }

    public void setCarBooking(CarBooking carBooking) {
        this.carBooking = carBooking;
    }

    @Override
    public String toString() {
        return "CarBookingMessage{" +
                "bookingId='" + bookingId + '\'' +
                ", carBooking=" + carBooking +
                '}';
    }


}
