# Courier Tracking System Architecture

## 1. Purpose

This document defines the high-level architecture for a Courier Tracking System module that supports:

- parcel booking
- shipment tracking
- status updates
- operational statistics and reporting

The goal is to provide a clear technical foundation before implementation begins.

Implementation direction for the core module:

- Java backend
- primary business modules: parcel booking, tracking, and status updates

## 2. System Objectives

The system should:

- allow staff to create parcel bookings quickly
- generate a unique tracking ID for every parcel
- maintain current shipment status and full tracking history
- let customers track parcels using the tracking ID
- provide admins and operators with booking and delivery statistics
- support future additions such as notifications, barcode scanning, and proof of delivery

## 3. Architecture Overview

The system should follow a modular layered architecture.

The main business implementation should live in a Java backend service. A frontend can consume these APIs, but shipment lifecycle logic, validation, and persistence should remain in the Java layer.

### 3.1 Layers

1. Presentation Layer
   - admin dashboard
   - staff booking interface
   - delivery agent status update interface
   - customer parcel tracking page

2. Application Layer
   - booking management
   - tracking management
   - status update management
   - report and stats service
   - authentication and authorization

3. Domain / Business Layer
   - parcel lifecycle rules
   - tracking ID generation
   - pricing and booking validation
   - shipment status transition validation
   - delivery and delay logic

4. Data Layer
   - relational database for transactional data
   - optional cache for fast tracking lookups
   - audit/event history storage

### 3.2 Java Backend Structure

The backend should be organized as a Java module-oriented service with clear package boundaries.

Suggested top-level Java modules:

- `auth`
- `booking`
- `tracking`
- `status`
- `reporting`
- `shared`

Suggested package structure inside each module:

- `controller` for API endpoints
- `service` for business logic
- `repository` for data access
- `entity` for persistence models
- `dto` for request and response payloads
- `mapper` for entity and DTO conversion
- `validator` for field and business rule validation

This structure keeps parcel booking, tracking, and status update workflows separated while allowing shared business rules to be reused safely.

## 4. Core Modules

### 4.1 Booking Module

Responsibilities:

- capture sender and receiver details
- record parcel attributes such as weight, dimensions, and type
- calculate charges based on delivery type or rate rules
- create shipment record
- generate unique tracking number
- initialize first status as `Booked`

Outputs:

- parcel record
- shipment record
- initial tracking history entry
- booking receipt / label data

### 4.2 Tracking Module

Responsibilities:

- search shipment by tracking ID
- display current shipment status
- show shipment origin, destination, booking date, and expected delivery date
- show chronological tracking history

Outputs:

- customer-facing tracking view
- internal operations tracking details

### 4.3 Status Update Module

Responsibilities:

- allow authorized staff or delivery agents to update shipment status
- record checkpoint location, timestamp, remarks, and actor
- validate allowed status transitions
- update current shipment state from the latest event

Supported statuses:

- `Booked`
- `Picked Up`
- `In Transit`
- `Arrived at Hub`
- `Out for Delivery`
- `Delivered`
- `Delayed`
- `Returned`
- `Cancelled`

### 4.4 Stats and Reporting Module

Responsibilities:

- aggregate booking and delivery data
- show current operational counts
- support date-based summaries
- provide trend reporting for management

Example metrics:

- total bookings
- active shipments
- delivered shipments
- delayed shipments
- returned shipments
- branch-wise performance
- daily, weekly, and monthly booking counts

## 5. Key Actors and Access Model

### 5.1 Admin

- manage users and roles
- configure hubs, routes, and pricing logic
- access all reports and statistics
- monitor system-wide operations

### 5.2 Staff / Operator

- create parcel bookings
- update shipment movement between hubs
- view operational dashboards

### 5.3 Delivery Agent

- update pickup and delivery-related statuses
- mark shipment as out for delivery or delivered
- add delivery remarks

### 5.4 Customer

- track parcel using tracking ID
- view shipment progress and estimated delivery

## 6. High-Level Data Model

### 6.1 Main Entities

#### User

- id
- name
- email / phone
- password hash
- role
- status

#### Customer

- id
- full name
- phone
- email
- address
- city
- state
- postal code

#### Parcel

- id
- tracking_id
- booking_date
- sender_id
- receiver_id
- parcel_type
- weight
- dimensions
- declared_value
- service_type
- amount
- payment_status

#### Shipment

- id
- parcel_id
- source_hub_id
- destination_hub_id
- current_status
- expected_delivery_date
- current_location
- assigned_agent_id

#### TrackingHistory

- id
- shipment_id
- status
- location
- remarks
- updated_by
- updated_at

#### Hub

- id
- name
- code
- city
- address

#### Payment

- id
- parcel_id
- amount
- mode
- status
- transaction_ref
- paid_at

## 7. Data Flow

### 7.1 Parcel Booking Flow

