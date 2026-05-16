import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ReporteService {
  private readonly baseUrl = 'http://localhost:8080/reportes';

  constructor(private http: HttpClient) {}

  descargarExcel(id: string, params: Record<string, string | number>): Observable<HttpResponse<Blob>> {
    let httpParams = new HttpParams();
    for (const [k, v] of Object.entries(params)) {
      if (v !== '' && v !== null && v !== undefined) {
        httpParams = httpParams.set(k, String(v));
      }
    }
    return this.http.get(`${this.baseUrl}/excel/${id}`, {
      headers: { Accept: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' },
      params: httpParams,
      responseType: 'blob',
      observe: 'response',
    });
  }
}
