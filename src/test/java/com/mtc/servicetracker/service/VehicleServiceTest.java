package com.mtc.servicetracker.service;

import com.mtc.servicetracker.dto.CreateVehicleRequest;
import com.mtc.servicetracker.exception.VehicleNotFoundException;
import com.mtc.servicetracker.model.ElectricVehicle;
import com.mtc.servicetracker.model.Truck;
import com.mtc.servicetracker.model.Vehicle;
import com.mtc.servicetracker.repository.ServiceRecordRepository;
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

    @Autowired
    private ServiceRecordRepository recordRepository;

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

    @Test
    @DisplayName("Creating an ELECTRIC produces an ElectricVehicle instance")
    void createElectricBuildsCorrectSubclass() {
        Vehicle created = service.create(new CreateVehicleRequest(
                "ELECTRIC", "Tesla", "Model 3", 2021, "EV-001", 15000));

        assertInstanceOf(ElectricVehicle.class, created);
        assertNotNull(created.getId());
    }

    @Test
    @DisplayName("Deleting a vehicle removes it and its service records")
    void deleteRemovesVehicleAndRecords() {
        Vehicle created = service.create(new CreateVehicleRequest(
                "CAR", "Honda", "Civic", 2019, "TEST-003", 42000));
        Long vehicleId = created.getId();

        service.delete(vehicleId);

        assertThrows(VehicleNotFoundException.class, () -> service.findById(vehicleId));
        assertEquals(0, recordRepository.findByVehicleIdOrderByServiceDateDesc(vehicleId).size());
    }
}