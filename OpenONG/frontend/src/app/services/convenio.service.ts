import { ConvenioMessage } from './../model-view/dto/convenio-message';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConvenioService {
  convenioUrl = 'http://localhost:8084/backend/api/convenio'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.convenioUrl);
  }

  listarAtivos() {
    return this.http.get<any[]>(this.convenioUrl + "/ativo");
  }

  listarPorParceiro(idParceiro: number) {
    let params = new HttpParams();
    
    params = params.append('id', idParceiro.toString());

    return this.http.get<any[]>(this.convenioUrl + "/porparceiro", { params: params });
  }

  add(convenio: ConvenioMessage) {
    return this.http.post(this.convenioUrl, convenio);
  }

  getConvenio(id: number) {
    return this.http.get<any>(this.convenioUrl + "/" + id);
  }
}
