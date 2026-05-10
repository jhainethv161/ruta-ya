import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ViajeApi {
  codigo:             number;
  fechaHora:          string;
  valorEstimado:      number;
  idEstado:           number;
  idDireccionOrigen:  number;
  idDireccionDestino: number;
  cedulaUsuario:      string;
  cedulaConductor:    string;
  placaVehiculo:      string;
}

@Injectable({ providedIn: 'root' })
export class ViajeService {
  private readonly url = 'http://localhost:8080/viajes';

  constructor(private http: HttpClient) {}

  getViajes(): Observable<ViajeApi[]> {
    return this.http.get<ViajeApi[]>(this.url, {
      headers: { Accept: 'application/json' },
    });
  }
}
