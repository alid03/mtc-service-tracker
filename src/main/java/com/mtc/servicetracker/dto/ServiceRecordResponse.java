package com.mtc.servicetracker.dto;

import com.mtc.servicetracker.model.ServiceRecord;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ServiceRecordResponse(
        Long id,
        Long vehicleId,
        String description,
        LocalDate serviceDate,
        int mileageAtService,
        BigDecimal cost
) {
    public static ServiceRecordResponse from(ServiceRecord r) {
        return new ServiceRecordResponse(
                r.getId(),
                r.getVehicle().getId(),
                r.getDescription(),
                r.getServiceDate(),
                r.getMileageAtService(),
                r.getCost()
        );
    }
}