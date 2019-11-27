import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DespesaService {

  constructor(private http: HttpClient) { }

  listarDespesasAtivasPorCategoria(mes: number, ano: number) {
    let params = new HttpParams();
    params = params.append('mes', mes.toString());
    params = params.append('ano', ano.toString());

    let url = 'http://localhost:8084/backend/api/despesa/porcategoriames';
    return this.http.get<any[]>(url, { params: params });
  }
}
