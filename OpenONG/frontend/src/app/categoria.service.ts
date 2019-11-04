import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Categoria } from './model-view/Categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  categoriaUrl = 'http://localhost:8084/backend/api/categoria'

  constructor(private http: HttpClient) { }

  add(categoria: Categoria) {
    return this.http.post(this.categoriaUrl, categoria);
  }
}
