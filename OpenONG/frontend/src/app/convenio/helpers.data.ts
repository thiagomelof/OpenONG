import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { ConvenioCategoria } from '../model-view/ConvenioCategoria';
import { ConvenioCategoriaService } from './../services/convenio-categoria';

export class ConvenioCategoriaDao {
  conveniocategorias: Array<ConvenioCategoria>;
  conveniocategoria: ConvenioCategoria;

  dataChange: BehaviorSubject<ConvenioCategoria[]> = new BehaviorSubject<ConvenioCategoria[]>([]);

  get data(): ConvenioCategoria[] { return this.dataChange.value; }

  constructor(private conveniocategoriaService: ConvenioCategoriaService) {
    conveniocategoriaService.listar().subscribe(
      listConvenioCategorias => {
        this.conveniocategorias = listConvenioCategorias;

        this.conveniocategorias.forEach(conveniocategoria => {
          this.addConvenioCategoria(conveniocategoria);
        });
      }
    );
  }

  addConvenioCategoria(conveniocategoria: ConvenioCategoria) {
    const copiedData = this.data.slice();
    copiedData.push(conveniocategoria);
    this.dataChange.next(copiedData);
  }
}

export class ConvenioCategoriaDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  listaFiltrada: ConvenioCategoria[] = [];
  listaRenderizada: ConvenioCategoria[] = [];

  constructor(private _conveniocategoriaDao: ConvenioCategoriaDao, private _paginator: MatPaginator, private _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<ConvenioCategoria[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._conveniocategoriaDao.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.listaFiltrada = this._conveniocategoriaDao.data.slice().filter((item: ConvenioCategoria) => {
        let searchStr = (item.convenio.id + item.convenio.nome).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) != -1;
      });
      
      return this.listaRenderizada;
    }));
  }

  disconnect() { }
}

