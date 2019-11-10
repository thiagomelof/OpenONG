import { fromEvent as observableFromEvent, Observable } from 'rxjs';

import { distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ItemDao, ItemDataSource } from './helpers.data';
import { MatPaginator, MatSort } from '@angular/material';
import { ItemService } from '../../services/item.service';


@Component({
	selector: 'app-list',
	templateUrl: './list.component.html',
	styleUrls: ['./../../shared/shared-table.component.scss']
})


export class ListComponent implements OnInit {
	public progressBarMode;
	displayedColumns = ['ID', 'Nome', 'Categoria', 'Status', 'Editar'];
	itemDao = new ItemDao(this.itemService);
	dataSource: ItemDataSource | null;

	constructor(private itemService: ItemService) { }
	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild('filter') filter: ElementRef;

	ngOnInit() {
		this.dataSource = new ItemDataSource(this.itemDao, this.paginator, this.sort);
		observableFromEvent(this.filter.nativeElement, 'keyup').pipe(
			debounceTime(150), distinctUntilChanged()).subscribe(() => {
				if (!this.dataSource) { return; }
				this.dataSource.filter = this.filter.nativeElement.value;
			});
	}

	componentesRequest(progressBar: boolean) {
		if (progressBar) {
			this.progressBarMode = "indeterminate";
		} else {
			this.progressBarMode = "";
		}
	}

	paginatorLength() {
		return this.dataSource.listaFiltrada.length;
	}
}