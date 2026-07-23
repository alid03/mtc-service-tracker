package com.mtc.servicetracker.model;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ELECTRIC")
public class ElectricVehicle extends Vehicle {

    private static final BigDecimal BASE_COST = new BigDecimal("74.99");
    private static final int SERVICE_INTERVAL = 10000;

    protected ElectricVehicle() {
    }

    public ElectricVehicle(String make, String model, int year, String licensePlate, int mileage) {
        super(make, model, year, licensePlate, mileage);
    }

    @Override
    public BigDecimal calculateServiceCost() {
        return BASE_COST;
    }

    @Override
    public int getServiceIntervalMiles() {
        return SERVICE_INTERVAL;
    }
}
