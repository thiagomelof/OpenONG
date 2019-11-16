import { ConvenioService } from './../services/convenio.service';
import { Convenio } from './../model-view/convenio';
import { DoacaoMessage } from './../model-view/dto/doacao-message';
import { UtilsService } from './../services/utils.service';
import { ItemService } from './../services/item.service';
import { Item } from './../model-view/item';
import { DoacaoItem } from '../model-view/doacao-item';
import { ParceiroDeNegocioService } from './../services/parceiro-de-negocio.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Doacao } from '../model-view/Doacao';
import { DoacaoService } from '../services/doacao.service';
import { Usuario } from '../model-view/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, startWith, map } from 'rxjs/operators';
import { MatSnackBar, MatTableDataSource } from '@angular/material';
import { ParceiroDeNegocio } from '../model-view/parceiro-de-negocio';
import { Observable } from 'rxjs';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { AppDateAdapter, APP_DATE_FORMATS } from '../shared/format-datepicker';

@Component({
  selector: 'app-doacao',
  templateUrl: './doacao.component.html',
  styleUrls: ['../shared/shared-form.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: AppDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS }
  ]
})
export class DoacaoComponent implements OnInit {
  public progressBarMode;
  isAddMode = false;
  saving = true;
  msg: string;

  displayedColumns = ['Item', 'Valor', 'Quantidade', 'Editar', 'Remover'];
  doacao = new DoacaoMessage();
  doacaoItem = new DoacaoItem();
  dataSource: MatTableDataSource<DoacaoItem>;
  convenios: Convenio[];
  itens: Item[];
  parceirosDeNegocio: ParceiroDeNegocio[];
  parceiroDeNegociosFiltradas: Observable<any[]>;
  parceiroDeNegocioControl = new FormControl('', [Validators.required]);
  nomeFormControl = new FormControl('', [Validators.required]);

  matcher = new MyErrorStateMatcher();
  constructor(private utilsService: UtilsService, private doacaoServer: DoacaoService, private parceiroDeNegocioServer: ParceiroDeNegocioService, private convenioServer: ConvenioService, private itemServer: ItemService, private activatedRoute: ActivatedRoute, public snackBar: MatSnackBar, private router: Router) { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource();
    this.doacao = new DoacaoMessage();
    this.doacao.doacao = new Doacao();
    this.doacao.doacao.usuarioCriacao = new Usuario();
    this.doacao.doacao.convenio = new Convenio();
    this.doacao.doacao.parceiroDeNegocio = new ParceiroDeNegocio();
    this.doacaoItem = new DoacaoItem();
    this.doacaoItem.item = new Item();
    this.doacaoItem.doacao = new Doacao();
    this.activatedRoute.params.forEach(params => {
      if (params.id == undefined || params.id == "") {
        this.isAddMode = true;
        this.doacao.doacao.status = true;
      } else {
        this.doacao.doacao.usuarioModificacao = new Usuario();

        this.doacaoServer.getDoacao(params.id).subscribe(d => {
          this.doacao = d;
          this.doacao.itens.forEach(item => {
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


  AdicionaItem(): DoacaoItem {

    let doacao = new DoacaoItem();
    doacao = JSON.parse(JSON.stringify(this.doacaoItem))
    doacao.linha = this.utilsService.GerarHash();

    return doacao;
  }

  removeLinha(row) {
    this.dataSource.data.splice(this.dataSource.data.indexOf(row), 1);
    this.dataSource.filter = "";
  }

  addLinhaEdit(item: DoacaoItem) {
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

    this.getStatusBar(true);

    this.setUsuario();

    this.doacao.itens = this.dataSource.data;

    this.doacaoServer.add(this.doacao).pipe(finalize(() => { this.getStatusBar(false); })).subscribe(dados => {

      if (dados != undefined) {
        if (this.isAddMode) {
          this.msg = "Doação cadastrado com sucesso!";
        }
        else {
          this.msg = "Doação atualizado com sucesso!";
        }
        this.router.navigate(['auth/doacao/list']);
      } else {
        if (this.isAddMode) {
          this.msg = "Erro ao cadastrar doação";
        }
        else {
          this.msg = "Erro ao atualizar doação";
        }
      }
      this.controleDeTelaRequest(this.msg);
    }
    );

  }

  private setUsuario() {
    if (this.isAddMode) {
      this.doacao.doacao.usuarioCriacao.id = 1;
    }
    else {
      this.doacao.doacao.usuarioModificacao.id = 1;
    }
  }

  getStatusBar(progressBar: boolean) {
    if (progressBar) {
      this.progressBarMode = "indeterminate";
      this.saving = false;
    } else {
      this.progressBarMode = "";
      this.saving = true;
    }
  }

  controleDeTelaRequest(msgSnack: string) {
    this.snackBar.open(msgSnack, "OK", { duration: 2000 });
  }

  onChangeParceiro(event) {
    if (this.parceirosDeNegocio != undefined) {
      if (event != undefined && event != "") {
        try {
          this.doacao.doacao.parceiroDeNegocio.id = this.parceirosDeNegocio.find(x => x.nome === event).id;
        } catch (error) { }

      }
    }
  }

  onChangeConvenio(event) {
    if (this.convenios != undefined) {
      if (event != undefined && event != "") {
        try {
          this.doacao.doacao.convenio.id = this.convenios.find(x => x.nome === event).id;
        } catch (error) { }

      }
    }
  }

  onChangeItem(event) {
    if (this.itens != undefined) {
      if (event != undefined && event != "") {
        try {
          this.doacaoItem.item.nome = this.itens.find(x => x.id === event).nome;
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
