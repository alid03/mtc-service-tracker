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
        int serviceIntervalMiles,
        int mileageAtLastService,
        boolean isDueForService
) {
    public static VehicleResponse from(Vehicle v) {
        return from(v, 0);
    }

    public static VehicleResponse from(Vehicle v, int mileageAtLastService) {
        boolean isDue = v.isDueForService(mileageAtLastService);
        return new VehicleResponse(
                v.getId(),
                v.getClass().getSimpleName(),
                v.getMake(),
                v.getModel(),
                v.getYear(),
                v.getLicensePlate(),
                v.getMileage(),
                v.calculateServiceCost(),
                v.getServiceIntervalMiles(),
                mileageAtLastService,
                isDue
        );
    }
}