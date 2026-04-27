package com.couriertracking.shared.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public final class TrackingIdGenerator {
    private static final AtomicInteger COUNTER = new AtomicInteger(10000);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

    private TrackingIdGenerator() {
    }

    public static String nextTrackingId() {
        return "CTR-" + LocalDate.now().format(FORMATTER) + "-" + COUNTER.incrementAndGet();
    }
}
