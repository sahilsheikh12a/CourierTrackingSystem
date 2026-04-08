# Courier Tracking System

A modular Java-based backend system for managing parcel bookings, shipment tracking, and delivery status updates for courier operations.

---

## Overview

The Courier Tracking System handles the full lifecycle of a parcel — from booking at the counter to final delivery. It supports internal staff, delivery agents, admins, and customers through role-based access and a clean shipment workflow.

---

## Core Modules

| Module | Responsibility |
|---|---|
| `auth` | Login, role validation, access control |
| `booking` | Parcel creation, tracking ID generation, charge calculation |
| `tracking` | Shipment lookup by tracking ID, history view |
| `status` | Status updates with transition validation and audit trail |
| `reporting` | Dashboard metrics, booking stats, delivery performance |
| `shared` | Common utilities, validators, mappers |

---

## Tracking ID Format

```
CTR-YYYYMMDD-XXXXX
```

Every parcel gets a unique, human-readable tracking ID generated at booking time. The ID is indexed for fast lookups.

---

## Shipment Statuses

```
Booked → Picked Up → In Transit → Arrived at Hub → Out for Delivery → Delivered
                                                                     ↘ Delayed
                                                                     ↘ Returned
                                                 Cancelled (before final delivery)
```

Status transitions are validated — the system blocks invalid progressions.

---

## Actors

- **Admin** — user management, hub/route config, full reports
- **Staff / Operator** — create bookings, update shipment movement
- **Delivery Agent** — mark pickup, out for delivery, delivered; add remarks
- **Customer** — track parcel by tracking ID (no login required)

---

## Backend Structure

Java layered architecture with clear package boundaries per module:

```
com.couriertracking.{module}
├── controller     # REST endpoints
├── service        # Business logic
├── repository     # Data access
├── entity         # DB-mapped models
├── dto            # Request/response payloads
├── mapper         # Entity ↔ DTO conversion
└── validator      # Field and business rule validation
```

---

## Key Entities

- `User` — internal actors with roles
- `Customer` — sender and receiver records
- `Parcel` — booking details, weight, dimensions, service type
- `Shipment` — current state, assigned agent, source/destination hub
- `TrackingHistory` — immutable event log per shipment
- `Hub` — network nodes (branches/warehouses)
- `Payment` — payment records linked to parcels

---

## Technology Direction

- **Backend:** Java (REST APIs)
- **Database:** PostgreSQL or MySQL
- **Auth:** Server-side, role-based access control
- **Optional:** Redis (tracking cache), message queue (notifications), SMS/email alerts

---

## Implementation Phases

| Phase | Focus |
|---|---|
| 1 | Foundation — schema, auth, project setup |
| 2 | Booking — parcel creation, tracking ID generation |
| 3 | Tracking — search by ID, status + history view |
| 4 | Status Updates — transition validation, audit trail |
| 5 | Dashboard & Reports — metrics, date-range stats |
| 6 | Enhancements — notifications, barcode/QR, proof of delivery |

---

## MVP Scope

The first working version covers:

- Internal user login
- Parcel booking
- Tracking ID generation
- Tracking by ID
- Shipment status history
- Status updates with transition rules
- Basic admin summary counts

---

## Project Documents

| Document | Status |
|---|---|
| [`architecture.md`](architecture.md) | Done |
| [`roadmap.md`](roadmap.md) | Done |
| `database-schema.md` | Planned |
| `api-spec.md` | Planned |
| `java-module-structure.md` | Planned |

---

## Repository Structure

```
CourierTrackingSystem/
├── backend/          # Java backend source
├── frontend/         # UI (staff, admin, customer views)
├── database/         # Schema and migration scripts
├── config/           # Environment and app configuration
├── docs/             # Supplementary documentation
├── scripts/          # Utility and deployment scripts
├── tests/            # Test suites
├── architecture.md
├── roadmap.md
└── README.md
```
