import { fromEvent as observableFromEvent, Observable } from 'rxjs';

import { distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ConvenioDao, ConvenioDataSource } from './helpers.data';
import { MatPaginator, MatSort } from '@angular/material';
import { ConvenioService } from '../../services/convenio.service';
import { Convenio } from '../../model-view/convenio';


@Component({
	selector: 'app-list',
	templateUrl: './list.component.html',
	styleUrls: ['./../../shared/shared-table.component.scss']
})


export class ListComponent implements OnInit {
	public progressBarMode;
	displayedColumns = ['ID', 'Nome', 'Observações', 'Status', 'Editar'];
	convenioDao = new ConvenioDao(this.convenioService);
	dataSource: ConvenioDataSource | null;

	constructor(private convenioService: ConvenioService) { }
	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild('filter') filter: ElementRef;

	ngOnInit() {
		
		this.dataSource = new ConvenioDataSource(this.convenioDao, this.paginator, this.sort);
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