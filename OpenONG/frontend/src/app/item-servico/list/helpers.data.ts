import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { Item } from '../../model-view/Item';
import { ItemService } from '../../services/item.service';

export class ItemDao {
  itens: Array<Item>;
  item: Item;

  dataChange: BehaviorSubject<Item[]> = new BehaviorSubject<Item[]>([]);

  get data(): Item[] { return this.dataChange.value; }

  constructor(private itemService: ItemService) {
    itemService.listar().subscribe(
      listItems => {
        this.itens = listItems;

        this.itens.forEach(item => {
          this.addItem(item);
        });
      }
    );
  }

  addItem(item: Item) {
    const copiedData = this.data.slice();
    copiedData.push(item);
    this.dataChange.next(copiedData);
  }
}

export class ItemDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  listaFiltrada: Item[] = [];
  listaRenderizada: Item[] = [];

  constructor(private _itemDao: ItemDao, private _paginator: MatPaginator, private _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Item[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._itemDao.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.listaFiltrada = this._itemDao.data.slice().filter((item: Item) => {
        let searchStr = (item.nome + item.categoria.nome + item.id + item.strStatus).toLowerCase();
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
  sortData(data: Item[]): Item[] {
    if (!this._sort.active || this._sort.direction == '') { return data; }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'ID': [propertyA, propertyB] = [a.id, b.id]; break;
        case 'Nome': [propertyA, propertyB] = [a.nome, b.nome]; break;
        case 'Categoria': [propertyA, propertyB] = [a.categoria.nome, b.categoria.nome]; break;
        case 'Status': [propertyA, propertyB] = [a.strStatus, a.strStatus]; break;
      }

      let valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      let valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction == 'asc' ? 1 : -1);
    });
  }
}

