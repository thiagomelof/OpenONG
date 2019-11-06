import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { Categoria } from '../../model-view/Categoria';
import { CategoriaService } from '../../categoria.service';

export class CategoriaDao {
  categorias: Array<Categoria>;
  categoria: Categoria;

  dataChange: BehaviorSubject<Categoria[]> = new BehaviorSubject<Categoria[]>([]);

  get data(): Categoria[] { return this.dataChange.value; }

  constructor(private categoriaService: CategoriaService) {
    categoriaService.listar().subscribe(
      listCategorias => {
        this.categorias = listCategorias;

        this.categorias.forEach(categoria => {
          this.addCategoria(categoria);
        });
      }
    );
  }

  addCategoria(categoria: Categoria) {
    const copiedData = this.data.slice();
    copiedData.push(categoria);
    this.dataChange.next(copiedData);
  }
}

export class CategoriaDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  filteredData: Categoria[] = [];
  renderedData: Categoria[] = [];

  constructor(private _categoriaDao: CategoriaDao, private _paginator: MatPaginator, private _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Categoria[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._categoriaDao.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.filteredData = this._categoriaDao.data.slice().filter((item: Categoria) => {
        let searchStr = (item.nome + item.observacoes + item.id).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) != -1;
      });

      // Sort filtered data
      const sortedData = this.sortData(this.filteredData.slice());

      // Grab the page's slice of the filtered sorted data.
      const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
      this.renderedData = sortedData.splice(startIndex, this._paginator.pageSize);
      return this.renderedData;
    }));
  }

  disconnect() { }

  /** Returns a sorted copy of the database data. */
  sortData(data: Categoria[]): Categoria[] {
    if (!this._sort.active || this._sort.direction == '') { return data; }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'ID': [propertyA, propertyB] = [a.id, b.id]; break;
        case 'Nome': [propertyA, propertyB] = [a.nome, b.nome]; break;
        case 'Observações': [propertyA, propertyB] = [a.observacoes, b.observacoes]; break;
      }

      let valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      let valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction == 'asc' ? 1 : -1);
    });
  }
}

