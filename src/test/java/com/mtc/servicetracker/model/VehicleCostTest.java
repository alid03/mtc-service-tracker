package com.mtc.servicetracker.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VehicleCostTest {

    @Test
    @DisplayName("Car charges the flat base rate")
    void carUsesFlatRate() {
        Vehicle civic = new Car("Honda", "Civic", 2019, "ABC123", 42000);
        assertEquals(new BigDecimal("89.99"), civic.calculateServiceCost());
    }

    @Test
    @DisplayName("Truck under 100k miles has no surcharge")
    void truckBelowThresholdHasNoSurcharge() {
        Vehicle silverado = new Truck("Chevrolet", "Silverado", 2018, "SIL450", 87000);
        assertEquals(new BigDecimal("129.99"), silverado.calculateServiceCost());
    }

    @Test
    @DisplayName("Truck at or past 100k miles carries the heavy-use surcharge")
    void truckAtThresholdAddsSurcharge() {
        Vehicle f150 = new Truck("Ford", "F-150", 2016, "TRK900", 112000);
        assertEquals(new BigDecimal("155.99"), f150.calculateServiceCost());
    }

    @Test
    @DisplayName("Service interval differs by vehicle type")
    void intervalsDifferByType() {
        assertEquals(5000, new Car("Honda", "Civic", 2019, "A1", 0).getServiceIntervalMiles());
        assertEquals(7500, new Truck("Ford", "F-150", 2016, "B2", 0).getServiceIntervalMiles());
        assertEquals(3000, new Motorcycle("Yamaha", "MT-07", 2021, "C3", 0).getServiceIntervalMiles());
    }

    @Test
    @DisplayName("A vehicle is due once it passes its own interval")
    void dueForServiceUsesSubclassInterval() {
        Vehicle civic = new Car("Honda", "Civic", 2019, "ABC123", 42000);
        Vehicle f150 = new Truck("Ford", "F-150", 2016, "TRK900", 112000);

        // Both have driven 6,000 miles since their last service.
        assertTrue(civic.isDueForService(36000));   // past the 5,000 car interval
        assertFalse(f150.isDueForService(106000));  // short of the 7,500 truck interval
    }

    @Test
    @DisplayName("Mileage cannot be set backwards")
    void mileageCannotDecrease() {
        Vehicle civic = new Car("Honda", "Civic", 2019, "ABC123", 42000);
        assertThrows(IllegalArgumentException.class, () -> civic.setMileage(41000));
    }
}