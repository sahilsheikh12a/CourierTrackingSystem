package com.couriertracking.booking.dto;

public class BookingResponse {
    private String trackingId;
    private String currentStatus;
    private double estimatedCost;

    public BookingResponse(String trackingId, String currentStatus, double estimatedCost) {
        this.trackingId = trackingId;
        this.currentStatus = currentStatus;
        this.estimatedCost = estimatedCost;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }
}
