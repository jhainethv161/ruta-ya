import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CatalogoItemDescripcion {
  id:          number;
  nombre:      string;
  descripcion: string;
}

export interface UsuarioApi {
  cedula:           string;
  primerNombre:     string;
  segundoNombre:    string;
  primerApellido:   string;
  segundoApellido:  string;
  correo:           string;
  contrasena:       string;
  fechaNacimiento:  string;
  idEstado:         number;
  idMetodoPagoPref: number;
}

export interface CrearUsuarioApi {
  cedula:           string;
  primerNombre:     string;
  segundoNombre:    string;
  primerApellido:   string;
  segundoApellido:  string;
  correo:           string;
  contrasena:       string;
  fechaNacimiento:  string;
  idEstado:         number;
  idMetodoPagoPref: number;
}

export interface ActualizarUsuarioApi {
  primerNombre:     string;
  segundoNombre:    string;
  primerApellido:   string;
  segundoApellido:  string;
  correo:           string;
  contrasena:       string;
  fechaNacimiento:  string;
  idEstado:         number;
  idMetodoPagoPref: number;
}

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private readonly baseUrl        = 'http://localhost:8080';
  private readonly url            = `${this.baseUrl}/usuarios`;
  private readonly estadosUrl     = `${this.baseUrl}/estados-usuario`;
  private readonly metodosPagoUrl = `${this.baseUrl}/metodos-pago`;

  constructor(private http: HttpClient) {}

  getUsuarios(): Observable<UsuarioApi[]> {
    return this.http.get<UsuarioApi[]>(this.url, {
      headers: { Accept: 'application/json' },
    });
  }

  getUsuarioPorCedula(cedula: string): Observable<UsuarioApi> {
    return this.http.get<UsuarioApi>(`${this.url}/${cedula}`, {
      headers: { Accept: 'application/json' },
    });
  }

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

  crearUsuario(usuario: CrearUsuarioApi): Observable<string> {
    return this.http.post(this.url, usuario, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text',
    });
  }

  actualizarUsuario(cedula: string, usuario: ActualizarUsuarioApi): Observable<string> {
    return this.http.put(`${this.url}/${cedula}`, usuario, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text',
    });
  }

  eliminarUsuario(cedula: string): Observable<string> {
    return this.http.delete(`${this.url}/${cedula}`, {
      responseType: 'text',
    });
  }
}
