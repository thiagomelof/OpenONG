import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS, MAT_MOMENT_DATE_FORMATS } from '@angular/material-moment-adapter';
import { RetornoMessage } from './../model-view/dto/retorno-message';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher, DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { Categoria } from '../model-view/categoria';
import { CategoriaService } from '../services/categoria.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['../shared/shared-form.component.scss'],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'pt' },
  { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS] },
  { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS }
  ]
})
export class CategoriaComponent implements OnInit {
  public progressBarMode;
  categoria = new Categoria();
  isAddMode = false;
  saving = true;
  msg: string;
  retorno = new RetornoMessage();

  nomeFormControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();

  constructor(private categoriaServer: CategoriaService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar, private router: Router) { }

  ngOnInit() {
    this.categoria.usuarioCriacao = new Usuario();

    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.categoria.status = true;
      } else {
        this.categoriaServer.getCategoria(params.id).subscribe(cat => { this.categoria = cat; })
        this.categoria.usuarioModificacao = new Usuario();
      }
    }
    )
  }

  addOrUpdate() {

    this.controleDeTelaRequest(true);

    this.setUsuario();

    this.categoriaServer.add(this.categoria).pipe(finalize(() => { this.controleDeTelaRequest(false); })).subscribe(dados => {
      this.retorno = <RetornoMessage>dados;
      this.msg = "";
      if (this.retorno.erros.length > 0) {
        this.retorno.erros.forEach(erro => { this.msg += erro.msgErro + '\n'; });
      } else {
        if (this.isAddMode) {
          this.msg = "Categoria cadastrada com sucesso!";
        }
        else {
          this.msg = "Categoria atualizada com sucesso!";
        }
        this.router.navigate(['auth/categoria/list']);
      }
      this.getStatusBar(this.msg);
    }
    );
  }

  private setUsuario() {
    if (this.isAddMode) {
      this.categoria.usuarioCriacao.id = 1;
    }
    else {
      this.categoria.usuarioModificacao.id = 1;
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

  getStatusBar(msgSnack: string) {
    this.snackBar.open(msgSnack, "FECHAR", { duration: 3500 });
  }
}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}