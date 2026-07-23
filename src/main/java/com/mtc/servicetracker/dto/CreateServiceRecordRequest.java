package com.mtc.servicetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateServiceRecordRequest(
        String description,
        LocalDate serviceDate,
        int mileageAtService,
        BigDecimal cost
) {}