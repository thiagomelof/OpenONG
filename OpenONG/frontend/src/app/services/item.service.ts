import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Item } from '../model-view/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  itemUrl = 'http://localhost:8084/backend/api/item'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.itemUrl);
  }

  listarAtivos() {
    return this.http.get<any[]>(this.itemUrl + "/ativo");
  }

  listarPorCategoriasDoConvenio(idConvenio: number) {
    let params = new HttpParams();

    params = params.append('id', idConvenio.toString());

    return this.http.get<any[]>(this.itemUrl + "/porcategoriasdeconvenio", { params: params });
  }

  add(item: Item) {
    return this.http.post(this.itemUrl, item);
  }

  getItem(id: number) {
    return this.http.get<any>(this.itemUrl + "/" + id);
  }
}
