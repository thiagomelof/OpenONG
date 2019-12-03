import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS, MAT_MOMENT_DATE_FORMATS } from '@angular/material-moment-adapter';
import { RetornoMessage } from './../model-view/dto/retorno-message';
import { CategoriaService } from './../services/categoria.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher, DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { Item } from '../model-view/item';
import { ItemService } from '../services/item.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, startWith, map } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';
import { TipoItem } from '../model-view/const/tipoitem';
import { Categoria } from '../model-view/categoria';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-item-servico',
  templateUrl: './item-servico.component.html',
  styleUrls: ['../shared/shared-form.component.scss'],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'pt' },
  { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS] },
  { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS }
  ]
})
export class ItemServicoComponent implements OnInit {

  tiposDeitem = [
    { value: TipoItem.Item, name: 'Item' },
    { value: TipoItem.Servico, name: 'Serviço' },
    { value: TipoItem.Dinheiro, name: 'Dinheiro' }
  ];

  public progressBarMode;
  categorias: any[];
  item = new Item();
  isAddMode = false;
  saving = true;
  msg: string;
  categoriasFiltradas: Observable<any[]>;
  nomeFormControl = new FormControl('', [Validators.required]);
  tipoControl = new FormControl('', [Validators.required]);
  unidadeControl = new FormControl('', [Validators.required]);
  categoriaControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();
  retorno = new RetornoMessage();
  constructor(private itemServer: ItemService, private categoriaServer: CategoriaService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar, private router: Router) {
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
  }

  addOrUpdate(form: NgForm) {

    this.controleDeTelaRequest(true);
    if (this.isAddMode) {

      this.item.usuarioCriacao = new Usuario();
      this.item.usuarioCriacao.id = 1;

    } else {
      this.item.usuarioModificacao.id = 1;
    }

    this.itemServer.add(this.item).pipe(finalize(() => { this.controleDeTelaRequest(false); })).subscribe(dados => {
      this.retorno = <RetornoMessage>dados;
      this.msg = "";
      if (this.retorno.erros.length > 0) {
        this.retorno.erros.forEach(erro => { this.msg += erro.msgErro + '\n'; });
      } else {
        if (this.isAddMode) {
          this.msg = "Item cadastrado com sucesso!";
        }
        else {
          this.msg = "Item atualizado com sucesso!";
        }
        this.router.navigate(['auth/item/list']);
      }
      this.getStatusBar(this.msg);
    }
    );

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

  onChange(event) {
    this.item.categoria.id = -1;
    if (this.categorias != undefined) {
      if (event != undefined && event != "") {
        try {
          this.item.categoria.id = this.categorias.find(x => x.nome === event).id;

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
