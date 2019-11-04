import { Component, OnInit, NgModule } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators, FormGroup } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Categoria } from '../model-view/Categoria';
import { CategoriaService } from '../categoria.service';
import { Usuario } from '../model-view/usuario';


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
  retorno: any;

  nomeFormControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();
  checkAtivo = true;

  constructor(private categoriaServer: CategoriaService) { }

  ngOnInit() {
    this.retorno = {};
    this.categoria.usuarioCriacao = new Usuario();
  }

  add() {
    this.categoria.usuarioCriacao.id = 1;
    this.categoriaServer.add(this.categoria).subscribe(dados => console.log(dados));

    // this.categoriaServer.add(this.categoria).finally(() => {
    //   alert("ALERT DO FINALLY");
    // }).subscribe(dados => alert("ALERT DO FINALLY"), error => alert("ERROR"));
  }

}
