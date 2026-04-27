package com.couriertracking.status.dto;

import java.time.LocalDateTime;

public class StatusUpdateResponse {
    private final String trackingId;
    private final String previousStatus;
    private final String currentStatus;
    private final LocalDateTime updatedAt;

    public StatusUpdateResponse(
            String trackingId,
            String previousStatus,
            String currentStatus,
            LocalDateTime updatedAt
    ) {
        this.trackingId = trackingId;
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
        this.updatedAt = updatedAt;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
