package com.mtc.servicetracker.dto;

public record CreateVehicleRequest(
        String type,
        String make,
        String model,
        int year,
        String licensePlate,
        int mileage
) {}