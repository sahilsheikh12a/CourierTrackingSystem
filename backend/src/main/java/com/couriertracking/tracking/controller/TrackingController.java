package com.couriertracking.tracking.controller;

import com.couriertracking.tracking.dto.TrackingResponse;
import com.couriertracking.tracking.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {
    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackingResponse> getTracking(@PathVariable String trackingId) {
        return ResponseEntity.ok(trackingService.getTracking(trackingId));
    }
}
