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

export interface CatalogoItemDescripcion {
  id:          number;
  nombre:      string;
  descripcion: string;
}

export interface VehiculoConductorApi {
  cedulaConductor: string;
  placaVehiculo:   string;
  disponible:      boolean;
}

export interface CiudadApi {
  codigo:             string;
  nombre:             string;
  codigoDepartamento: string;
}

export interface DireccionApi {
  direccion:    string;
  descripcion:  string;
  codigoCiudad: string;
}

export interface CrearViajeApi {
  valorEstimado:    number;
  idEstado:         number;
  cedulaUsuario:    string;
  cedulaConductor:  string;
  placaVehiculo:    string;
  direccionOrigen:  DireccionApi;
  direccionDestino: DireccionApi;
}

@Injectable({ providedIn: 'root' })
export class ViajeService {
  private readonly baseUrl              = 'http://localhost:8080';
  private readonly url                  = `${this.baseUrl}/viajes`;
  private readonly estadosUrl           = `${this.baseUrl}/estados-viaje`;
  private readonly vehiculoConductorUrl = `${this.baseUrl}/vehiculo-conductor`;
  private readonly ciudadesUrl          = `${this.baseUrl}/ciudades`;

  constructor(private http: HttpClient) {}

  getViajes(): Observable<ViajeApi[]> {
    return this.http.get<ViajeApi[]>(this.url, {
      headers: { Accept: 'application/json' },
    });
  }

  crearViaje(viaje: CrearViajeApi): Observable<string> {
    return this.http.post(this.url, viaje, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text',
    });
  }

  getEstados(): Observable<CatalogoItemDescripcion[]> {
    return this.http.get<CatalogoItemDescripcion[]>(this.estadosUrl, {
      headers: { Accept: 'application/json' },
    });
  }

  getVehiculoConductor(): Observable<VehiculoConductorApi[]> {
    return this.http.get<VehiculoConductorApi[]>(this.vehiculoConductorUrl, {
      headers: { Accept: 'application/json' },
    });
  }

  getCiudades(): Observable<CiudadApi[]> {
    return this.http.get<CiudadApi[]>(this.ciudadesUrl, {
      headers: { Accept: 'application/json' },
    });
  }
}
