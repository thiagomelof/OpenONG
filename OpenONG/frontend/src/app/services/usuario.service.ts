import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../model-view/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  usuarioUrl = 'http://localhost:8084/backend/api/usuario'

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<any[]>(this.usuarioUrl);
  }

  listarAtivos() {
    return this.http.get<any[]>(this.usuarioUrl + "/ativo");
  }

  add(usuario: Usuario) {
    return this.http.post(this.usuarioUrl, usuario);
  }

  getUsuario(id: number) {
    return this.http.get<any>(this.usuarioUrl + "/" + id);
  }
}
