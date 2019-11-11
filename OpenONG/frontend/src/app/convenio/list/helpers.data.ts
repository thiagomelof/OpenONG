import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { Convenio } from '../../model-view/Convenio';
import { ConvenioService } from '../../services/convenio.service';

export class ConvenioDao {
  convenios: Array<Convenio>;
  convenio: Convenio;

  dataChange: BehaviorSubject<Convenio[]> = new BehaviorSubject<Convenio[]>([]);

  get data(): Convenio[] { return this.dataChange.value; }

  constructor(private convenioService: ConvenioService) {
    convenioService.listar().subscribe(
      listConvenios => {
        this.convenios = listConvenios;

        this.convenios.forEach(convenio => {
          this.addConvenio(convenio);
        });
      }
    );
  }

  addConvenio(convenio: Convenio) {
    const copiedData = this.data.slice();
    copiedData.push(convenio);
    this.dataChange.next(copiedData);
  }
}

export class ConvenioDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  listaFiltrada: Convenio[] = [];
  listaRenderizada: Convenio[] = [];

  constructor(private _convenioDao: ConvenioDao, private _paginator: MatPaginator, private _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Convenio[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._convenioDao.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.listaFiltrada = this._convenioDao.data.slice().filter((item: Convenio) => {
        let searchStr = (item.nome + item.observacoes + item.id + item.strStatus).toLowerCase();
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
  sortData(data: Convenio[]): Convenio[] {
    if (!this._sort.active || this._sort.direction == '') { return data; }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'ID': [propertyA, propertyB] = [a.id, b.id]; break;
        case 'Nome': [propertyA, propertyB] = [a.nome, b.nome]; break;
        case 'Observações': [propertyA, propertyB] = [a.observacoes, b.observacoes]; break;
        case 'Status': [propertyA, propertyB] = [a.strStatus, a.strStatus]; break;
      }

      let valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      let valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction == 'asc' ? 1 : -1);
    });
  }
}

