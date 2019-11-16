import { DoacaoMessage } from './../model-view/dto/doacao-message';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DoacaoService {

  doacaoUrl = 'http://localhost:8084/backend/api/doacao'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.doacaoUrl);
  }

  listarAtivas() {
    return this.http.get<any[]>(this.doacaoUrl + "/ativo");
  }

  add(doacao: DoacaoMessage) {
    return this.http.post(this.doacaoUrl, doacao);
  }

  getDoacao(id: number) {
    return this.http.get<any>(this.doacaoUrl + "/" + id);
  }
}
