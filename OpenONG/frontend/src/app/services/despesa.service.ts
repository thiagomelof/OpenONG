import { DespesaMessage } from './../model-view/dto/despesa-message';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DespesaService {

  despesaUrl = 'http://localhost:8084/backend/api/despesa'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.despesaUrl);
  }

  listarAtivas() {
    return this.http.get<any[]>(this.despesaUrl + "/ativo");
  }

  add(despesa: DespesaMessage) {
    return this.http.post(this.despesaUrl, despesa);
  }

  getDespesa(id: number) {
    return this.http.get<any>(this.despesaUrl + "/" + id);
  }
}
