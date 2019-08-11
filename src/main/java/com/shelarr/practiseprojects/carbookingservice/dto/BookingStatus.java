package com.shelarr.practiseprojects.carbookingservice.dto;

public enum BookingStatus {

    CONFIRMED("Confirmed", true),
    IN_PROGRESS("In Progress", false),
    STARTED("Started", true),
    ENDED("Ended", false),
    INVOICED("Invoiced", true),
    PAYMENT_RECEIVED("Payment Received", true),
    CANCELLED("Cancelled", false),
    FAILED("Failed", false);

    private String status;

    private boolean isActive;

    BookingStatus(String status, boolean isActive) {
        this.status = status;
        this.isActive = isActive;
    }

    public static String getStatus(String status) {
        for (BookingStatus bd : BookingStatus.values()) {
            if (bd.getStatus().equalsIgnoreCase(status)) {
                return bd.getStatus();
            }
        }
        return "";
    }

    public static boolean getActive(String status) {
        for (BookingStatus bd : BookingStatus.values()) {
            if (bd.getStatus().equalsIgnoreCase(status)) {
                return bd.getActive();
            }
        }
        return false;
    }

    public String getStatus() {
        return status;
    }

    public boolean getActive() {
        return isActive;
    }

}
