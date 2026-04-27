package com.couriertracking.status.validator;

import com.couriertracking.shared.enums.ShipmentStatus;

public class StatusValidator {
    public void validateTransition(ShipmentStatus currentStatus, ShipmentStatus newStatus) {
        boolean valid = switch (currentStatus) {
            case BOOKED -> newStatus == ShipmentStatus.PICKED_UP || newStatus == ShipmentStatus.CANCELLED;
            case PICKED_UP -> newStatus == ShipmentStatus.IN_TRANSIT || newStatus == ShipmentStatus.DELAYED;
            case IN_TRANSIT -> newStatus == ShipmentStatus.ARRIVED_AT_HUB || newStatus == ShipmentStatus.DELAYED;
            case ARRIVED_AT_HUB -> newStatus == ShipmentStatus.OUT_FOR_DELIVERY || newStatus == ShipmentStatus.RETURNED;
            case OUT_FOR_DELIVERY -> newStatus == ShipmentStatus.DELIVERED || newStatus == ShipmentStatus.RETURNED;
            case DELAYED -> newStatus == ShipmentStatus.IN_TRANSIT || newStatus == ShipmentStatus.OUT_FOR_DELIVERY;
            case DELIVERED, RETURNED, CANCELLED -> false;
        };

        if (!valid) {
            throw new IllegalArgumentException(
                    "Invalid transition from " + currentStatus.displayName() + " to " + newStatus.displayName() + "."
            );
        }
    }
}
