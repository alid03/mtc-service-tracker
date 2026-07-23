import { Component } from '@angular/core';
import { VehicleList } from './vehicle-list/vehicle-list';

@Component({
  selector: 'app-root',
  imports: [VehicleList],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {}
