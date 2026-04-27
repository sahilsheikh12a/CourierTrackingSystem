package com.couriertracking.status.mapper;

import com.couriertracking.shared.enums.ShipmentStatus;
import com.couriertracking.status.dto.StatusUpdateResponse;

import java.time.LocalDateTime;

public class StatusMapper {
    public StatusUpdateResponse toResponse(
            String trackingId,
            ShipmentStatus previousStatus,
            ShipmentStatus currentStatus,
            LocalDateTime updatedAt
    ) {
        return new StatusUpdateResponse(
                trackingId,
                previousStatus.displayName(),
                currentStatus.displayName(),
                updatedAt
        );
    }
}
