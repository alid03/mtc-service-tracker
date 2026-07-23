package com.mtc.servicetracker.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@DiscriminatorValue("TRUCK")
public class Truck extends Vehicle {

    private static final BigDecimal BASE_COST = new BigDecimal("129.99");
    private static final BigDecimal HEAVY_USE_SURCHARGE = new BigDecimal("1.20");
    private static final int HEAVY_USE_MILEAGE = 100_000;
    private static final int SERVICE_INTERVAL = 7500;

    protected Truck() {
    }

    public Truck(String make, String model, int year, String licensePlate, int mileage) {
        super(make, model, year, licensePlate, mileage);
    }

    /** Trucks past 100k miles take longer to service, so they carry a surcharge. */
    @Override
    public BigDecimal calculateServiceCost() {
        if (getMileage() >= HEAVY_USE_MILEAGE) {
            return BASE_COST.multiply(HEAVY_USE_SURCHARGE).setScale(2, RoundingMode.HALF_UP);
        }
        return BASE_COST;
    }

    @Override
    public int getServiceIntervalMiles() {
        return SERVICE_INTERVAL;
    }
}