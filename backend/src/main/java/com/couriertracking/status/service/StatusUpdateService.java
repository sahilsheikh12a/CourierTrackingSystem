package com.couriertracking.status.service;

import com.couriertracking.shared.enums.ShipmentStatus;
import com.couriertracking.status.dto.StatusUpdateRequest;
import com.couriertracking.status.dto.StatusUpdateResponse;
import com.couriertracking.status.mapper.StatusMapper;
import com.couriertracking.status.validator.StatusValidator;
import com.couriertracking.tracking.entity.Shipment;
import com.couriertracking.tracking.entity.TrackingHistory;
import com.couriertracking.tracking.repository.ShipmentRepository;
import com.couriertracking.tracking.repository.TrackingHistoryRepository;

import java.time.LocalDateTime;

public class StatusUpdateService {
    private final ShipmentRepository shipmentRepository;
    private final TrackingHistoryRepository trackingHistoryRepository;
    private final StatusValidator statusValidator;
    private final StatusMapper statusMapper;

    public StatusUpdateService(
            ShipmentRepository shipmentRepository,
            TrackingHistoryRepository trackingHistoryRepository,
            StatusValidator statusValidator,
            StatusMapper statusMapper
    ) {
        this.shipmentRepository = shipmentRepository;
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.statusValidator = statusValidator;
        this.statusMapper = statusMapper;
    }

    public StatusUpdateResponse updateStatus(StatusUpdateRequest request) {
        Shipment shipment = shipmentRepository.findByTrackingId(request.getTrackingId())
                .orElseThrow(() -> new IllegalArgumentException("Tracking ID not found: " + request.getTrackingId()));

        ShipmentStatus newStatus = ShipmentStatus.fromText(request.getNewStatus());
        ShipmentStatus previousStatus = shipment.getCurrentStatus();
        statusValidator.validateTransition(previousStatus, newStatus);

        shipment.setCurrentStatus(newStatus);
        shipmentRepository.save(shipment);

        LocalDateTime updatedAt = LocalDateTime.now();
        String remarks = (request.getRemarks() == null || request.getRemarks().isBlank())
                ? "Status updated by operator."
                : request.getRemarks().trim();

        trackingHistoryRepository.addHistory(
                new TrackingHistory(request.getTrackingId(), newStatus, remarks, updatedAt)
        );

        return statusMapper.toResponse(request.getTrackingId(), previousStatus, newStatus, updatedAt);
    }
}
