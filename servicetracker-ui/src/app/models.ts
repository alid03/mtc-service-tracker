export interface Vehicle {
  id: number;
  type: string;
  make: string;
  model: string;
  year: number;
  licensePlate: string;
  mileage: number;
  serviceCost: number;
  serviceIntervalMiles: number;
}

export interface CreateVehicleRequest {
  type: string;
  make: string;
  model: string;
  year: number;
  licensePlate: string;
  mileage: number;
}
