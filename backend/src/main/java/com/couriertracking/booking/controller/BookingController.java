package com.couriertracking.booking.controller;

import com.couriertracking.booking.dto.BookingRequest;
import com.couriertracking.booking.dto.BookingResponse;
import com.couriertracking.booking.service.BookingService;

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookingResponse createBooking(BookingRequest request) {
        return bookingService.createBooking(request);
    }
}
