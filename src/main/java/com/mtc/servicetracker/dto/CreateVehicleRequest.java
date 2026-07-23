package com.mtc.servicetracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record CreateVehicleRequest(
        @NotBlank String type,
        @NotBlank String make,
        @NotBlank String model,
        @Min(1900) @Max(2100) int year,
        @NotBlank String licensePlate,
        @Min(0) int mileage
) {}