package com.mtc.servicetracker.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type")
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;

    @Column(name = "model_year")
    private int year;

    @Column(unique = true)
    private String licensePlate;

    private int mileage;

    /** Required by JPA. Not for application use. */
    protected Vehicle() {
    }

    protected Vehicle(String make, String model, int year, String licensePlate, int mileage) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.mileage = mileage;
    }

    public abstract BigDecimal calculateServiceCost();

    public abstract int getServiceIntervalMiles();

    /** A vehicle is due once it has driven past its type's service interval. */
    public boolean isDueForService(int mileageAtLastService) {
        return mileage - mileageAtLastService >= getServiceIntervalMiles();
    }

    public Long getId() { return id; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getLicensePlate() { return licensePlate; }
    public int getMileage() { return mileage; }

    public void setMileage(int mileage) {
        if (mileage < this.mileage) {
            throw new IllegalArgumentException("Mileage cannot decrease");
        }
        this.mileage = mileage;
    }
}