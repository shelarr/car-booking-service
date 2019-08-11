package com.shelarr.practiseprojects.carbookingservice.request;

import java.sql.Time;

public final class CarBookingRequest {

    private final String driverId;

    private final String userIdName;

    private final Time bookingFrom;

    private final Time bookingTo;

    public CarBookingRequest(Builder builder) {
        this.driverId = builder.driverId;
        this.userIdName = builder.userIdName;
        this.bookingFrom = convertToTime(builder.bookingFrom);
        this.bookingTo = convertToTime(builder.bookingTo);
    }

    public String getDriverId() {
        return driverId;
    }

    public String getUserIdName() {
        return userIdName;
    }

    public Time getBookingFrom() {
        return bookingFrom;
    }

    public Time getBookingTo() {
        return bookingTo;
    }

    private Time convertToTime(String timeString) {
        try {
            return Time.valueOf(timeString);
        } catch (Exception ex) {
            throw new RuntimeException("Invalid format for booking time");
        }
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

    public static class Builder {

        private String driverId;

        private String userIdName;

        private String bookingFrom;

        private String bookingTo;

        private Builder() {
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder setDriverId(String driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder setUserIdName(String userIdName) {
            this.userIdName = userIdName;
            return this;
        }

        public Builder setBookingFrom(String bookingFrom) {
            this.bookingFrom = bookingFrom;
            return this;
        }

        public Builder setBookingTo(String bookingTo) {
            this.bookingTo = bookingTo;
            return this;
        }

        public CarBookingRequest build() {
            return new CarBookingRequest(this);
        }

    }

}