1. Staff enters sender, receiver, and parcel details.
2. System validates required fields and pricing inputs.
3. Booking service generates a unique tracking ID.
4. Parcel and shipment records are created.
5. Initial tracking event is stored as `Booked`.
6. Receipt or label data is generated.

### 7.2 Tracking Flow

1. Customer or staff enters a tracking ID.
2. Tracking service fetches parcel, shipment, and tracking history.
3. System returns current status and historical events in order.

### 7.3 Status Update Flow

1. Staff or delivery agent selects a shipment.
2. User submits a new status with optional remarks and location.
3. System validates status transition rules.
4. Tracking history record is stored.
5. Shipment current status and location are updated.
6. Stats are refreshed through query aggregation or background processing.

## 8. Business Rules

### 8.1 Tracking ID

- must be unique
- should be human-readable
- should support indexing for fast lookup

Suggested format:

- `CTR-YYYYMMDD-XXXXX`

### 8.2 Status Transition Rules

The system should prevent invalid progression.

Examples:

- `Delivered` cannot be set before `Out for Delivery`
- `Cancelled` should usually apply only before final delivery
- `Returned` should only happen after failed delivery or return workflow

### 8.3 Auditability

- every status change must be preserved in history
- current shipment status must always be derived from the latest valid event
- updates should capture actor and timestamp

## 9. API / Service Boundaries

Suggested backend service areas:

### 9.1 Booking Service

- create booking
- calculate charges
- generate tracking ID
- fetch booking details

### 9.2 Tracking Service

- get shipment by tracking ID
- get tracking history
- get current shipment state

### 9.3 Status Service

- add status update
- validate transition
- update shipment current state

### 9.4 Reporting Service

- get dashboard summary
- get date-range booking stats
- get delivery performance stats

### 9.5 Auth Service

- login
- role validation
- access control checks

## 10. Java Implementation Notes

The main application logic should be implemented in Java using a layered service design.

Suggested Java responsibilities:

- `controller`
  - expose booking, tracking, and status update endpoints
- `service`
  - implement shipment workflows and business rules
- `repository`
  - manage parcel, shipment, customer, and tracking history persistence
- `entity`
  - define database-mapped domain models
- `dto`
  - define request and response objects
- `validator`
  - enforce validation and status transition rules

The first Java services to prioritize are:

- `BookingService`
- `TrackingService`
- `StatusUpdateService`
- `AuthService`

## 11. Suggested Java Package Direction

An example backend package direction:

- `com.couriertracking.auth`
- `com.couriertracking.booking`
- `com.couriertracking.tracking`
- `com.couriertracking.status`
- `com.couriertracking.reporting`
- `com.couriertracking.shared`

Within the main courier workflow, implementation priority should be:

1. booking
2. tracking
3. status update

## 12. Suggested Technology Direction

This is framework-agnostic, but the system should use:

- frontend UI for staff/admin/customer views
- Java backend APIs, preferably REST for the initial version
- relational database such as PostgreSQL or MySQL
- server-side authentication with role-based access control

Optional additions:

- Redis for caching tracking lookups
- message queue for async notifications
- SMS/email integration for delivery alerts

## 13. Non-Functional Requirements

### 11.1 Performance

- tracking search should be fast
- tracking ID must be indexed
- dashboard stats should avoid expensive real-time full-table scans at scale

### 11.2 Security

- role-based access for internal actions
- password hashing and secure session handling
- audit logging for sensitive updates

### 11.3 Reliability

- status update operations should be transactional
- shipment history should never be partially written
- duplicate status submissions should be controlled where needed

### 11.4 Scalability

- design must support increasing parcel volume
- reporting queries should be separated or optimized for large datasets
- event history should remain queryable over time

## 14. Recommended Implementation Phases

### Phase 1: Core Foundation

- define schema
- implement authentication and roles
- set up base project structure

### Phase 2: Booking

- parcel booking form
- parcel and shipment creation
- tracking ID generation

### Phase 3: Tracking

- tracking search endpoint
- public tracking page
- internal shipment detail view

### Phase 4: Status Updates

- status update UI
- status transition validation
- tracking history management

### Phase 5: Dashboard and Reports

- admin summary cards
- booking trends
- delivery and delay reports

### Phase 6: Enhancements

- notifications
- barcode or QR support
- proof of delivery upload
- SLA and route analytics

## 15. Risks and Design Considerations

- status rules must be defined early to avoid inconsistent shipment states
- tracking history must be immutable enough for audit confidence
- reporting can become expensive if aggregation strategy is not planned
- booking and delivery workflows may differ by business policy, so configurable status rules may be useful

## 16. Next Recommended Documents

After this architecture, the next useful documents are:

1. `database-schema.md`
2. `api-spec.md`
3. `java-module-structure.md`
4. `workflow-diagrams.md`
5. `implementation-plan.md`
