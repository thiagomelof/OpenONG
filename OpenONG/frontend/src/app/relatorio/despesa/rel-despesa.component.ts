import { AppDateAdapter, APP_DATE_FORMATS } from './../../shared/format-datepicker';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { ConvenioService } from './../../services/convenio.service';
import { startWith, map } from 'rxjs/operators';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { ParceiroDeNegocioService } from './../../services/parceiro-de-negocio.service';
import { MatTableDataSource } from '@angular/material';
import { DespesaParameters } from './../../model-view/dto/despesa-parameters';
import { DespesaItem } from './../../model-view/despesa-item';
import { RelatoriosService } from './../../services/relatorios.service';
import { Component, OnInit } from '@angular/core';
import { Convenio } from '../../model-view/convenio';
import { ParceiroDeNegocio } from '../../model-view/parceiro-de-negocio';

@Component({
  selector: 'app-rel-despesa',
  templateUrl: './rel-despesa.component.html',
  styleUrls: ['./../../shared/shared-table.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: AppDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS }
  ]
})
export class RelDespesaComponent implements OnInit {
  displayedColumns = ['Despesa', 'Fornecedor', 'Lançamento', 'Convênio', 'Item', 'Quantidade', 'Valor', 'Total', 'Observações'];
  dataSource: MatTableDataSource<DespesaItem>;
  fornecedor: boolean;
  convenio: boolean;
  itens: DespesaItem[];
  convenios: Convenio[];
  params: DespesaParameters;
  parceirosDeNegocio: ParceiroDeNegocio[];
  parceiroDeNegociosFiltradas: Observable<any[]>;
  parceiroDeNegocioControl = new FormControl('');

  constructor(private relatorioServer: RelatoriosService, private parceiroDeNegocioServer: ParceiroDeNegocioService, private convenioServer: ConvenioService) { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource();
    this.params = new DespesaParameters();
    this.params.convenio = new Convenio();
    this.params.parceiro = new ParceiroDeNegocio();
    this.params.convenio.id = 0;
    this.params.parceiro.id = 0;
    this.fornecedor = false;
    this.convenio = false;

    this.parceiroDeNegocioServer.listarAtivas().subscribe(pn => {
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

  addLinha(item: DespesaItem) {
    this.dataSource.data.push(item);
    this.dataSource.filter = "";
  }

  onChangeParceiro(event) {

    if (this.parceirosDeNegocio != undefined) {
      this.params.parceiro.id = -1;
      this.fornecedor = false;
      if (event != undefined && event != "") {
        try {
          this.params.parceiro.id = this.parceirosDeNegocio.find(x => x.nome === event).id;
        } catch (error) { }
      }
      if (this.params.parceiro.id > 0) {
        this.fornecedor = true;
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

    this.relatorioServer.getRelatorioDespesa(this.params).subscribe(dados => {
      this.itens = dados;

      this.itens.forEach(item => {
        this.addLinha(item);
      });
    })
  }


}
