import { Status } from './../../model-view/const/status';
import { AppDateAdapter, APP_DATE_FORMATS } from './../../shared/format-datepicker';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { ConvenioService } from './../../services/convenio.service';
import { startWith, map } from 'rxjs/operators';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { ParceiroDeNegocioService } from './../../services/parceiro-de-negocio.service';
import { MatTableDataSource } from '@angular/material';
import { DoacaoParameters } from './../../model-view/dto/doacao-parameters';
import { DoacaoItem } from './../../model-view/doacao-item';
import { RelatoriosService } from './../../services/relatorios.service';
import { Component, OnInit } from '@angular/core';
import { Convenio } from '../../model-view/convenio';
import { ParceiroDeNegocio } from '../../model-view/parceiro-de-negocio';
import { TipoParceiro } from '../../model-view/const/tipoparceiro';

@Component({
  selector: 'app-rel-doacao',
  templateUrl: './rel-doacao.component.html',
  styleUrls: ['./../../shared/shared-table.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: AppDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS }
  ]
})
export class RelDoacaoComponent implements OnInit {
  displayedColumns = ['Doação', 'Doador', 'Lançamento', 'Convênio', 'Item', 'Total', 'Observações'];
  dataSource: MatTableDataSource<DoacaoItem>;
  doador: boolean;
  convenio: boolean;
  total: number;
  totalbool: boolean;
  itens: DoacaoItem[];
  convenios: Convenio[];
  params: DoacaoParameters;
  parceirosDeNegocio: ParceiroDeNegocio[];
  parceiroDeNegociosFiltradas: Observable<any[]>;
  parceiroDeNegocioControl = new FormControl('');

  constructor(private relatorioServer: RelatoriosService, private parceiroDeNegocioServer: ParceiroDeNegocioService, private convenioServer: ConvenioService) { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource();
    this.params = new DoacaoParameters();
    this.params.convenio = new Convenio();
    this.params.parceiro = new ParceiroDeNegocio();
    this.params.convenio.id = 0;
    this.params.parceiro.id = 0;
    this.doador = false;
    this.convenio = false;
    this.totalbool = false;
    this.total = 0;

    this.parceiroDeNegocioServer.listarParceirosPorTipo(TipoParceiro.Doador, Status.Todos).subscribe(pn => {
      this.parceirosDeNegocio = pn;

      if (this.parceirosDeNegocio != undefined) {
        this.parceiroDeNegociosFiltradas = this.parceiroDeNegocioControl.valueChanges.pipe(startWith(null),
          map(parceiroDeNegocio => parceiroDeNegocio ? this.filtrarParceiroDeNegocios(parceiroDeNegocio) : this.parceirosDeNegocio.slice()));
      }
    })

    this.convenioServer.listarAtivos().subscribe(conv => {
      let convenioVazio = new Convenio();
      convenioVazio.id = 0;
      convenioVazio.nome = "Selecione...";
      this.convenios = [];
      this.convenios = conv;
      this.convenios.splice(0, 0, convenioVazio);
    })
  }

  addLinha(item: DoacaoItem) {
    this.dataSource.data.push(item);
    this.dataSource.filter = "";
  }

  onChangeParceiro(event) {

    if (this.parceirosDeNegocio != undefined) {
      this.params.parceiro.id = -1;
      this.doador = false;
      if (event != undefined && event != "") {
        try {
          this.params.parceiro.id = this.parceirosDeNegocio.find(x => x.nome === event).id;
        } catch (error) { }
      }
      if (this.params.parceiro.id > 0) {
        this.doador = true;
      }
    }
  }

  onChangeConvenio(event) {
    if (this.convenios != undefined) {
      this.params.convenio.nome = "";
      this.convenio = false;
      if (event != undefined && event != "") {
        try {
          this.params.convenio.nome = this.convenios.find(x => x.id === event).nome;
        } catch (error) { }
      }

      if (this.params.convenio.nome != "") {
        this.convenio = true;
      }
    }
  }

  filtrarParceiroDeNegocios(name: string) {
    return this.parceirosDeNegocio.filter(pn =>
      pn.nome.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  filtrar() {
    this.dataSource.data = [];
    this.dataSource.filter = "";
    this.total = 0;
    this.totalbool = false;
    this.relatorioServer.getRelatorioDoacoes(this.params).subscribe(dados => {
      this.itens = dados;

      this.itens.forEach(item => {
        this.addLinha(item);
        this.total = (this.total + ((item.valorUnitario) * item.quantidade));
      });

      this.totalbool = true;
    })
  }


}
