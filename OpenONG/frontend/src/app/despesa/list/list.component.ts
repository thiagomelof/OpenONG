import { fromEvent as observableFromEvent, Observable } from 'rxjs';

import { distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { DespesaDao, DespesaDataSource } from './helpers.data';
import { MatPaginator, MatSort } from '@angular/material';
import { DespesaService } from '../../services/despesa.service';


@Component({
	selector: 'app-list',
	templateUrl: './list.component.html',
	styleUrls: ['./../../shared/shared-table.component.scss']
})


export class ListComponent implements OnInit {
	public progressBarMode;
	displayedColumns = ['ID', 'Parceiro', 'Data', 'Status', 'Observações', 'Editar'];
	despesaDao = new DespesaDao(this.despesaService);
	dataSource: DespesaDataSource | null;

	constructor(private despesaService: DespesaService) { }
	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild('filter') filter: ElementRef;

	ngOnInit() {
		this.dataSource = new DespesaDataSource(this.despesaDao, this.paginator, this.sort);
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