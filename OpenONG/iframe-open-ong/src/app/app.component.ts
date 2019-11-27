import { DespesaService } from './services/despesa.service';
import { DespesasPorCategoriaEMesMessage } from './model-view/dto/despesas-por-categoria-message';
import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-iframe-open-ong',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  displayedColumns = ['Categoria', 'Valor'];
  dataSource: MatTableDataSource<DespesasPorCategoriaEMesMessage>;
  despesaPorCategoria: DespesasPorCategoriaEMesMessage[];
  categorias: string[];
  valores: number[];
  ano: number;
  mes: number;

  constructor(private despesaService: DespesaService) { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource();
    this.dataSource.data = [];
    this.ano = new Date().getFullYear();
    this.mes = new Date().getMonth() + 1;
    this.despesaPorCategoria = [];
    this.categorias = [];
    this.valores = [];
    this.getDespesas();

  }

  onChangeAno(event) {
    this.ano = event;
    this.getDespesas();
  }

  onChangeMes(event) {
    this.mes = event;
    this.getDespesas();
  }

  getDespesas() {

    this.dataSource.data = [];
    this.dataSource.filter = "";
    this.despesaService.listarDespesasAtivasPorCategoria(this.mes, this.ano).subscribe(d => {
      this.despesaPorCategoria = d;
      this.despesaPorCategoria.forEach(cat => {
        this.addLinha(cat);
      });

      let despCat = new DespesasPorCategoriaEMesMessage();
      despCat.categoria = "Total";
      despCat.valor = this.despesaPorCategoria.reduce((sum, current) => sum + current.valor, 0);
      this.addLinha(despCat);
    });
  }

  addLinha(item: DespesasPorCategoriaEMesMessage) {
    this.dataSource.data.push(item);
    this.dataSource.filter = "";
  }

  meses = [
    { id: 1, nome: 'Janeiro' },
    { id: 2, nome: 'Fevereiro' },
    { id: 3, nome: 'Março' },
    { id: 4, nome: 'Abril' },
    { id: 5, nome: 'Maio' },
    { id: 6, nome: 'Junho' },
    { id: 7, nome: 'Julho' },
    { id: 8, nome: 'Agosto' },
    { id: 9, nome: 'Setembro' },
    { id: 10, nome: 'Outubro' },
    { id: 11, nome: 'Novembro' },
    { id: 12, nome: 'Dezembro' },
  ];

  anos = [
    { id: new Date().getFullYear() - 3 },
    { id: new Date().getFullYear() - 2 },
    { id: new Date().getFullYear() - 1 },
    { id: new Date().getFullYear() }
  ];
}
