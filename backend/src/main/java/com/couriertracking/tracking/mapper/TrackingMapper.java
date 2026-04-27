package com.couriertracking.tracking.mapper;

import com.couriertracking.tracking.dto.TrackingResponse;
import com.couriertracking.tracking.entity.Shipment;
import com.couriertracking.tracking.entity.TrackingHistory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackingMapper {
    public TrackingResponse toResponse(Shipment shipment, List<TrackingHistory> history) {
        return new TrackingResponse(
                shipment.getTrackingId(),
                shipment.getParcel().getReceiver().getName(),
                shipment.getParcel().getOrigin(),
                shipment.getParcel().getDestination(),
                shipment.getCurrentStatus().displayName(),
                shipment.getBookedAt(),
                history
        );
    }
}
