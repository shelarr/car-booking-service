package com.shelarr.practiseprojects.carbookingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Time;

public class Driver {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String name;

    private String address;

    private String licenseNumber;

    @ApiModelProperty(required = true, example = "05:00:00")
    @JsonFormat(pattern = "HH:MM:SS")
    private Time availableFrom;


    @ApiModelProperty(required = true, example = "14:00:22")
    @JsonFormat(pattern = "HH:MM:SS")
    private Time availableTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Time getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Time availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Time getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Time availableTo) {
        this.availableTo = availableTo;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", availableFrom=" + availableFrom +
                ", availableTo=" + availableTo +
                '}';
    }
}
