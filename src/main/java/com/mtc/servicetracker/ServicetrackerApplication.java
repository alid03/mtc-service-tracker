package com.mtc.servicetracker;

import com.mtc.servicetracker.model.*;
import com.mtc.servicetracker.repository.ServiceRecordRepository;
import com.mtc.servicetracker.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ServicetrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicetrackerApplication.class, args);
	}

	@Bean
	CommandLineRunner seed(VehicleRepository vehicles, ServiceRecordRepository records) {
		return args -> {
			if (vehicles.count() == 0) {
				Vehicle civic = vehicles.save(new Car("Honda", "Civic", 2019, "ABC123", 42000));
				Vehicle f150 = vehicles.save(new Truck("Ford", "F-150", 2016, "TRK900", 112000));
				vehicles.save(new Motorcycle("Yamaha", "MT-07", 2021, "MOTO88", 9000));

				records.save(new ServiceRecord(civic, "Oil change and tire rotation",
						LocalDate.of(2026, 3, 14), 36000, new BigDecimal("89.99")));
				records.save(new ServiceRecord(f150, "Brake pads, front",
						LocalDate.of(2026, 5, 2), 108000, new BigDecimal("155.99")));
			}

			for (Vehicle v : vehicles.findAll()) {
				System.out.println(v.getId() + " " + v.getMake() + " " + v.getModel()
						+ " -> $" + v.calculateServiceCost()
						+ " | records: " + records.findByVehicleIdOrderByServiceDateDesc(v.getId()).size());
			}
		};
	}
}