Here's the README. Create README.md in the project root (D:\servicetracker), and delete HELP.md while you're at it — that's Initializr boilerplate.

markdown
# MTC Service Tracker

A REST API for a tire and auto shop to track vehicles and their service history.

I maintain a live website for a local tire shop, and built this as a prototype backend
for the service-tracking side of that business.

## Stack

Java 17 · Spring Boot 4.1 · Spring Data JPA / Hibernate · H2 · Maven · JUnit 5

## Architecture

Controller → Service → Repository → H2
(HTTP/JSON) (logic) (persistence) (database)


Requests arrive as JSON, are converted to domain objects, and flow down through the
layers. The controller stays thin — all business logic lives in the service layer.

## Domain model

`Vehicle` is an abstract base class with three concrete subclasses:

| Type | Base cost | Service interval |
|------|-----------|------------------|
| Car | $89.99 | 5,000 mi |
| Truck | $129.99 (+20% past 100k mi) | 7,500 mi |
| Motorcycle | $64.99 | 3,000 mi |

Each subclass overrides `calculateServiceCost()` and `getServiceIntervalMiles()`.

`isDueForService()` is defined once on the base class but calls the abstract interval
method, so shared logic produces the correct result for every type. Adding a new vehicle
type means adding a class — no existing code changes, and the compiler enforces that the
required behavior is implemented.

The hierarchy is mapped with JPA single-table inheritance using a `vehicle_type`
discriminator column.

## API

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/vehicles` | List all vehicles |
| GET | `/api/vehicles/{id}` | Get one vehicle |
| POST | `/api/vehicles` | Add a vehicle |
| POST | `/api/vehicles/{id}/service` | Log a service record |
| GET | `/api/vehicles/{id}/history` | Service history, newest first |

Unknown ids return 404. Creation returns 201.

### Example

Request:

POST /api/vehicles
{"type":"TRUCK","make":"Chevrolet","model":"Silverado","year":2018,
"licensePlate":"SIL450","mileage":87000}


Response:

201 Created
{"id":4,"type":"Truck","make":"Chevrolet","model":"Silverado","year":2018,
"licensePlate":"SIL450","mileage":87000,"serviceCost":129.99,"serviceIntervalMiles":7500}


The same endpoint returns $155.99 for a truck past 100,000 miles — the cost is computed
by the object, not by the caller.

## Running it

./mvnw spring-boot:run


Runs on port 8080. The H2 console is at `/h2-console`
(JDBC URL `jdbc:h2:file:./data/servicetracker`, user `sa`, no password).

## Tests

./mvnw test


Unit tests cover the cost and service-interval logic per vehicle type, including the
truck surcharge threshold and the mileage validation rule. Integration tests cover
vehicle creation, unknown-type rejection, and missing-vehicle handling.

## Notes

H2 is used for development. The persistence layer is standard JPA, so moving to
PostgreSQL is a configuration change rather than a code change.

This is a prototype. For production I would add authentication, request validation,
structured logging, and Flyway migrations in place of Hibernate's `ddl-auto`.