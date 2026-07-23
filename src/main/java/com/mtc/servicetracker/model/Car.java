package com.mtc.servicetracker.model;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {

    private static final BigDecimal BASE_COST = new BigDecimal("89.99");
    private static final int SERVICE_INTERVAL = 5000;

    protected Car() {
    }

    public Car(String make, String model, int year, String licensePlate, int mileage) {
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