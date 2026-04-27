package com.couriertracking.tracking.service;

import com.couriertracking.tracking.dto.TrackingResponse;
import com.couriertracking.tracking.mapper.TrackingMapper;
import com.couriertracking.tracking.repository.ShipmentRepository;
import com.couriertracking.tracking.repository.TrackingHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class TrackingService {
    private final ShipmentRepository shipmentRepository;
    private final TrackingHistoryRepository trackingHistoryRepository;
    private final TrackingMapper trackingMapper;

    public TrackingService(
            ShipmentRepository shipmentRepository,
            TrackingHistoryRepository trackingHistoryRepository,
            TrackingMapper trackingMapper
    ) {
        this.shipmentRepository = shipmentRepository;
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.trackingMapper = trackingMapper;
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
