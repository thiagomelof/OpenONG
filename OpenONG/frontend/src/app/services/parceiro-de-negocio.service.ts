import { ParceiroDeNegocio } from '../model-view/parceiro-de-negocio';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class ParceiroDeNegocioService {

  parceirosUrl = 'http://localhost:8084/backend/api/parceirodenegocio'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.parceirosUrl);
  }

  listarAtivas() {
    return this.http.get<any[]>(this.parceirosUrl + "/ativo");
  }

  add(parceiro: ParceiroDeNegocio) {
    return this.http.post(this.parceirosUrl, parceiro);
  }

  getParceiro(id: number) {
    return this.http.get<any>(this.parceirosUrl + "/" + id);
  }
}
