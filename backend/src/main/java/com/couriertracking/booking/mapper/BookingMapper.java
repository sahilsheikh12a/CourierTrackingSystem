package com.couriertracking.booking.mapper;

import com.couriertracking.booking.dto.BookingResponse;
import com.couriertracking.tracking.entity.Shipment;

public class BookingMapper {
    public BookingResponse toResponse(Shipment shipment) {
        return new BookingResponse(
                shipment.getTrackingId(),
                shipment.getCurrentStatus().displayName(),
                shipment.getParcel().getEstimatedCost()
        );
    }
}
