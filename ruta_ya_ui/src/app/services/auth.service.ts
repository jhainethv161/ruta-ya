import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly url = 'http://localhost:8080/auth/login';
  private readonly STORAGE_KEY = 'rutaya_auth';

  autenticado = signal(sessionStorage.getItem(this.STORAGE_KEY) === 'true');

  constructor(private http: HttpClient) {}

  login(correo: string, contrasena: string): Observable<boolean> {
    return this.http.post<boolean>(this.url, { correo, contrasena }).pipe(
      tap(ok => {
        if (ok) {
          sessionStorage.setItem(this.STORAGE_KEY, 'true');
          this.autenticado.set(true);
        }
      }),
    );
  }

  logout(): void {
    sessionStorage.removeItem(this.STORAGE_KEY);
    this.autenticado.set(false);
  }
}
