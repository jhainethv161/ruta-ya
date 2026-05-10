import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CatalogoItemDescripcion {
  id:          number;
  nombre:      string;
  descripcion: string;
}

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private readonly baseUrl        = 'http://localhost:8080';
  private readonly estadosUrl     = `${this.baseUrl}/estados-usuario`;
  private readonly metodosPagoUrl = `${this.baseUrl}/metodos-pago`;

  constructor(private http: HttpClient) {}

  getEstados(): Observable<CatalogoItemDescripcion[]> {
    return this.http.get<CatalogoItemDescripcion[]>(this.estadosUrl, {
      headers: { Accept: 'application/json' },
    });
  }

  getMetodosPago(): Observable<CatalogoItemDescripcion[]> {
    return this.http.get<CatalogoItemDescripcion[]>(this.metodosPagoUrl, {
      headers: { Accept: 'application/json' },
    });
  }
}
