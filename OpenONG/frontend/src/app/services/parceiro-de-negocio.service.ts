import { Status } from './../model-view/const/status';
import { ParceiroDeNegocio } from '../model-view/parceiro-de-negocio';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'
import { TipoParceiro } from '../model-view/const/tipoparceiro';

@Injectable({
  providedIn: 'root'
})
export class ParceiroDeNegocioService {

  parceirosUrl = 'http://localhost:8084/backend/api/parceirodenegocio'

  constructor(private http: HttpClient) { }

  listarParceirosPorTipo(tipo: TipoParceiro, status: Status) {
    let params = new HttpParams();
    params = params.append('tipo', tipo);
    params = params.append('status', status);

    return this.http.get<any[]>(this.parceirosUrl + "/tipo", { params: params });
  }

  listarParceirosQuantidadePorTipoEPeriodo(tipo: TipoParceiro, status: Status) {
    let params = new HttpParams();
    params = params.append('tipo', tipo);
    params = params.append('status', status);

    return this.http.get<any[]>(this.parceirosUrl + "/porperiodo", { params: params });
  }

  add(parceiro: ParceiroDeNegocio) {
    return this.http.post(this.parceirosUrl, parceiro);
  }

  getParceiro(id: number) {
    return this.http.get<any>(this.parceirosUrl + "/" + id);
  }
}
