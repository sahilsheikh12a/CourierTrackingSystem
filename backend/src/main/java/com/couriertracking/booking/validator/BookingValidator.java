package com.couriertracking.booking.validator;

import com.couriertracking.booking.dto.BookingRequest;

public class BookingValidator {
    public void validate(BookingRequest request) {
        require(request.getSenderName(), "Sender name is required.");
        require(request.getSenderPhone(), "Sender phone is required.");
        require(request.getReceiverName(), "Receiver name is required.");
        require(request.getReceiverPhone(), "Receiver phone is required.");
        require(request.getOrigin(), "Origin is required.");
        require(request.getDestination(), "Destination is required.");
        require(request.getServiceType(), "Service type is required.");

        if (request.getWeightKg() <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero.");
        }

        if (request.getOrigin().equalsIgnoreCase(request.getDestination())) {
            throw new IllegalArgumentException("Origin and destination must be different.");
        }
    }

    private void require(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
