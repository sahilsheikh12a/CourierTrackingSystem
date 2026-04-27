package com.couriertracking.status.mapper;

import com.couriertracking.shared.enums.ShipmentStatus;
import com.couriertracking.status.dto.StatusUpdateResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
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
