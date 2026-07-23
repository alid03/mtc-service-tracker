import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../api.service';
import { Vehicle, CreateVehicleRequest, ServiceRecord, CreateServiceRecordRequest } from '../models';

@Component({
  selector: 'app-vehicle-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vehicle-list.html',
  styleUrl: './vehicle-list.css'
})
export class VehicleList {
  private api = inject(ApiService);

  vehicles = signal<Vehicle[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);
  validationErrors = signal<Record<string, string>>({});
  expandedVehicle = signal<number | null>(null);
  history = signal<Record<number, ServiceRecord[]>>({});

  form: CreateVehicleRequest = this.emptyForm();
  serviceForm: Record<number, CreateServiceRecordRequest> = {};

  ngOnInit() {
    this.load();
  }

  load() {
    this.loading.set(true);
    this.api.getVehicles().subscribe({
      next: data => {
        this.vehicles.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Could not reach the API. Is the backend running on port 8080?');
        this.loading.set(false);
      }
    });
  }

  submit() {
    this.validationErrors.set({});
    this.api.addVehicle(this.form).subscribe({
      next: created => {
        this.vehicles.update(list => [...list, created]);
        this.form = this.emptyForm();
      },
      error: (err) => {
        if (err.status === 400 && err.error?.details) {
          this.validationErrors.set(err.error.details);
        } else {
          this.error.set('Could not add that vehicle.');
        }
      }
    });
  }

  delete(id: number) {
    const vehicle = this.vehicles().find(v => v.id === id);
    if (!vehicle) return;

    const message = `Delete ${vehicle.year} ${vehicle.make} ${vehicle.model}?`;
    if (!confirm(message)) return;

    this.api.deleteVehicle(id).subscribe({
      next: () => {
        this.vehicles.update(list => list.filter(v => v.id !== id));
      },
      error: () => this.error.set('Could not delete that vehicle.')
    });
  }

  toggleExpand(id: number) {
    if (this.expandedVehicle() === id) {
      this.expandedVehicle.set(null);
    } else {
      this.expandedVehicle.set(id);
      this.loadHistory(id);
      this.initServiceForm(id);
    }
  }

  private loadHistory(id: number) {
    this.api.getServiceHistory(id).subscribe({
      next: records => {
        this.history.update(h => ({ ...h, [id]: records }));
      },
      error: () => this.error.set('Could not load service history.')
    });
  }

  private initServiceForm(id: number) {
    this.serviceForm[id] = {
      description: '',
      serviceDate: new Date().toISOString().split('T')[0],
      mileageAtService: 0,
      cost: 0
    };
  }

  logService(id: number) {
    const form = this.serviceForm[id];
    if (!form || !form.description) return;

    this.api.logService(id, form).subscribe({
      next: record => {
        this.history.update(h => ({
          ...h,
          [id]: [record, ...(h[id] || [])]
        }));
        this.initServiceForm(id);
      },
      error: () => this.error.set('Could not log service record.')
    });
  }

  isFormValid(): boolean {
    return !!(this.form.type && this.form.make && this.form.model &&
              this.form.licensePlate && this.form.year && this.form.mileage >= 0);
  }

  private emptyForm(): CreateVehicleRequest {
    return { type: 'CAR', make: '', model: '', year: new Date().getFullYear(),
      licensePlate: '', mileage: 0 };
  }
}
