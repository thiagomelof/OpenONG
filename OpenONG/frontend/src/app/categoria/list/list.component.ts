import { fromEvent as observableFromEvent, Observable } from 'rxjs';

import { distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { CategoriaDao, CategoriaDataSource } from './helpers.data';
import { MatPaginator, MatSort } from '@angular/material';
import { CategoriaService } from '../../services/categoria.service';


@Component({
	selector: 'app-list',
	templateUrl: './list.component.html',
	styleUrls: ['./../../shared/shared-table.component.scss']
})


export class ListComponent implements OnInit {
	public progressBarMode;
	displayedColumns = ['ID', 'Nome', 'Observações', 'Status', 'Editar'];
	categoriaDao = new CategoriaDao(this.categoriaService);
	dataSource: CategoriaDataSource | null;

	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild('filter') filter: ElementRef;

	constructor(private categoriaService: CategoriaService) { }

	ngOnInit() {
		this.dataSource = new CategoriaDataSource(this.categoriaDao, this.paginator, this.sort);
		observableFromEvent(this.filter.nativeElement, 'keyup').pipe(
			debounceTime(150), distinctUntilChanged()).subscribe(() => {
				if (!this.dataSource) { return; }
				this.dataSource.filter = this.filter.nativeElement.value;
			});
	}

	paginatorLength() {
		return this.dataSource.listaFiltrada.length;
	}
}