package com.couriertracking.booking.service;

import com.couriertracking.booking.dto.BookingRequest;
import com.couriertracking.booking.dto.BookingResponse;
import com.couriertracking.booking.entity.Customer;
import com.couriertracking.booking.entity.Parcel;
import com.couriertracking.booking.mapper.BookingMapper;
import com.couriertracking.booking.repository.ParcelRepository;
import com.couriertracking.booking.validator.BookingValidator;
import com.couriertracking.shared.enums.ShipmentStatus;
import com.couriertracking.shared.util.TrackingIdGenerator;
import com.couriertracking.tracking.entity.Shipment;
import com.couriertracking.tracking.entity.TrackingHistory;
import com.couriertracking.tracking.repository.ShipmentRepository;
import com.couriertracking.tracking.repository.TrackingHistoryRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {
    private final ParcelRepository parcelRepository;
    private final ShipmentRepository shipmentRepository;
    private final TrackingHistoryRepository trackingHistoryRepository;
    private final BookingValidator bookingValidator;
    private final BookingMapper bookingMapper;

    public BookingService(
            ParcelRepository parcelRepository,
            ShipmentRepository shipmentRepository,
            TrackingHistoryRepository trackingHistoryRepository,
            BookingValidator bookingValidator,
            BookingMapper bookingMapper
    ) {
        this.parcelRepository = parcelRepository;
        this.shipmentRepository = shipmentRepository;
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.bookingValidator = bookingValidator;
        this.bookingMapper = bookingMapper;
    }

    public BookingResponse createBooking(BookingRequest request) {
        bookingValidator.validate(request);

        String trackingId = TrackingIdGenerator.nextTrackingId();
        LocalDateTime now = LocalDateTime.now();

        Parcel parcel = new Parcel();
        parcel.setTrackingId(trackingId);
        parcel.setSender(new Customer(request.getSenderName(), request.getSenderPhone()));
        parcel.setReceiver(new Customer(request.getReceiverName(), request.getReceiverPhone()));
        parcel.setOrigin(request.getOrigin());
        parcel.setDestination(request.getDestination());
        parcel.setWeightKg(request.getWeightKg());
        parcel.setServiceType(request.getServiceType());
        parcel.setEstimatedCost(calculateCost(request.getWeightKg(), request.getServiceType()));
        parcelRepository.save(parcel);

        Shipment shipment = new Shipment();
        shipment.setTrackingId(trackingId);
        shipment.setParcel(parcel);
        shipment.setCurrentStatus(ShipmentStatus.BOOKED);
        shipment.setBookedAt(now);
        shipmentRepository.save(shipment);

        trackingHistoryRepository.addHistory(
                new TrackingHistory(trackingId, ShipmentStatus.BOOKED, "Parcel booked successfully.", now)
        );

        return bookingMapper.toResponse(shipment);
    }

    private double calculateCost(double weightKg, String serviceType) {
        double baseRate = 60.0;
        double weightCharge = weightKg * 35.0;
        double expressCharge = "express".equalsIgnoreCase(serviceType) ? 80.0 : 0.0;
        return baseRate + weightCharge + expressCharge;
    }
}
