import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Convenio } from '../model-view/convenio';

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

  add(convenio: Convenio) {
    return this.http.post(this.convenioUrl, convenio);
  }

  getConvenio(id: number) {
    return this.http.get<any>(this.convenioUrl + "/" + id);
  }
}
