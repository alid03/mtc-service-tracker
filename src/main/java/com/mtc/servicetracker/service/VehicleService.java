package com.mtc.servicetracker.service;

import com.mtc.servicetracker.dto.CreateServiceRecordRequest;
import com.mtc.servicetracker.dto.CreateVehicleRequest;
import com.mtc.servicetracker.exception.VehicleNotFoundException;
import com.mtc.servicetracker.model.*;
import com.mtc.servicetracker.repository.ServiceRecordRepository;
import com.mtc.servicetracker.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicles;
    private final ServiceRecordRepository records;

    public VehicleService(VehicleRepository vehicles, ServiceRecordRepository records) {
        this.vehicles = vehicles;
        this.records = records;
    }

    public List<Vehicle> findAll() {
        return vehicles.findAll();
    }

    public Vehicle findById(Long id) {
        return vehicles.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
    }

    @Transactional
    public Vehicle create(CreateVehicleRequest request) {
        Vehicle vehicle = switch (request.type().toUpperCase()) {
            case "CAR" -> new Car(request.make(), request.model(), request.year(),
                    request.licensePlate(), request.mileage());
            case "TRUCK" -> new Truck(request.make(), request.model(), request.year(),
                    request.licensePlate(), request.mileage());
            case "MOTORCYCLE" -> new Motorcycle(request.make(), request.model(), request.year(),
                    request.licensePlate(), request.mileage());
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + request.type());
        };
        return vehicles.save(vehicle);
    }

    @Transactional
    public ServiceRecord logService(Long vehicleId, CreateServiceRecordRequest request) {
        Vehicle vehicle = findById(vehicleId);
        ServiceRecord record = new ServiceRecord(vehicle, request.description(),
                request.serviceDate(), request.mileageAtService(), request.cost());
        return records.save(record);
    }

    public List<ServiceRecord> historyFor(Long vehicleId) {
        findById(vehicleId);
        return records.findByVehicleIdOrderByServiceDateDesc(vehicleId);
    }
}