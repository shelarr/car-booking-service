package com.shelarr.practiseprojects.carbookingservice.dto;

public enum BookingStatus {

    CONFIRMED("Confirmed"),
    STARTED("Started"),
    ENDED("Ended"),
    INVOICED("Invoiced"),
    PAYMENT_RECEIVED("Payment Received"),
    CANCELLED("Cancelled");

    private String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public static String getStatus(String status) {
        for (BookingStatus bd : BookingStatus.values()) {
            if (bd.getStatus().equalsIgnoreCase(status)) {
                return bd.getStatus();
            }
        }
        return "";
    }

    public String getStatus() {
        return status;
    }

}
