import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Categoria } from '../model-view/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  categoriaUrl = 'http://localhost:8084/backend/api/categoria'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.categoriaUrl);
  }

  listarAtivas() {
    return this.http.get<any[]>(this.categoriaUrl + "/ativo");
  }

  add(categoria: Categoria) {
    return this.http.post(this.categoriaUrl, categoria);
  }

  getCategoria(id: number) {
    return this.http.get<any>(this.categoriaUrl + "/" + id);
  }
}
