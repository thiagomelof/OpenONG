import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class ParceiroDeNegocioService {

  parceirosUrl = 'http://localhost:8084/backend/api/parceirodenegocio'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(`${this.parceirosUrl}`);
  }

  criar(parceiro: any) {
    return this.http.post(this.parceirosUrl, parceiro );
  }
}
