import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Categoria } from '../model-view/categoria';
import { CategoriaService } from '../services/categoria.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['../shared/shared-form.component.scss']
})
export class CategoriaComponent implements OnInit {
  public progressBarMode;
  categoria = new Categoria();
  isAddMode = false;
  saving = true;
  retorno: any;
  msg: string;

  nomeFormControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();

  constructor(private categoriaServer: CategoriaService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar) { }

  ngOnInit() {
    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.categoria.status = true;
      } else {
        this.categoriaServer.getCategoria(params.id).subscribe(cat => { this.categoria = cat; })
        this.categoria.usuarioCriacao = new Usuario();
        this.categoria.usuarioModificacao = new Usuario();
      }
    }
    )
    this.retorno = {};

  }

  addOrUpdate() {

    this.componentesRequest(true);
    if (this.isAddMode) {

      this.categoria.usuarioCriacao = new Usuario();
      this.categoria.usuarioCriacao.id = 1;

    } else {
      this.categoria.usuarioModificacao.id = 1;
    }

    this.categoriaServer.add(this.categoria).pipe(finalize(() => {

      this.componentesRequest(false);

    })

    ).subscribe(dados => {


      if (dados != undefined) {
        if (this.isAddMode) {
          this.msg = "Categoria cadastrada com sucesso!";
          this.categoria = new Categoria();
        }
        else {
          this.msg = "Categoria atualizada com sucesso!";
        }
      } else {
        if (this.isAddMode) {
          this.msg = "Erro ao cadastrar categoria";
          this.categoria = new Categoria();
        }
        else {
          this.msg = "Erro ao atualizar categoria";
        }
      }
      this.retornoRequest(this.msg);
    }
    );

  }

  componentesRequest(progressBar: boolean) {
    if (progressBar) {
      this.progressBarMode = "indeterminate";
      this.saving = false;
    } else {
      this.progressBarMode = "";
      this.saving = true;
    }
  }

  retornoRequest(msgSnack: string) {
    this.snackBar.open(msgSnack, "OK", {
      duration: 2000,

    });
  }
}


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}