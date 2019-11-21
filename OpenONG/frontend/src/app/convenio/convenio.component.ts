import { RetornoMessage } from './../model-view/dto/retorno-message';
import { ConvenioMessage } from './../model-view/dto/convenio-message';
import { UtilsService } from './../services/utils.service';
import { CategoriaService } from './../services/categoria.service';
import { Categoria } from './../model-view/categoria';
import { ConvenioCategoria } from '../model-view/convenio-categoria';
import { ParceiroDeNegocioService } from './../services/parceiro-de-negocio.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Convenio } from '../model-view/convenio';
import { ConvenioService } from '../services/convenio.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, startWith, map } from 'rxjs/operators';
import { MatSnackBar, MatTableDataSource } from '@angular/material';
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
  isAddMode = false;
  saving = true;
  msg: string;
  convenioCategoria = new ConvenioCategoria();
  categoriasConvenio: ConvenioCategoria[];
  convenio = new ConvenioMessage();
  categorias: Categoria[];
  parceiroDeNegocio: ParceiroDeNegocio[];
  parceiroDeNegociosFiltradas: Observable<any[]>;
  parceiroDeNegocioControl = new FormControl('', [Validators.required]);
  nomeFormControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();
  retorno = new RetornoMessage();

  constructor(private utilsService: UtilsService, private convenioServer: ConvenioService, private parceiroDeNegocioServer: ParceiroDeNegocioService, private categoriaServer: CategoriaService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar, private router: Router) { }

  ngOnInit() {
    this.categoriasConvenio = [];
    this.convenio = new ConvenioMessage();
    this.dataSource = new MatTableDataSource();
    this.convenio.convenio = new Convenio();
    this.convenio.convenio.usuarioCriacao = new Usuario();
    this.convenio.convenio.parceiroDeNegocio = new ParceiroDeNegocio();

    this.convenioCategoria = new ConvenioCategoria();
    this.convenioCategoria.categoria = new Categoria();
    this.convenioCategoria.convenio = new Convenio();

    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.convenio.convenio.status = true;
      } else {

        this.convenio.convenio.usuarioModificacao = new Usuario();

        this.convenioServer.getConvenio(params.id).subscribe(conv => {

          this.convenio = conv;
          this.convenio.categorias.forEach(cat => {
            this.addLinhaEdit(cat);
          });
        })
      }
    }
    )

    this.parceiroDeNegocioServer.listarAtivas().subscribe(pn => {
      this.parceiroDeNegocio = pn;

      if (this.parceiroDeNegocio != undefined) {
        this.parceiroDeNegociosFiltradas = this.parceiroDeNegocioControl.valueChanges.pipe(startWith(null),
          map(parceiroDeNegocio => parceiroDeNegocio ? this.filtrarParceiroDeNegocios(parceiroDeNegocio) : this.parceiroDeNegocio.slice()));
      }

    })

    this.categoriaServer.listarAtivas().subscribe(cat => { this.categorias = cat; })
  }

  AdicionaCategoria(): ConvenioCategoria {

    let _convenio = new ConvenioCategoria();
    _convenio.percentual = this.convenioCategoria.percentual;
    _convenio = JSON.parse(JSON.stringify(this.convenioCategoria));
    _convenio.linha = this.utilsService.GerarHash();

    return _convenio;

  }

  removeLinha(row) {
    this.dataSource.data.splice(this.dataSource.data.indexOf(row), 1);
    this.dataSource.filter = "";
  }

  addLinhaEdit(categoria: ConvenioCategoria) {
    this.dataSource.data.push(categoria);
    this.dataSource.filter = "";
  }

  addLinha() {
    this.categoriasConvenio.push(this.AdicionaCategoria());
    this.dataSource.data = this.categoriasConvenio;
    this.dataSource.filter = "";
  }

  filtrarParceiroDeNegocios(name: string) {
    return this.parceiroDeNegocio.filter(pn =>
      pn.nome.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }


  addOrUpdate() {

    this.controleDeTelaRequest(true);

    this.setUsuario();

    this.convenio.categorias = this.dataSource.data;

    this.convenioServer.add(this.convenio).pipe(finalize(() => { this.controleDeTelaRequest(false); })).subscribe(dados => {

      this.retorno = <RetornoMessage>dados;
      this.msg = "";
      if (this.retorno.erros.length > 0) {
        this.retorno.erros.forEach(erro => { this.msg += erro.msgErro + '\n'; });
      } else {
        if (this.isAddMode) {
          this.msg = "Convênio cadastrado com sucesso!";
        }
        else {
          this.msg = "Convênio atualizado com sucesso!";
        }
        this.router.navigate(['auth/convenio/list']);
      }
      this.getStatusBar(this.msg);
    }
    );

  }

  private setUsuario() {
    if (this.isAddMode) {
      this.convenio.convenio.usuarioCriacao.id = 1;
    }
    else {
      this.convenio.convenio.usuarioModificacao.id = 1;
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

  onChangeParceiro(event) {

    if (this.parceiroDeNegocio != undefined) {

      this.convenio.convenio.parceiroDeNegocio.id = -1;

      if (event != undefined && event != "") {
        try {
          this.convenio.convenio.parceiroDeNegocio.id = this.parceiroDeNegocio.find(x => x.nome === event).id;
        } catch (error) { }

      }
    }
  }


  onChangeCategoria(event) {
    if (this.categorias != undefined) {
      if (event != undefined && event != "") {
        try {
          this.convenioCategoria.categoria.nome = this.categorias.find(x => x.id === event).nome;
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
