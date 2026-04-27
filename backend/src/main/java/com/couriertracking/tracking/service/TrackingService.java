package com.couriertracking.tracking.service;

import com.couriertracking.tracking.dto.TrackingResponse;
import com.couriertracking.tracking.mapper.TrackingMapper;
import com.couriertracking.tracking.repository.ShipmentRepository;
import com.couriertracking.tracking.repository.TrackingHistoryRepository;

public class TrackingService {
    private final ShipmentRepository shipmentRepository;
    private final TrackingHistoryRepository trackingHistoryRepository;
    private final TrackingMapper trackingMapper = new TrackingMapper();

    public TrackingService(
            ShipmentRepository shipmentRepository,
            TrackingHistoryRepository trackingHistoryRepository
    ) {
        this.shipmentRepository = shipmentRepository;
        this.trackingHistoryRepository = trackingHistoryRepository;
    }

    public TrackingResponse getTracking(String trackingId) {
        return shipmentRepository.findByTrackingId(trackingId)
                .map(shipment -> trackingMapper.toResponse(
                        shipment,
                        trackingHistoryRepository.findByTrackingId(trackingId)
                ))
                .orElseThrow(() -> new IllegalArgumentException("Tracking ID not found: " + trackingId));
    }
}
