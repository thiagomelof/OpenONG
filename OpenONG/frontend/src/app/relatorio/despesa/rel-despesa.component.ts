import { DespesaParameters } from './../../model-view/dto/despesa-parameters';
import { DespesaItem } from './../../model-view/despesa-item';
import { RelatoriosService } from './../../services/relatorios.service';
import { Component, OnInit } from '@angular/core';
import { Convenio } from '../../model-view/convenio';
import { ParceiroDeNegocio } from '../../model-view/parceiro-de-negocio';

@Component({
  selector: 'app-rel-despesa',
  templateUrl: './rel-despesa.component.html',
  styleUrls: ['./../../shared/shared-table.component.scss']
})
export class RelDespesaComponent implements OnInit {
  itens: DespesaItem[];

  constructor(private relatorioServer: RelatoriosService) { }

  ngOnInit() {


    let params = new DespesaParameters();
    params.convenio = new Convenio();
    params.parceiro = new ParceiroDeNegocio();
    params.convenio.id = 0;
    params.parceiro.id = 0;
    params.dataInicio = new Date("2019/01/01");
    params.dataFim = new Date("2019/12/20");

    this.relatorioServer.getRelatorioDespesa(params).subscribe(dados => {
      this.itens = dados;
      console.log(dados);
    })

  }

}
