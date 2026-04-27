package com.couriertracking;

import com.couriertracking.booking.controller.BookingController;
import com.couriertracking.booking.dto.BookingRequest;
import com.couriertracking.booking.dto.BookingResponse;
import com.couriertracking.booking.repository.ParcelRepository;
import com.couriertracking.booking.service.BookingService;
import com.couriertracking.booking.validator.BookingValidator;
import com.couriertracking.status.controller.StatusController;
import com.couriertracking.status.dto.StatusUpdateRequest;
import com.couriertracking.status.dto.StatusUpdateResponse;
import com.couriertracking.status.mapper.StatusMapper;
import com.couriertracking.status.service.StatusUpdateService;
import com.couriertracking.status.validator.StatusValidator;
import com.couriertracking.tracking.controller.TrackingController;
import com.couriertracking.tracking.dto.TrackingResponse;
import com.couriertracking.tracking.repository.ShipmentRepository;
import com.couriertracking.tracking.repository.TrackingHistoryRepository;
import com.couriertracking.tracking.service.TrackingService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CourierTrackingApplication {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    public static void main(String[] args) {
        ParcelRepository parcelRepository = new ParcelRepository();
        ShipmentRepository shipmentRepository = new ShipmentRepository();
        TrackingHistoryRepository trackingHistoryRepository = new TrackingHistoryRepository();

        BookingController bookingController = new BookingController(
                new BookingService(
                        parcelRepository,
                        shipmentRepository,
                        trackingHistoryRepository,
                        new BookingValidator()
                )
        );

        TrackingController trackingController = new TrackingController(
                new TrackingService(shipmentRepository, trackingHistoryRepository)
        );

        StatusController statusController = new StatusController(
                new StatusUpdateService(
                        shipmentRepository,
                        trackingHistoryRepository,
                        new StatusValidator(),
                        new StatusMapper()
                )
        );

        runConsole(bookingController, trackingController, statusController);
    }

    private static void runConsole(
            BookingController bookingController,
            TrackingController trackingController,
            StatusController statusController
    ) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> handleBooking(scanner, bookingController);
                        case "2" -> handleTracking(scanner, trackingController);
                        case "3" -> handleStatusUpdate(scanner, statusController);
                        case "4" -> printDemoTips();
                        case "0" -> {
                            running = false;
                            System.out.println("Exiting Courier Tracking System.");
                        }
                        default -> System.out.println("Invalid option. Choose 0, 1, 2, 3, or 4.");
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

                System.out.println();
            }
        }
    }

    private static void handleBooking(Scanner scanner, BookingController bookingController) {
        System.out.println("--- Parcel Booking ---");
        BookingRequest request = new BookingRequest();

        System.out.print("Sender name: ");
        request.setSenderName(scanner.nextLine().trim());

        System.out.print("Sender phone: ");
        request.setSenderPhone(scanner.nextLine().trim());

        System.out.print("Receiver name: ");
        request.setReceiverName(scanner.nextLine().trim());

        System.out.print("Receiver phone: ");
        request.setReceiverPhone(scanner.nextLine().trim());

        System.out.print("Origin city: ");
        request.setOrigin(scanner.nextLine().trim());

        System.out.print("Destination city: ");
        request.setDestination(scanner.nextLine().trim());

        System.out.print("Parcel weight in kg: ");
        request.setWeightKg(readDouble(scanner));

        System.out.print("Service type (Standard/Express): ");
        request.setServiceType(scanner.nextLine().trim());

        BookingResponse response = bookingController.createBooking(request);

        System.out.println();
        System.out.println("Booking created successfully.");
        System.out.println("Tracking ID: " + response.getTrackingId());
        System.out.println("Current status: " + response.getCurrentStatus());
        System.out.println("Estimated cost: Rs. " + String.format("%.2f", response.getEstimatedCost()));
    }

    private static void handleTracking(Scanner scanner, TrackingController trackingController) {
        System.out.println("--- Track Shipment ---");
        System.out.print("Enter tracking ID: ");
        String trackingId = scanner.nextLine().trim();

        TrackingResponse response = trackingController.getTracking(trackingId);

        System.out.println();
        System.out.println("Tracking ID: " + response.getTrackingId());
        System.out.println("Route: " + response.getOrigin() + " -> " + response.getDestination());
        System.out.println("Receiver: " + response.getReceiverName());
        System.out.println("Current status: " + response.getCurrentStatus());
        System.out.println("Booked on: " + DATE_TIME_FORMATTER.format(response.getBookedAt()));
        System.out.println("History:");
        response.getHistory().forEach(history ->
                System.out.println(" - " + DATE_TIME_FORMATTER.format(history.getUpdatedAt())
                        + " | " + history.getStatus()
                        + " | " + history.getRemarks())
        );
    }

    private static void handleStatusUpdate(Scanner scanner, StatusController statusController) {
        System.out.println("--- Update Shipment Status ---");
        StatusUpdateRequest request = new StatusUpdateRequest();

        System.out.print("Tracking ID: ");
        request.setTrackingId(scanner.nextLine().trim());

        System.out.print("New status: ");
        request.setNewStatus(scanner.nextLine().trim());

        System.out.print("Remarks: ");
        request.setRemarks(scanner.nextLine().trim());

        StatusUpdateResponse response = statusController.updateStatus(request);

        System.out.println();
        System.out.println("Status updated successfully.");
        System.out.println("Tracking ID: " + response.getTrackingId());
        System.out.println("Previous status: " + response.getPreviousStatus());
        System.out.println("Current status: " + response.getCurrentStatus());
        System.out.println("Updated at: " + DATE_TIME_FORMATTER.format(response.getUpdatedAt()));
    }

    private static double readDouble(Scanner scanner) {
        String raw = scanner.nextLine().trim();
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Weight must be a valid number.");
        }
    }

    private static void printMenu() {
        System.out.println("==============================================");
        System.out.println("        Courier Tracking System Demo");
        System.out.println("==============================================");
        System.out.println("1. Book a parcel");
        System.out.println("2. Track a shipment");
        System.out.println("3. Update shipment status");
        System.out.println("4. Presentation tips");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void printDemoTips() {
        System.out.println("--- Suggested Presentation Flow ---");
        System.out.println("1. Create a parcel booking to generate a tracking ID.");
        System.out.println("2. Track the parcel to show booked status and history.");
        System.out.println("3. Update status step by step: Picked Up -> In Transit -> Delivered.");
        System.out.println("4. Track again to show the full audit trail.");
    }
}
