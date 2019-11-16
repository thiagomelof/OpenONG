import { ConvenioCategoria } from './../model-view/convenio-categoria';

import {merge as observableMerge,  BehaviorSubject ,  Observable } from 'rxjs';

import {map} from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';

export class ConvenioCategoriaDatabase {
  dataChange: BehaviorSubject<ConvenioCategoria[]> = new BehaviorSubject<ConvenioCategoria[]>([]);
  get data(): ConvenioCategoria[] { return this.dataChange.value; }

  constructor() {
  }

  addLines(categoria: ConvenioCategoria) {
    const copiedData = this.data.slice();
    copiedData.push(categoria);
    this.dataChange.next(copiedData);
  }
}

export class ConvenioCategoriaDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  constructor(private convenioCategoria: ConvenioCategoriaDatabase) {
    super();
  }
  connect(): Observable<ConvenioCategoria[]> {
    const displayDataChanges = [
      this.convenioCategoria.dataChange,
      this._filterChange,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      return this.convenioCategoria.data.slice().filter((item: ConvenioCategoria) => {
        let searchStr = (item.percentual + item.linha).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) != -1;
      });
    }));
  }

  disconnect() {}
}
