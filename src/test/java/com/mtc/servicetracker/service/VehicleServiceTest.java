package com.mtc.servicetracker.service;

import com.mtc.servicetracker.dto.CreateVehicleRequest;
import com.mtc.servicetracker.exception.VehicleNotFoundException;
import com.mtc.servicetracker.model.Truck;
import com.mtc.servicetracker.model.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VehicleServiceTest {

    @Autowired
    private VehicleService service;

    @Test
    @DisplayName("Creating a TRUCK produces a Truck instance")
    void createBuildsCorrectSubclass() {
        Vehicle created = service.create(new CreateVehicleRequest(
                "TRUCK", "Chevrolet", "Silverado", 2018, "TEST-001", 87000));

        assertInstanceOf(Truck.class, created);
        assertNotNull(created.getId());
    }

    @Test
    @DisplayName("An unknown vehicle type is rejected")
    void unknownTypeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.create(
                new CreateVehicleRequest("SPACESHIP", "NASA", "X-1", 2026, "TEST-002", 0)));
    }

    @Test
    @DisplayName("Looking up a missing vehicle throws VehicleNotFoundException")
    void missingVehicleThrows() {
        assertThrows(VehicleNotFoundException.class, () -> service.findById(999999L));
    }
}