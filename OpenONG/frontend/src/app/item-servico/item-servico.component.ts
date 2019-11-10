import { CategoriaService } from './../services/categoria.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Item } from '../model-view/Item';
import { ItemService } from '../services/item.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, startWith, map } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';
import { Location } from '@angular/common';
import { TipoItem } from '../model-view/const/tipoitem';
import { Categoria } from '../model-view/Categoria';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-item-servico',
  templateUrl: './item-servico.component.html',
  styleUrls: ['../shared/shared-form.component.scss']
})
export class ItemServicoComponent implements OnInit {

  tiposDeitem = [
    { value: TipoItem.Dinheiro, name: 'Dinheiro' },
    { value: TipoItem.Item, name: 'Item' },
    { value: TipoItem.Servico, name: 'Serviço' },
  ];

  public progressBarMode;
  categorias: any[];
  item = new Item();
  isAddMode = false;
  saving = true;
  retorno: any;
  router: Router;
  msg: string;
  categoriasFiltradas: Observable<any[]>;
  nomeFormControl = new FormControl('', [Validators.required]);
  tipoControl = new FormControl('', [Validators.required]);
  unidadeControl = new FormControl('', [Validators.required]);
  categoriaControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();
  constructor(private itemServer: ItemService, private categoriaServer: CategoriaService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar, private location: Location) {
    this.item.categoria = new Categoria();
    this.categoriaServer.listarAtivas().subscribe(cat => {
      this.categorias = cat;

      if (this.categorias != undefined) {
        this.categoriasFiltradas = this.categoriaControl.valueChanges.pipe(startWith(null),
          map(categoria => categoria ? this.filtrarCategorias(categoria) : this.categorias.slice()));
      }
    })



  }
  filtrarCategorias(name: string) {
    return this.categorias.filter(cat =>
      cat.nome.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  ngOnInit() {

    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.item.status = true;
      } else {
        this.itemServer.getItem(params.id).subscribe(cat => { this.item = cat; })
        this.item.usuarioCriacao = new Usuario();
        this.item.usuarioModificacao = new Usuario();


      }
    }
    )
    this.retorno = {};

  }

  addOrUpdate(form: NgForm) {

    this.componentesRequest(true);
    if (this.isAddMode) {

      this.item.usuarioCriacao = new Usuario();
      this.item.usuarioCriacao.id = 1;

    } else {
      this.item.usuarioModificacao.id = 1;
    }

    this.itemServer.add(this.item).pipe(finalize(() => {

      this.componentesRequest(false);

    })

    ).subscribe(dados => {


      if (dados != undefined) {
        if (this.isAddMode) {
          this.msg = "Item cadastrado com sucesso!";
          this.item = new Item();
          this.item.categoria = new Categoria();
        }
        else {
          this.msg = "Item atualizado com sucesso!";
        }
      } else {
        if (this.isAddMode) {
          this.msg = "Erro ao cadastrar item";
          this.item = new Item();
        }
        else {
          this.msg = "Erro ao atualizar item";
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

  onChange(event) {
    if (this.categorias != undefined) {
      if (event != undefined && event != "") {
        try {
          this.item.categoria.id = this.categorias.find(x => x.nome === event).id;
          console.log(this.item.categoria.id);
        } catch (error) { }

      }
    }
  }
}


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
