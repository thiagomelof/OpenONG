import { ConvenioMessage } from './../model-view/dto/convenio-message';
import { UtilsService } from './../services/utils.service';
import { CategoriaService } from './../services/categoria.service';
import { Categoria } from './../model-view/categoria';
import { ConvenioCategoria } from './../model-view/conveniocategoria';
import { ParceiroDeNegocioService } from './../services/parceiro-de-negocio.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Convenio } from '../model-view/Convenio';
import { ConvenioService } from '../services/convenio.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, startWith, map } from 'rxjs/operators';
import { MatSnackBar, MatTableDataSource } from '@angular/material';
import { Location } from '@angular/common';
import { ParceiroDeNegocio } from '../model-view/parceiro-de-negocio';
import { Observable } from 'rxjs';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { AppDateAdapter, APP_DATE_FORMATS } from '../shared/format-datepicker';

@Component({
  selector: 'app-convenio',
  templateUrl: './convenio.component.html',
  styleUrls: ['../shared/shared-form.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: AppDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS }
  ]
})
export class ConvenioComponent implements OnInit {

  displayedColumns = ['Categoria', 'Percentual', 'Remover'];
  dataSource: MatTableDataSource<ConvenioCategoria>;
  public progressBarMode;
  parceiroDeNegocio: ParceiroDeNegocio[];
  categorias: Categoria[];
  convenio = new Convenio();
  convenioCategoria = new ConvenioCategoria();
  convenioMessage = new ConvenioMessage();
  isAddMode = false;
  saving = true;
  retorno: any;
  router: Router;
  msg: string;
  parceiroDeNegociosFiltradas: Observable<any[]>;
  nomeFormControl = new FormControl('', [Validators.required]);
  tipoControl = new FormControl('', [Validators.required]);
  unidadeControl = new FormControl('', [Validators.required]);
  parceiroDeNegocioControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();
  constructor(private utilsService: UtilsService, private convenioServer: ConvenioService, private parceiroDeNegocioServer: ParceiroDeNegocioService, private categoriaServer: CategoriaService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar, private location: Location) {
    this.convenioMessage = new ConvenioMessage();
    this.dataSource = new MatTableDataSource();
    this.convenio.usuarioCriacao = new Usuario();
    this.convenio.parceiroDeNegocio = new ParceiroDeNegocio();
    this.convenioCategoria = new ConvenioCategoria();
    this.convenioCategoria.categoria = new Categoria();
    this.convenioCategoria.convenio = new Convenio();

    this.parceiroDeNegocioServer.listarAtivas().subscribe(pn => {
      this.parceiroDeNegocio = pn;

      if (this.parceiroDeNegocio != undefined) {
        this.parceiroDeNegociosFiltradas = this.parceiroDeNegocioControl.valueChanges.pipe(startWith(null),
          map(parceiroDeNegocio => parceiroDeNegocio ? this.filtrarParceiroDeNegocios(parceiroDeNegocio) : this.parceiroDeNegocio.slice()));
      }
    })

    this.categoriaServer.listarAtivas().subscribe(cat => {
      this.categorias = cat;
    })


  }

  AdicionaCategoria(): ConvenioCategoria {

    let _convenio = new ConvenioCategoria();
    _convenio.percentual = this.convenioCategoria.percentual;
    _convenio.categoria = this.convenioCategoria.categoria;
    _convenio.linha = this.utilsService.GerarHash();
    _convenio.convenio = new Convenio();
    return _convenio;
  }

  removeLinha(row) {
    this.dataSource.data.splice(this.dataSource.data.indexOf(row), 1);
    console.log(this.dataSource.data)
    this.dataSource.filter = "";
  }

  addLinha() {
    this.dataSource.data.push(this.AdicionaCategoria());
    this.dataSource.filter = "";
  }

  filtrarParceiroDeNegocios(name: string) {
    return this.parceiroDeNegocio.filter(pn =>
      pn.nome.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }


  ngOnInit() {

    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.convenio.status = true;
      } else {
        this.convenioServer.getConvenio(params.id).subscribe(cat => {
          this.convenio = cat;

        })
      }
    }
    )
    this.retorno = {};

  }

  addOrUpdate() {

    this.componentesRequest(true);
    if (this.isAddMode) {
      this.convenio.usuarioCriacao.id = 1;
    } else {
      this.convenio.usuarioModificacao.id = 1;
    }


    this.convenioMessage.convenio = this.convenio;
    this.convenioMessage.categorias = this.dataSource.data;
    this.convenioServer.add(this.convenioMessage).pipe(finalize(() => {
      this.componentesRequest(false);
    })).subscribe(dados => {

      if (dados != undefined) {
        if (this.isAddMode) {
          this.msg = "Convenio cadastrado com sucesso!";
          this.convenio = new Convenio();
          this.convenio.parceiroDeNegocio = new ParceiroDeNegocio();
        }
        else {
          this.msg = "Convenio atualizado com sucesso!";
        }
      } else {
        if (this.isAddMode) {
          this.msg = "Erro ao cadastrar convenio";
          this.convenio = new Convenio();
        }
        else {
          this.msg = "Erro ao atualizar convenio";
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

  onChangeParceiro(event) {
    if (this.parceiroDeNegocio != undefined) {
      if (event != undefined && event != "") {
        try {
          this.convenio.parceiroDeNegocio.id = this.parceiroDeNegocio.find(x => x.nome === event).id;
          console.log(this.convenio.parceiroDeNegocio.id);
        } catch (error) { }

      }
    }
  }


  onChangeCategoria(event) {
    console.log(event);
    if (this.categorias != undefined) {
      if (event != undefined && event != "") {
        try {
          this.convenioCategoria.categoria.nome = this.categorias.find(x => x.id === event).nome;
          console.log(this.convenioCategoria.categoria.id);
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