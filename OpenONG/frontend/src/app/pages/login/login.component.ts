import { MatSnackBar } from '@angular/material';
import { Component, OnInit, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
// import { AuthService } from '../../core/auth.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Usuario } from '../../model-view/usuario';
import { LoginService } from '../../services/login.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  private usuario: Usuario = new Usuario();
  mostrarTelasEmitter = new EventEmitter<boolean>();
  userForm: FormGroup;
  formErrors = {
    'email': '',
    'password': ''
  };
  validationMessages = {
    'email': {
      'required': 'Por favor, insira seu e-mail',
      'email': 'Por favor, insira um e-mail válido'
    },
    'password': {
      'required': 'Por favor, insira sua senha',
      'pattern': 'A senha deve conter letras e números',
      'minlength': 'Por favor, insira mais que 4 caracteres',
      'maxlength': 'Por favor, insira menos que 25 caracteres',
    }
  };

  constructor(private router: Router, private fb: FormBuilder, private loginService: LoginService, public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.buildForm();
  }

  buildForm() {
    this.userForm = this.fb.group({
      'email': ['', [
        Validators.required,
        Validators.email
      ]
      ],
      'password': ['', [
        Validators.pattern('^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$'),
        Validators.minLength(6),
        Validators.maxLength(25)
      ]
      ],
    });

    this.userForm.valueChanges.subscribe(data => this.onValueChanged(data));
    this.onValueChanged();
  }

  onValueChanged(data?: any) {
  }

  login() {
    this.loginService.login(this.usuario).subscribe(user => {
      let usuario = user
      if (usuario.nome != null && usuario.nome != "") {
        this.mostrarTelasEmitter.emit(true);
        this.router.navigate(['/']);
      }
      else {
        this.snackBar.open("Usuário e/ou senha incorreto(s)", "FECHAR", { duration: 3500 });
        this.mostrarTelasEmitter.emit(false);
      }
    });
  }
}

