package com.couriertracking.tracking.repository;

import com.couriertracking.tracking.entity.TrackingHistory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TrackingHistoryRepository {
    private final Map<String, List<TrackingHistory>> historyStore = new LinkedHashMap<>();

    public void addHistory(TrackingHistory history) {
        historyStore.computeIfAbsent(history.getTrackingId(), key -> new ArrayList<>()).add(history);
    }

    public List<TrackingHistory> findByTrackingId(String trackingId) {
        return new ArrayList<>(historyStore.getOrDefault(trackingId, List.of()));
    }
}
