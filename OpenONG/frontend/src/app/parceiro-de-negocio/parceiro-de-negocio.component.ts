import { Usuario } from './../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { ParceiroDeNegocio } from '../model-view/parceiro-de-negocio';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { tiposDeParceiro, estados } from './helpers.data';
import { ParceiroDeNegocioService } from '../services/parceiro-de-negocio.service';
import { finalize } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';
import { Endereco } from '../model-view/Endereco';

@Component({
  selector: 'app-parceiro-de-negocio',
  templateUrl: './parceiro-de-negocio.component.html',
  styleUrls: ['../shared/shared-form.component.scss']
})

export class ParceiroDeNegocioComponent implements OnInit {
  tiposDeParceiro = tiposDeParceiro;
  estados = estados;
  nomeFormControl = new FormControl('', [Validators.required]);
  codigoFormControl = new FormControl('', [Validators.required]);
  emailFormControl = new FormControl('', [Validators.required, Validators.pattern(EMAIL_REGEX)]);
  tipoDeParceiroControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();

  public progressBarMode;
  parceiro = new ParceiroDeNegocio();
  isAddMode = false;
  saving = true;

  msg: string;

  constructor(private parceiroServer: ParceiroDeNegocioService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar, private router: Router) { }

  ngOnInit() {
    this.parceiro.usuarioCriacao = new Usuario();
    this.parceiro.endereco = new Endereco();

    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.parceiro.status = true;
      } else {
        this.parceiroServer.getParceiro(params.id).subscribe(pn => { this.parceiro = pn; })
        this.parceiro.usuarioCriacao = new Usuario();
      }
    }
    )
  }

  addOrUpdate() {

    this.controleDeTelaRequest(true);

    this.setUsuario();

    this.parceiroServer.add(this.parceiro).pipe(finalize(() => { this.controleDeTelaRequest(false); })).subscribe(dados => {

      if (dados != undefined) {
        if (this.isAddMode) {
          this.msg = "Parceiro cadastrada com sucesso!";
        }
        else {
          this.msg = "Parceiro atualizada com sucesso!";
        }

        this.router.navigate(['auth/parceiro/list']);

      } else {
        if (this.isAddMode) {
          this.msg = "Erro ao cadastrar parceiro";
        }
        else {
          this.msg = "Erro ao atualizar parceiro";
        }
      }
      this.getStatusBar(this.msg);
    }
    );

  }

  private setUsuario() {
    if (this.isAddMode) {
      this.parceiro.usuarioCriacao.id = 1;
    }
    else {
      this.parceiro.usuarioModificacao.id = 1;
    }
  }

  controleDeTelaRequest(progressBar: boolean) {
    if (progressBar) {
      this.progressBarMode = "indeterminate";
      this.saving = false;
    } else {
      this.progressBarMode = "";
      this.saving = true;
    }
  }

  getStatusBar(msgSnack: string) { this.snackBar.open(msgSnack, "OK", { duration: 4000 }); }

}

const EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
