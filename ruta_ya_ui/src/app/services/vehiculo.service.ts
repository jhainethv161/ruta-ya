import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface VehiculoApi {
  placa: string;
  modelo: string;
  idMarca: number;
  idTipo: number;
}

@Injectable({ providedIn: 'root' })
export class VehiculoService {
  private readonly url = 'http://localhost:8080/vehiculos';

  constructor(private http: HttpClient) {}

  getVehiculos(): Observable<VehiculoApi[]> {
    return this.http.get<VehiculoApi[]>(this.url, {
      headers: { Accept: 'application/json' },
    });
  }

  crearVehiculo(vehiculo: VehiculoApi): Observable<string> {
    return this.http.post(this.url, vehiculo, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text',
    });
  }
}
