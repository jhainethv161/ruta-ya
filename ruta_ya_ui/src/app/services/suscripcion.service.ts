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
