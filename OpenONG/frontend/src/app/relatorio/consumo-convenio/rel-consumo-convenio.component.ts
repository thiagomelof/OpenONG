import { FormControl, Validators } from '@angular/forms';
import { ConsumoConvenioMessage } from './../../model-view/dto/consumo-convenio-message';
import { Convenio } from './../../model-view/convenio';
import { ConvenioService } from './../../services/convenio.service';
import { RelatoriosService } from './../../services/relatorios.service';
import { MatTableDataSource, MatSnackBar } from '@angular/material';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rel-consumo-convenio',
  templateUrl: './rel-consumo-convenio.component.html',
  styleUrls: ['./../../shared/shared-table.component.scss']
})
export class RelConsumoConvenioComponent implements OnInit {
  displayedColumns = ['categoria',  'percPlanejado','valPlanejado', 'valDespesas', 'percUtilizado'];
  dataSource: MatTableDataSource<ConsumoConvenioMessage>;
  consumo: ConsumoConvenioMessage[];
  convenios: Convenio[];
  convenio: Convenio;
  totalDespesa: number;
  totalDoacao: number;
  totalConsumido: number;
  totalbool: boolean;
  convenioControl = new FormControl('', [Validators.required]);
  constructor(private relatorioServer: RelatoriosService, private convenioServer: ConvenioService, public snackBar: MatSnackBar) { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource();
    this.consumo = [];
    this.totalbool = false;
    this.convenio = new Convenio();
    this.totalDoacao = 0;
    this.totalDespesa = 0;


    this.convenioServer.listar().subscribe(conv => {
      let convenioVazio = new Convenio();
      convenioVazio.id = 0;
      convenioVazio.nome = "Selecione...";
      this.convenios = [];
      this.convenios = conv;
      this.convenios.splice(0, 0, convenioVazio);
    })
  }

  addLinha(item: ConsumoConvenioMessage) {
    this.dataSource.data.push(item);
    this.dataSource.filter = "";
  }


  onChangeConvenio(event) {
    if (this.convenios != undefined) {
      this.convenio.nome = "";
      if (event != undefined && event != "") {
        try {
          this.convenio.nome = this.convenios.find(x => x.id === event).nome;
        } catch (error) { }
      }
    }
  }

  filtrar() {
    this.dataSource.data = [];
    this.dataSource.filter = "";
    this.totalDespesa = 0;
    this.totalDoacao = 0;
    this.totalConsumido = 0;
    this.totalbool = false;
    if (this.convenio.id > 0 && this.convenio.id != undefined) {
      this.relatorioServer.getRelatorioConsumoConvenio(this.convenio.id).subscribe(dados => {
        this.consumo = dados;

        this.consumo.forEach(item => {
          if (item.percentualUtilizado >= item.percentualAplicado)
            item.color = "red";

          this.totalDespesa = this.totalDespesa + item.despesa;
          this.totalDoacao = item.doacao;
          this.totalConsumido = this.totalConsumido + item.percentualUtilizado;

          item.valorPlanejado = (this.totalDoacao * item.percentualAplicado) / 100;

          this.addLinha(item);
        });

        this.totalbool = true;
      })
    } else {
      this.getStatusBar("Necessário informar um convênio.");
    }
  }
  getStatusBar(msgSnack: string) {
    this.snackBar.open(msgSnack, "FECHAR", { duration: 3500 });
  }

}
