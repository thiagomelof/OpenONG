import { RetornoMessage } from './../model-view/dto/retorno-message';
import { ConvenioService } from './../services/convenio.service';
import { Convenio } from './../model-view/convenio';
import { DespesaMessage } from './../model-view/dto/despesa-message';
import { UtilsService } from './../services/utils.service';
import { ItemService } from './../services/item.service';
import { Item } from './../model-view/item';
import { DespesaItem } from '../model-view/despesa-item';
import { ParceiroDeNegocioService } from './../services/parceiro-de-negocio.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Despesa } from '../model-view/Despesa';
import { DespesaService } from '../services/despesa.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, startWith, map } from 'rxjs/operators';
import { MatSnackBar, MatTableDataSource } from '@angular/material';
import { ParceiroDeNegocio } from '../model-view/parceiro-de-negocio';
import { Observable } from 'rxjs';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { AppDateAdapter, APP_DATE_FORMATS } from '../shared/format-datepicker';

@Component({
  selector: 'app-despesa',
  templateUrl: './despesa.component.html',
  styleUrls: ['../shared/shared-form.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: AppDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS }
  ]
})
export class DespesaComponent implements OnInit {
  public progressBarMode;
  isAddMode = false;
  saving = true;
  msg: string;

  displayedColumns = ['Item', 'Valor', 'Quantidade', 'Editar', 'Remover'];
  despesa = new DespesaMessage();
  despesaItem = new DespesaItem();
  dataSource: MatTableDataSource<DespesaItem>;
  convenios: Convenio[];
  itens: Item[];
  parceirosDeNegocio: ParceiroDeNegocio[];
  parceiroDeNegociosFiltradas: Observable<any[]>;
  parceiroDeNegocioControl = new FormControl('', [Validators.required]);
  nomeFormControl = new FormControl('', [Validators.required]);
  retorno = new RetornoMessage();

  matcher = new MyErrorStateMatcher();
  constructor(private utilsService: UtilsService, private despesaServer: DespesaService, private parceiroDeNegocioServer: ParceiroDeNegocioService, private convenioServer: ConvenioService, private itemServer: ItemService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar, private router: Router) { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource();
    this.despesa = new DespesaMessage();
    this.despesa.despesa = new Despesa();
    this.despesa.despesa.usuarioCriacao = new Usuario();
    this.despesa.despesa.convenio = new Convenio();
    this.despesa.despesa.parceiroDeNegocio = new ParceiroDeNegocio();
    this.despesaItem = new DespesaItem();
    this.despesaItem.item = new Item();
    this.despesaItem.despesa = new Despesa();
    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.despesa.despesa.status = true;
      } else {
        this.despesa.despesa.usuarioModificacao = new Usuario();

        this.despesaServer.getDespesa(params.id).subscribe(d => {
          this.despesa = d;
          this.despesa.itens.forEach(item => {
            this.addLinhaEdit(item);
          });

        })
      }
    }
    )

    this.parceiroDeNegocioServer.listarAtivas().subscribe(pn => {
      this.parceirosDeNegocio = pn;

      if (this.parceirosDeNegocio != undefined) {
        this.parceiroDeNegociosFiltradas = this.parceiroDeNegocioControl.valueChanges.pipe(startWith(null),
          map(parceiroDeNegocio => parceiroDeNegocio ? this.filtrarParceiroDeNegocios(parceiroDeNegocio) : this.parceirosDeNegocio.slice()));
      }
    })

    this.itemServer.listarAtivos().subscribe(cat => {
      this.itens = cat;
    })

    this.convenioServer.listarAtivos().subscribe(conv => {
      this.convenios = conv;
    })
  }


  AdicionaItem(): DespesaItem {

    let despesa = new DespesaItem();
    despesa = JSON.parse(JSON.stringify(this.despesaItem))
    despesa.linha = this.utilsService.GerarHash();

    return despesa;
  }

  removeLinha(row) {
    this.dataSource.data.splice(this.dataSource.data.indexOf(row), 1);
    this.dataSource.filter = "";
  }

  addLinhaEdit(item: DespesaItem) {
    this.dataSource.data.push(item);
    this.dataSource.filter = "";
  }

  addLinha() {
    this.dataSource.data.push(this.AdicionaItem());
    this.dataSource.filter = "";
  }

  filtrarParceiroDeNegocios(name: string) {
    return this.parceirosDeNegocio.filter(pn =>
      pn.nome.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  addOrUpdate() {

    this.controleDeTelaRequest(true);

    this.setUsuario();

    this.despesa.itens = this.dataSource.data;

    this.despesaServer.add(this.despesa).pipe(finalize(() => { this.controleDeTelaRequest(false); })).subscribe(dados => {

      this.retorno = <RetornoMessage>dados;
      this.msg = "";
      if (this.retorno.erros.length > 0) {
        this.retorno.erros.forEach(erro => { this.msg += erro.msgErro + '\n'; });
      } else {
        if (this.isAddMode) {
          this.msg = "Despesa cadastrada com sucesso!";
        }
        else {
          this.msg = "Despesa atualizada com sucesso!";
        }
        this.router.navigate(['auth/despesa/list']);
      }
      this.getStatusBar(this.msg);
    }
    );

  }

  private setUsuario() {
    if (this.isAddMode) {
      this.despesa.despesa.usuarioCriacao.id = 1;
    }
    else {
      this.despesa.despesa.usuarioModificacao.id = 1;
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
    this.despesa.despesa.parceiroDeNegocio.id = -1;
    if (this.parceirosDeNegocio != undefined) {
      if (event != undefined && event != "") {
        try {
          this.despesa.despesa.parceiroDeNegocio.id = this.parceirosDeNegocio.find(x => x.nome === event).id;
        } catch (error) { }

      }
    }
  }

  onChangeConvenio(event) {
    if (this.convenios != undefined) {
      if (event != undefined && event != "") {
        try {
          this.despesa.despesa.convenio.id = this.convenios.find(x => x.nome === event).id;
        } catch (error) { }

      }
    }
  }

  onChangeItem(event) {
    if (this.itens != undefined) {
      if (event != undefined && event != "") {
        try {
          this.despesaItem.item.nome = this.itens.find(x => x.id === event).nome;
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
