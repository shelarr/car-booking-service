package com.shelarr.practiseprojects.carbookingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.sql.Time;

public class CarBooking {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String userIdName;

    private Long carId;

    private String carRegNumber;

    private Long driverId;

    private String driverName;

    private String driverLicenseNumber;

    @ApiModelProperty(required = true, example = "14:00:00")
    @JsonFormat(pattern = "HH:MM:SS")
    private Time bookingFrom;

    @ApiModelProperty(required = true, example = "16:00:00")
    @JsonFormat(pattern = "HH:MM:SS")
    private Time bookingTo;

    private BigDecimal bookingCharge;

    private Boolean isActive;

    private String bookingStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserIdName() {
        return userIdName;
    }

    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarRegNumber() {
        return carRegNumber;
    }

    public void setCarRegNumber(String carRegNumber) {
        this.carRegNumber = carRegNumber;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
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

    public BigDecimal getBookingCharge() {
        return bookingCharge;
    }

    public void setBookingCharge(BigDecimal bookingCharge) {
        this.bookingCharge = bookingCharge;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "CarBooking{" +
                "id=" + id +
                ", userIdName='" + userIdName + '\'' +
                ", carId=" + carId +
                ", carRegNumber='" + carRegNumber + '\'' +
                ", driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", driverLicenseNumber='" + driverLicenseNumber + '\'' +
                ", bookingFrom=" + bookingFrom +
                ", bookingTo=" + bookingTo +
                ", bookingCharge=" + bookingCharge +
                ", isActive=" + isActive +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }

}
