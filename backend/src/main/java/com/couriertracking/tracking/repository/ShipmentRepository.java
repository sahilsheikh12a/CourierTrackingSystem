package com.couriertracking.tracking.repository;

import com.couriertracking.tracking.entity.Shipment;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ShipmentRepository {
    private final Map<String, Shipment> shipments = new LinkedHashMap<>();

    public Shipment save(Shipment shipment) {
        shipments.put(shipment.getTrackingId(), shipment);
        return shipment;
    }

    public Optional<Shipment> findByTrackingId(String trackingId) {
        return Optional.ofNullable(shipments.get(trackingId));
    }
}
