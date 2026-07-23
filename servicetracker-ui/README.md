# MTC Service Tracker

A full-stack application for a tire and auto shop to track vehicles and their service history.

I maintain a live website for a local tire shop, and built this as a prototype for the
service-tracking side of that business.

## Stack

**Backend** — Java 17 · Spring Boot 4.1 · Spring Data JPA / Hibernate · H2 · Maven · JUnit 5
**Frontend** — Angular 20 · TypeScript

## Architecture

Angular → Controller → Service → Repository → H2
(UI) (HTTP/JSON) (logic) (persistence) (database)


Requests arrive as JSON, are converted to domain objects, and flow down through the
layers. The controller stays thin — all business logic lives in the service layer.

## Domain model

`Vehicle` is an abstract base class with four concrete subclasses:

| Type | Base cost | Service interval |
|------|-----------|------------------|
| Car | $89.99 | 5,000 mi |
| Truck | $129.99 (+20% past 100k mi) | 7,500 mi |
| Motorcycle | $64.99 | 3,000 mi |
| Electric | $74.99 | 10,000 mi |

Each subclass overrides `calculateServiceCost()` and `getServiceIntervalMiles()`.

`isDueForService()` is defined once on the base class but calls the abstract interval
method, so shared logic produces the correct result for every type. Adding `ElectricVehicle`
required one new class and one line in the factory switch — no changes to `Vehicle`, the
existing subclasses, or the due-for-service logic, and the compiler enforced that the new
type implemented the required behavior.

The hierarchy is mapped with JPA single-table inheritance using a `vehicle_type`
discriminator column.

## API

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/vehicles` | List all vehicles |
| GET | `/api/vehicles/{id}` | Get one vehicle |
| POST | `/api/vehicles` | Add a vehicle |
| DELETE | `/api/vehicles/{id}` | Remove a vehicle and its service records |
| POST | `/api/vehicles/{id}/service` | Log a service record |
| GET | `/api/vehicles/{id}/history` | Service history, newest first |

Creation returns 201, deletion 204, unknown ids 404. Requests are validated with bean
validation — blank fields or an out-of-range year return 400 with a JSON body naming the
offending fields, handled by a `@RestControllerAdvice`.

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

## Frontend

A single-page Angular app for shop staff: view the fleet, add a vehicle, remove one.
Built with standalone components, signals for state, `HttpClient` with typed responses,
two-way binding on the form, and the `currency` and `number` pipes for formatting.

## Running it

Backend:

./mvnw spring-boot:run

Runs on port 8080. H2 console at `/h2-console`
(JDBC URL `jdbc:h2:file:./data/servicetracker`, user `sa`, no password).

Frontend:

cd servicetracker-ui
ng serve

Runs on port 4200. The backend allows this origin via `@CrossOrigin`.

## Tests

./mvnw test


Unit tests cover cost and service-interval logic per vehicle type, the truck surcharge
threshold, and the mileage validation rule. Integration tests cover vehicle creation,
unknown-type rejection, and missing-vehicle handling.

Note: H2 in file mode allows a single connection, so stop the running app before running
tests.

## Notes

H2 is used for development. The persistence layer is standard JPA, so moving to
PostgreSQL is a configuration change rather than a code change.

This is a prototype. For production I would add authentication, structured logging, and
Flyway migrations in place of Hibernate's `ddl-auto`.
