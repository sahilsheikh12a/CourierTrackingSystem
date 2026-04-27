package com.couriertracking.tracking.controller;

import com.couriertracking.tracking.dto.TrackingResponse;
import com.couriertracking.tracking.service.TrackingService;

public class TrackingController {
    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    public TrackingResponse getTracking(String trackingId) {
        return trackingService.getTracking(trackingId);
    }
}
