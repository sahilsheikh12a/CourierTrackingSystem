package com.couriertracking.tracking.entity;

import com.couriertracking.shared.enums.ShipmentStatus;

import java.time.LocalDateTime;

public class TrackingHistory {
    private String trackingId;
    private ShipmentStatus status;
    private String remarks;
    private LocalDateTime updatedAt;

    public TrackingHistory() {
    }

    public TrackingHistory(String trackingId, ShipmentStatus status, String remarks, LocalDateTime updatedAt) {
        this.trackingId = trackingId;
        this.status = status;
        this.remarks = remarks;
        this.updatedAt = updatedAt;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
