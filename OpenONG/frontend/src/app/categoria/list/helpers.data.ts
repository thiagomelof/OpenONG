import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';

import { map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { Categoria } from '../../model-view/Categoria';
import { CategoriaService } from '../../categoria.service';

/** An example database that the data source uses to retrieve data for the table. */
export class ExampleDatabase {
  categorias: Array<Categoria>;
  categoria: Categoria;
  /** Stream that emits whenever the data has been modified. */
  dataChange: BehaviorSubject<Categoria[]> = new BehaviorSubject<Categoria[]>([]);
  get data(): Categoria[] { return this.dataChange.value; }

  constructor(private categoriaService: CategoriaService) {
    // Fill up the database with 100 users.

    categoriaService.listar().subscribe(
      listCategorias => {
        this.categorias = listCategorias;

        this.categorias.forEach(categoria => {
          this.addUser(categoria);
        });

      }



    );
  }

  /** Adds a new user to the database. */
  addUser(categoria: Categoria) {
    const copiedData = this.data.slice();
    copiedData.push(categoria);
    this.dataChange.next(copiedData);
  }
}

/**
 * Data source to provide what data should be rendered in the table. Note that the data source
 * can retrieve its data in any way. In this case, the data source is provided a reference
 * to a common data base, ExampleDatabase. It is not the data source's responsibility to manage
 * the underlying data. Instead, it only needs to take the data and send the table exactly what
 * should be rendered.
 */
export class ExampleDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  filteredData: Categoria[] = [];
  renderedData: Categoria[] = [];

  constructor(private _exampleDatabase: ExampleDatabase,
    private _paginator: MatPaginator,
    private _sort: MatSort) {
    super();

    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Categoria[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.filteredData = this._exampleDatabase.data.slice().filter((item: Categoria) => {
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

