// Business logic for shipment status updates.
// Validates the requested status transition against allowed rules,
// creates a new TrackingHistory event with actor, location, remarks, and timestamp,
// and updates the Shipment's current status and current location.
