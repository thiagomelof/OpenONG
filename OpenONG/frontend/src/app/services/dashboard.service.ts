import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  listarDespesasAtivasPorCategoria() {
    let url = 'http://localhost:8084/backend/api/despesa/porcategoria';
    return this.http.get<any[]>(url);
  }

  listarDoacoesPorPeriodo(ano: number) {
    let params = new HttpParams();

    params = params.append('ano', ano.toString());

    let url = 'http://localhost:8084/backend/api/doacao/porperiodo';
    return this.http.get<any[]>(url, { params: params });
  }

  listarDespesasPorPeriodo(ano: number) {
    let params = new HttpParams();

    params = params.append('ano', ano.toString());

    let url = 'http://localhost:8084/backend/api/despesa/porperiodo';
    return this.http.get<any[]>(url, { params: params });
  }
}
