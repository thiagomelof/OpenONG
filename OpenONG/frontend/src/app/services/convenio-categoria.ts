import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConvenioCategoria } from '../model-view/conveniocategoria';

@Injectable({
  providedIn: 'root'
})
export class ConvenioCategoriaService {
  conveniocategoriaUrl = 'http://localhost:8084/backend/api/conveniocategoria'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.conveniocategoriaUrl);
  }
  
  add(conveniocategoria: ConvenioCategoria) {
    return this.http.post(this.conveniocategoriaUrl, conveniocategoria);
  }

  listarPorConvenio(id: number) {
    return this.http.get<any>(this.conveniocategoriaUrl + "/" + id);
  }
}
