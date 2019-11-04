import { Injectable, EventEmitter } from '@angular/core';
import { Usuario } from './model-view/usuario';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private usuarioAutenticado: boolean = false;
  mostrarTelasEmitter = new EventEmitter<boolean>();

  loginUrl = 'http://localhost:8084/backend/api/login'

  constructor(private router: Router, private http: HttpClient) { }

  login(usuario: Usuario) {
    this.http.post<Usuario>(`${this.loginUrl}`, usuario).subscribe(user => {
      usuario = user
      console.log(usuario);
      debugger;
      if (usuario.nome != null) {
        console.log(usuario.nome);
        this.usuarioAutenticado = true;
        this.mostrarTelasEmitter.emit(true);
        this.router.navigate(['/']);
      }
      else {
        this.mostrarTelasEmitter.emit(false);
        this.usuarioAutenticado = false;
      }

    });
    //TODO: Corrigir subscribe retorno usuario


  }
}
