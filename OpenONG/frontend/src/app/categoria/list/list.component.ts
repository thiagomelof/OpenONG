import {fromEvent as observableFromEvent,  Observable } from 'rxjs';

import {distinctUntilChanged, debounceTime} from 'rxjs/operators';
import { Component, OnInit , ElementRef, ViewChild} from '@angular/core';
import { ExampleDatabase, ExampleDataSource } from './helpers.data';
import { MatPaginator, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { CategoriaService } from '../../categoria.service';


@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

	showNavListCode;
	displayedColumns = ['ID', 'Nome', 'Observações'];
	exampleDatabase = new ExampleDatabase(this.categoriaService);
	dataSource: ExampleDataSource | null;
	constructor(private categoriaService: CategoriaService) { }
	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild('filter') filter: ElementRef;

	ngOnInit() {
	    this.dataSource = new ExampleDataSource(this.exampleDatabase, this.paginator, this.sort);
	    observableFromEvent(this.filter.nativeElement, 'keyup').pipe(
	        debounceTime(150),
	        distinctUntilChanged(),)
	        .subscribe(() => {
	          if (!this.dataSource) { return; }
	          this.dataSource.filter = this.filter.nativeElement.value;
	        });
	}

	// isAllSelected(): boolean {
	//     if (!this.dataSource) { return false; }
	//     if (this.selection.isEmpty()) { return false; }

	//     if (this.filter.nativeElement.value) {
	//       return this.selection.selected.length == this.dataSource.renderedData.length;
	//     } else {
	//       return this.selection.selected.length == this.exampleDatabase.data.length;
	//     }
	// }

	masterToggle() {
	    if (!this.dataSource) { return; }

	    // if (this.isAllSelected()) {
	    //   this.selection.clear();
	    // } else if (this.filter.nativeElement.value) {
	    //   this.dataSource.renderedData.forEach(data => this.selection.select(data.id));
	    // } else {
	    //   this.exampleDatabase.data.forEach(data => this.selection.select(data.id));
	    // }
	}


}