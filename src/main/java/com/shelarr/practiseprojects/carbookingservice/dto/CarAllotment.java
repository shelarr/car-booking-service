package com.shelarr.practiseprojects.carbookingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Time;

public class CarAllotment {

    @ApiModelProperty(hidden = true)
    private Long id;

    private Long driverId;

    private Long carId;

    private String driverName;

    private String driverLicenseNumber;

    @ApiModelProperty(required = true, example = "05:00:00")
    @JsonFormat(pattern = "HH:MM:SS")
    private Time driverAvailableFrom;

    @ApiModelProperty(required = true, example = "12:00:00")
    @JsonFormat(pattern = "HH:MM:SS")
    private Time driverAvailableTo;

    private String carRegNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public Time getDriverAvailableFrom() {
        return driverAvailableFrom;
    }

    public void setDriverAvailableFrom(Time driverAvailableFrom) {
        this.driverAvailableFrom = driverAvailableFrom;
    }

    public Time getDriverAvailableTo() {
        return driverAvailableTo;
    }

    public void setDriverAvailableTo(Time driverAvailableTo) {
        this.driverAvailableTo = driverAvailableTo;
    }

    public String getCarRegNumber() {
        return carRegNumber;
    }

    public void setCarRegNumber(String carRegNumber) {
        this.carRegNumber = carRegNumber;
    }

    @Override
    public String toString() {
        return "CarAllotment{" +
                "id=" + id +
                ", driverId=" + driverId +
                ", carId=" + carId +
                ", driverName='" + driverName + '\'' +
                ", driverLicenseNumber='" + driverLicenseNumber + '\'' +
                ", driverAvailableFrom=" + driverAvailableFrom +
                ", driverAvailableTo=" + driverAvailableTo +
                ", carRegNumber='" + carRegNumber + '\'' +
                '}';
    }
}
