import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Beneficiado } from '../model-view/beneficiado';

@Injectable({
  providedIn: 'root'
})
export class BeneficiadoService {
  beneficiadoUrl = 'http://localhost:8084/backend/api/beneficiado'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.beneficiadoUrl);
  }

  listarAtivos() {
    return this.http.get<any[]>(this.beneficiadoUrl + "/ativo");
  }

  add(beneficiado: Beneficiado) {
    return this.http.post(this.beneficiadoUrl, beneficiado);
  }

  getBeneficiado(id: number) {
    return this.http.get<any>(this.beneficiadoUrl + "/" + id);
  }
}
