package com.mtc.servicetracker.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "service_records")
public class ServiceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private String description;

    private LocalDate serviceDate;

    private int mileageAtService;

    private BigDecimal cost;

    protected ServiceRecord() {
    }

    public ServiceRecord(Vehicle vehicle, String description, LocalDate serviceDate,
                         int mileageAtService, BigDecimal cost) {
        this.vehicle = vehicle;
        this.description = description;
        this.serviceDate = serviceDate;
        this.mileageAtService = mileageAtService;
        this.cost = cost;
    }

    public Long getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public String getDescription() { return description; }
    public LocalDate getServiceDate() { return serviceDate; }
    public int getMileageAtService() { return mileageAtService; }
    public BigDecimal getCost() { return cost; }
}