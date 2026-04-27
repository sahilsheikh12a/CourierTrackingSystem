package com.couriertracking.tracking.dto;

import com.couriertracking.tracking.entity.TrackingHistory;

import java.time.LocalDateTime;
import java.util.List;

public class TrackingResponse {
    private final String trackingId;
    private final String receiverName;
    private final String origin;
    private final String destination;
    private final String currentStatus;
    private final LocalDateTime bookedAt;
    private final List<TrackingHistory> history;

    public TrackingResponse(
            String trackingId,
            String receiverName,
            String origin,
            String destination,
            String currentStatus,
            LocalDateTime bookedAt,
            List<TrackingHistory> history
    ) {
        this.trackingId = trackingId;
        this.receiverName = receiverName;
        this.origin = origin;
        this.destination = destination;
        this.currentStatus = currentStatus;
        this.bookedAt = bookedAt;
        this.history = history;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public List<TrackingHistory> getHistory() {
        return history;
    }
}
