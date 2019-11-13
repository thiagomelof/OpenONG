import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { Despesa } from '../../model-view/Despesa';
import { DespesaService } from '../../services/despesa.service';

export class DespesaDao {
  despesas: Array<Despesa>;
  despesa: Despesa;

  dataChange: BehaviorSubject<Despesa[]> = new BehaviorSubject<Despesa[]>([]);

  get data(): Despesa[] { return this.dataChange.value; }

  constructor(private despesaService: DespesaService) {
    despesaService.listar().subscribe(
      listDespesas => {
        this.despesas = listDespesas;

        this.despesas.forEach(despesa => {
          this.addDespesa(despesa);
        });
      }
    );
  }

  addDespesa(despesa: Despesa) {
    const copiedData = this.data.slice();
    copiedData.push(despesa);
    this.dataChange.next(copiedData);
  }
}

export class DespesaDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  listaFiltrada: Despesa[] = [];
  listaRenderizada: Despesa[] = [];

  constructor(private _despesaDao: DespesaDao, private _paginator: MatPaginator, private _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Despesa[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._despesaDao.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.listaFiltrada = this._despesaDao.data.slice().filter((item: Despesa) => {
        let searchStr = (item.parceiroDeNegocio.nome + item.observacoes + item.id + item.strStatus+ item.dataCriacao).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) != -1;
      });

      // Sort filtered data
      const sortedData = this.sortData(this.listaFiltrada.slice());

      // Grab the page's slice of the filtered sorted data.
      const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
      this.listaRenderizada = sortedData.splice(startIndex, this._paginator.pageSize);
      return this.listaRenderizada;
    }));
  }

  disconnect() { }

  /** Returns a sorted copy of the database data. */
  sortData(data: Despesa[]): Despesa[] {
    if (!this._sort.active || this._sort.direction == '') { return data; }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'ID': [propertyA, propertyB] = [a.id, b.id]; break;
        case 'Parceiro': [propertyA, propertyB] = [a.parceiroDeNegocio.nome, b.parceiroDeNegocio.nome]; break;
        case 'Observações': [propertyA, propertyB] = [a.observacoes, b.observacoes]; break;
        case 'Status': [propertyA, propertyB] = [a.strStatus, a.strStatus]; break;
        case 'Data': [propertyA, propertyB] = [a.dataCriacao.toString(), a.dataCriacao.toString()]; break;
      }

      let valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      let valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction == 'asc' ? 1 : -1);
    });
  }
}

