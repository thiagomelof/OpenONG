import { Injectable, EventEmitter } from '@angular/core';
import { Usuario } from '../model-view/usuario';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loginUrl = 'http://localhost:8084/backend/api/login'

  constructor(private http: HttpClient) { }

  login(usuario: Usuario) {
    return this.http.post<Usuario>(`${this.loginUrl}`, usuario);
  }
}
