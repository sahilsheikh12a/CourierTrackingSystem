package com.couriertracking.booking.repository;

import com.couriertracking.booking.entity.Parcel;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ParcelRepository {
    private final Map<String, Parcel> parcels = new LinkedHashMap<>();

    public Parcel save(Parcel parcel) {
        parcels.put(parcel.getTrackingId(), parcel);
        return parcel;
    }

    public Optional<Parcel> findByTrackingId(String trackingId) {
        return Optional.ofNullable(parcels.get(trackingId));
    }
}
