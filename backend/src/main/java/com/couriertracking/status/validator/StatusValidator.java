// Validator for status update inputs and transition rules.
// Checks that the new status is valid for the shipment's current state.
// Enforces the allowed transition map:
//   Booked -> Picked Up -> In Transit -> Arrived at Hub -> Out for Delivery -> Delivered
//   Any non-terminal state -> Delayed or Cancelled
//   Failed delivery -> Returned
