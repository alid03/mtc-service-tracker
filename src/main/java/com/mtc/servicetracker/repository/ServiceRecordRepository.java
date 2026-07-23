package com.mtc.servicetracker.repository;

import com.mtc.servicetracker.model.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    List<ServiceRecord> findByVehicleIdOrderByServiceDateDesc(Long vehicleId);
}