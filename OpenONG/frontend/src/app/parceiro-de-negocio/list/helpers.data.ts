import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { ParceiroDeNegocio } from '../../model-view/parceiro-de-negocio';
import { ParceiroDeNegocioService } from '../../services/parceiro-de-negocio.service';

export class ParceiroDeNegocioDao {
  itens: Array<ParceiroDeNegocio>;
  parceirodenegocio: ParceiroDeNegocio;

  dataChange: BehaviorSubject<ParceiroDeNegocio[]> = new BehaviorSubject<ParceiroDeNegocio[]>([]);

  get data(): ParceiroDeNegocio[] { return this.dataChange.value; }

  constructor(private parceirodenegocioService: ParceiroDeNegocioService) {
    parceirodenegocioService.listar().subscribe(
      listParceiroDeNegocios => {
        this.itens = listParceiroDeNegocios;

        this.itens.forEach(parceirodenegocio => {
          this.addParceiroDeNegocio(parceirodenegocio);
        });
      }
    );
  }

  addParceiroDeNegocio(parceirodenegocio: ParceiroDeNegocio) {
    const copiedData = this.data.slice();
    copiedData.push(parceirodenegocio);
    this.dataChange.next(copiedData);
  }
}

export class ParceiroDeNegocioDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  listaFiltrada: ParceiroDeNegocio[] = [];
  listaRenderizada: ParceiroDeNegocio[] = [];

  constructor(private _parceirodenegocioDao: ParceiroDeNegocioDao, private _paginator: MatPaginator, private _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<ParceiroDeNegocio[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._parceirodenegocioDao.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.listaFiltrada = this._parceirodenegocioDao.data.slice().filter((parceirodenegocio: ParceiroDeNegocio) => {
        let searchStr = (parceirodenegocio.nome + parceirodenegocio.strTipoParceiro + parceirodenegocio.id + parceirodenegocio.strStatus).toLowerCase();
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
  sortData(data: ParceiroDeNegocio[]): ParceiroDeNegocio[] {
    if (!this._sort.active || this._sort.direction == '') { return data; }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'ID': [propertyA, propertyB] = [a.id, b.id]; break;
        case 'Nome': [propertyA, propertyB] = [a.nome, b.nome]; break;
        case 'Tipo': [propertyA, propertyB] = [a.strTipoParceiro, b.strTipoParceiro]; break;
        case 'Status': [propertyA, propertyB] = [a.strStatus, b.strStatus]; break;
      }

      let valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      let valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction == 'asc' ? 1 : -1);
    });
  }
}

