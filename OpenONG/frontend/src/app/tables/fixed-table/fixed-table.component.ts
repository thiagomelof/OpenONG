import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ExampleDatabase, ExampleDataSource } from './helpers.data';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { DATEPICKER_HELPERS } from '../../material-widgets/datepicker/helpers.data';

@Component({
	selector: 'app-fixed-table',
	templateUrl: './fixed-table.component.html',
	styleUrls: ['./fixed-table.component.scss']
})
export class FixedTableComponent implements OnInit {
	public displayedColumns = ['linha', 'item', 'valor', 'quantidade', 'observacoes'];
	public exampleDatabase = new ExampleDatabase();
	public dataSource: ExampleDataSource | null;
	public showFilterTableCode;

	dataLanc = new FormControl(new Date());
	dataVenc = new FormControl(new Date());
	events: string[] = [];

	myFilter = (d: Date): boolean => {
		const day = d.getDay();

		return day !== 0 && day !== 6;
	}

	addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
		this.events.push(`${type}: ${event.value}`);
	}
	constructor() { }

	ngOnInit() {
		this.dataSource = new ExampleDataSource(this.exampleDatabase);
	}
	
	datepickerHelpers: any = DATEPICKER_HELPERS;
}
