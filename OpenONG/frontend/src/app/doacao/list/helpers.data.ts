import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { Doacao } from '../../model-view/Doacao';
import { DoacaoService } from '../../services/doacao.service';

export class DoacaoDao {
  doacaos: Array<Doacao>;
  doacao: Doacao;

  dataChange: BehaviorSubject<Doacao[]> = new BehaviorSubject<Doacao[]>([]);

  get data(): Doacao[] { return this.dataChange.value; }

  constructor(private doacaoService: DoacaoService) {
    doacaoService.listar().subscribe(
      listDoacaos => {
        this.doacaos = listDoacaos;

        this.doacaos.forEach(doacao => {
          this.addDoacao(doacao);
        });
      }
    );
  }

  addDoacao(doacao: Doacao) {
    const copiedData = this.data.slice();
    copiedData.push(doacao);
    this.dataChange.next(copiedData);
  }
}

export class DoacaoDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  listaFiltrada: Doacao[] = [];
  listaRenderizada: Doacao[] = [];

  constructor(private _doacaoDao: DoacaoDao, private _paginator: MatPaginator, private _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Doacao[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._doacaoDao.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.listaFiltrada = this._doacaoDao.data.slice().filter((item: Doacao) => {
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
  sortData(data: Doacao[]): Doacao[] {
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

