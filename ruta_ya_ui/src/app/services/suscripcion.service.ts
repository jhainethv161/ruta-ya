import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SuscripcionApi {
  codigo:        number;
  idTipo:        number;
  idEstado:      number;
  cedulaUsuario: string;
}

export interface CatalogoItemDescripcion {
  id:          number;
  nombre:      string;
  descripcion: string;
}

export interface CrearSuscripcionApi {
  idTipo:        number;
  idEstado:      number;
  cedulaUsuario: string;
}

export interface ActualizarSuscripcionApi {
  idTipo:        number;
  idEstado:      number;
  cedulaUsuario: string;
}

@Injectable({ providedIn: 'root' })
export class SuscripcionService {
  private readonly baseUrl    = 'http://localhost:8080';
  private readonly url        = `${this.baseUrl}/suscripciones`;
  private readonly tiposUrl   = `${this.baseUrl}/tipos-suscripcion`;
  private readonly estadosUrl = `${this.baseUrl}/estados-suscripcion`;

  constructor(private http: HttpClient) {}

  getSuscripciones(): Observable<SuscripcionApi[]> {
    return this.http.get<SuscripcionApi[]>(this.url, {
      headers: { Accept: 'application/json' },
    });
  }

  crearSuscripcion(suscripcion: CrearSuscripcionApi): Observable<string> {
    return this.http.post(this.url, suscripcion, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text',
    });
  }

  actualizarSuscripcion(codigo: number, suscripcion: ActualizarSuscripcionApi): Observable<string> {
    return this.http.put(`${this.url}/${codigo}`, suscripcion, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text',
    });
  }

  eliminarSuscripcion(codigo: number): Observable<string> {
    return this.http.delete(`${this.url}/${codigo}`, {
      responseType: 'text',
    });
  }

  getTipos(): Observable<CatalogoItemDescripcion[]> {
    return this.http.get<CatalogoItemDescripcion[]>(this.tiposUrl, {
      headers: { Accept: 'application/json' },
    });
  }

  getEstados(): Observable<CatalogoItemDescripcion[]> {
    return this.http.get<CatalogoItemDescripcion[]>(this.estadosUrl, {
      headers: { Accept: 'application/json' },
    });
  }
}
