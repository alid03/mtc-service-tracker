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
  mileageAtLastService: number;
  isDueForService: boolean;
}

export interface CreateVehicleRequest {
  type: string;
  make: string;
  model: string;
  year: number;
  licensePlate: string;
  mileage: number;
}

export interface ServiceRecord {
  id: number;
  description: string;
  serviceDate: string;
  mileageAtService: number;
  cost: number;
}

export interface CreateServiceRecordRequest {
  description: string;
  serviceDate: string;
  mileageAtService: number;
  cost: number;
}
