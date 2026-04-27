package com.couriertracking.tracking.entity;

import com.couriertracking.booking.entity.Parcel;
import com.couriertracking.shared.enums.ShipmentStatus;

import java.time.LocalDateTime;

public class Shipment {
    private String trackingId;
    private Parcel parcel;
    private ShipmentStatus currentStatus;
    private LocalDateTime bookedAt;

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public ShipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ShipmentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}
