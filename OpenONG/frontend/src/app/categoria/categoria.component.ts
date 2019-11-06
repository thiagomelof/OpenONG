import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators, FormGroup } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Categoria } from '../model-view/Categoria';
import { CategoriaService } from '../categoria.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute } from '@angular/router';
import { finalize } from 'rxjs/operators';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['./categoria.component.scss']
})
export class CategoriaComponent implements OnInit {
  categoria = new Categoria();
  isAddMode = false;
  saving = true;

  retorno: any;

  nomeFormControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();

  constructor(private categoriaServer: CategoriaService, private activatedRoute: ActivatedRoute) { }


  ngOnInit() {
    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.categoria.status = true;
      } else {
        this.categoriaServer.getCategoria(params.id).subscribe(
          cat => {
            this.categoria = cat;
          }
        )
      }
    }
    )
    this.retorno = {};

  }

  addOrUpdate() {

    if (this.isAddMode) {
      this.categoria.usuarioCriacao = new Usuario();
      this.categoria.usuarioCriacao.id = 1;
    } else {
      this.categoria.usuarioModificacao = new Usuario();
      this.categoria.usuarioModificacao.id = 1;
    }
    this.saving = false;

    this.categoriaServer.add(this.categoria).pipe(finalize(() =>{ 
      console.log("finalize");
      this.saving = true
    })

      ).subscribe(dados => {
        console.log("subscribe");
      console.log(dados)
    }
    );

  }

}
