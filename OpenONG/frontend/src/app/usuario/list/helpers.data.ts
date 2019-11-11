import { merge as observableMerge, BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort } from '@angular/material';

import { Usuario } from '../../model-view/usuario';
import { UsuarioService } from '../../services/usuario.service';

export class UsuarioDao {
  itens: Array<Usuario>;
  usuario: Usuario;

  dataChange: BehaviorSubject<Usuario[]> = new BehaviorSubject<Usuario[]>([]);

  get data(): Usuario[] { return this.dataChange.value; }

  constructor(private usuarioService: UsuarioService) {
    usuarioService.listar().subscribe(
      listUsuarios => {
        this.itens = listUsuarios;

        this.itens.forEach(usuario => {
          this.addUsuario(usuario);
        });
      }
    );
  }

  addUsuario(usuario: Usuario) {
    const copiedData = this.data.slice();
    copiedData.push(usuario);
    this.dataChange.next(copiedData);
  }
}

export class UsuarioDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  listaFiltrada: Usuario[] = [];
  listaRenderizada: Usuario[] = [];

  constructor(private _usuarioDao: UsuarioDao, private _paginator: MatPaginator, private _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Usuario[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._usuarioDao.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.listaFiltrada = this._usuarioDao.data.slice().filter((usuario: Usuario) => {
        let searchStr = (usuario.nome + usuario.id + usuario.strStatus).toLowerCase();
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
  sortData(data: Usuario[]): Usuario[] {
    if (!this._sort.active || this._sort.direction == '') { return data; }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'ID': [propertyA, propertyB] = [a.id, b.id]; break;
        case 'Nome': [propertyA, propertyB] = [a.nome, b.nome]; break;
        case 'Status': [propertyA, propertyB] = [a.strStatus, b.strStatus]; break;
      }

      let valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      let valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction == 'asc' ? 1 : -1);
    });
  }
}

