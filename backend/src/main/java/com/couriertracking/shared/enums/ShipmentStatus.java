package com.couriertracking.shared.enums;

import java.util.Locale;

public enum ShipmentStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    ARRIVED_AT_HUB,
    OUT_FOR_DELIVERY,
    DELIVERED,
    DELAYED,
    RETURNED,
    CANCELLED;

    public static ShipmentStatus fromText(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Status is required.");
        }

        String normalized = value.trim()
                .toUpperCase(Locale.ROOT)
                .replace(' ', '_');

        return ShipmentStatus.valueOf(normalized);
    }

    public String displayName() {
        return switch (this) {
            case BOOKED -> "Booked";
            case PICKED_UP -> "Picked Up";
            case IN_TRANSIT -> "In Transit";
            case ARRIVED_AT_HUB -> "Arrived At Hub";
            case OUT_FOR_DELIVERY -> "Out For Delivery";
            case DELIVERED -> "Delivered";
            case DELAYED -> "Delayed";
            case RETURNED -> "Returned";
            case CANCELLED -> "Cancelled";
        };
    }
}
