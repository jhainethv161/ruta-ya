import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface VehiculoApi {
  placa: string;
  modelo: string;
  idMarca: number;
  idTipo: number;
}

export interface CatalogoItem {
  id: number;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class VehiculoService {
  private readonly baseUrl   = 'http://localhost:8080';
  private readonly url       = `${this.baseUrl}/vehiculos`;
  private readonly marcasUrl = `${this.baseUrl}/marcas`;
  private readonly tiposUrl  = `${this.baseUrl}/tipos-vehiculo`;

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

  getMarcas(): Observable<CatalogoItem[]> {
    return this.http.get<CatalogoItem[]>(this.marcasUrl, {
      headers: { Accept: 'application/json' },
    });
  }

  getTipos(): Observable<CatalogoItem[]> {
    return this.http.get<CatalogoItem[]>(this.tiposUrl, {
      headers: { Accept: 'application/json' },
    });
  }
}
