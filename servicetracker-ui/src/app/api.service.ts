import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vehicle, CreateVehicleRequest, ServiceRecord, CreateServiceRecordRequest } from './models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/vehicles';

  getVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.baseUrl);
  }

  addVehicle(request: CreateVehicleRequest): Observable<Vehicle> {
    return this.http.post<Vehicle>(this.baseUrl, request);
  }

  deleteVehicle(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getServiceHistory(id: number): Observable<ServiceRecord[]> {
    return this.http.get<ServiceRecord[]>(`${this.baseUrl}/${id}/history`);
  }

  logService(id: number, request: CreateServiceRecordRequest): Observable<ServiceRecord> {
    return this.http.post<ServiceRecord>(`${this.baseUrl}/${id}/service`, request);
  }
}
