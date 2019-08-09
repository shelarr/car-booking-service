package com.shelarr.practiseprojects.carbookingservice.request;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CarBookingRequest {

    private String driverId;

    private String userIdName;

    private Time bookingFrom;

    private Time bookingTo;

    public CarBookingRequest(String driverId, String userIdName, String bookingFrom, String bookingTo) throws ParseException {
        this.driverId = driverId;
        this.userIdName = userIdName;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");

        long bookingFromTime = sdf.parse(bookingFrom).getTime();
        this.bookingFrom = new Time(bookingFromTime);
        ;

        long bookingToTime = sdf.parse(bookingTo).getTime();
        this.bookingTo = new Time(bookingToTime);
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getUserIdName() {
        return userIdName;
    }

    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }

    public Time getBookingFrom() {
        return bookingFrom;
    }

    public void setBookingFrom(Time bookingFrom) {
        this.bookingFrom = bookingFrom;
    }

    public Time getBookingTo() {
        return bookingTo;
    }

    public void setBookingTo(Time bookingTo) {
        this.bookingTo = bookingTo;
    }

    @Override
    public String toString() {
        return "CarBookingRequest{" +
                "driverId='" + driverId + '\'' +
                ", userIdName='" + userIdName + '\'' +
                ", bookingFrom=" + bookingFrom +
                ", bookingTo=" + bookingTo +
                '}';
    }

}
