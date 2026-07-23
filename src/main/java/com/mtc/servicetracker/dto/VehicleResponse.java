package com.mtc.servicetracker.dto;

import com.mtc.servicetracker.model.Vehicle;

import java.math.BigDecimal;

public record VehicleResponse(
        Long id,
        String type,
        String make,
        String model,
        int year,
        String licensePlate,
        int mileage,
        BigDecimal serviceCost,
        int serviceIntervalMiles
) {
    public static VehicleResponse from(Vehicle v) {
        return new VehicleResponse(
                v.getId(),
                v.getClass().getSimpleName(),
                v.getMake(),
                v.getModel(),
                v.getYear(),
                v.getLicensePlate(),
                v.getMileage(),
                v.calculateServiceCost(),
                v.getServiceIntervalMiles()
        );
    }
}