package com.mtc.servicetracker.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("MOTORCYCLE")
public class Motorcycle extends Vehicle {

    private static final BigDecimal BASE_COST = new BigDecimal("64.99");
    private static final int SERVICE_INTERVAL = 3000;

    protected Motorcycle() {
    }

    public Motorcycle(String make, String model, int year, String licensePlate, int mileage) {
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