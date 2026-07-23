package com.mtc.servicetracker.controller;

import com.mtc.servicetracker.dto.*;
import com.mtc.servicetracker.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<VehicleResponse> list() {
        return service.findAll().stream().map(VehicleResponse::from).toList();
    }

    @GetMapping("/{id}")
    public VehicleResponse get(@PathVariable Long id) {
        return VehicleResponse.from(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@RequestBody CreateVehicleRequest request) {
        VehicleResponse created = VehicleResponse.from(service.create(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/service")
    public ResponseEntity<ServiceRecordResponse> logService(
            @PathVariable Long id,
            @RequestBody CreateServiceRecordRequest request) {
        ServiceRecordResponse created = ServiceRecordResponse.from(service.logService(id, request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}/history")
    public List<ServiceRecordResponse> history(@PathVariable Long id) {
        return service.historyFor(id).stream().map(ServiceRecordResponse::from).toList();
    }
}