
import {merge as observableMerge,  BehaviorSubject ,  Observable } from 'rxjs';

import {map} from 'rxjs/operators';
import { DataSource } from '@angular/cdk/collections';
import { LinhasDocumento } from '../interfaces';



const QUANTIDADE = ['1', '2', '3', '4'];
const NAMES = ['Verba', 'Arroz', 'Lapis de cor', 'Feijão'];

 /** An example database that the data source uses to retrieve data for the table. */
export class ExampleDatabase {
  /** Stream that emits whenever the data has been modified. */
  dataChange: BehaviorSubject<LinhasDocumento[]> = new BehaviorSubject<LinhasDocumento[]>([]);
  get data(): LinhasDocumento[] { return this.dataChange.value; }

  constructor() {
    // Fill up the database with 100 users.
    // for (let i = 0; i < 3; i++) { this.addDocumentLines(); }
    this.addDocumentLinesA();
    this.addDocumentLinesB();
    this.addDocumentLinesC();
  }

  // addDocumentLines() {
  //   const copiedData = this.data.slice();
  //   copiedData.push(this.createDocumentLines());
  //   this.dataChange.next(copiedData);
  // }
  // private createDocumentLines() {
  //   const name = NAMES[Math.round(Math.random() * (NAMES.length - 1))];

  //   return {
	// 		linha: (this.data.length + 1).toString(),
	// 		item: name,
	// 		valorUnitario: Math.round(Math.random() * 100).toString(),
	// 		quantidade: QUANTIDADE[Math.round(Math.random() * (QUANTIDADE.length - 1))],
	// 		observacoes: "teste"
  //   };
  // }


  addDocumentLinesA() {
    const copiedData = this.data.slice();
    copiedData.push(this.createDocumentLinesA());
    this.dataChange.next(copiedData);
  }

  addDocumentLinesB() {
    const copiedData = this.data.slice();
    copiedData.push(this.createDocumentLinesB());
    this.dataChange.next(copiedData);
  }

  addDocumentLinesC() {
    const copiedData = this.data.slice();
    copiedData.push(this.createDocumentLinesC());
    this.dataChange.next(copiedData);
  }


  private createDocumentLinesA() {
    const name = "Doação em dinheiro";

    return {
			linha: (this.data.length + 1).toString(),
			item: name,
			valorUnitario: "250",
			quantidade: "",
			observacoes: "Depósito feito na conta BB"
    };
  }

  private createDocumentLinesB() {
    const name = "Feijão";

    return {
			linha: (this.data.length + 1).toString(),
			item: name,
			valorUnitario: "",
			quantidade: "5",
			observacoes: "Entregue na ONG"
    };
  }

  private createDocumentLinesC() {
    const name = "Arroz";

    return {
			linha: (this.data.length + 1).toString(),
			item: name,
			valorUnitario: "",
			quantidade: "10",
			observacoes: "Entregue na ONG"
    };
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

  constructor(private _exampleDatabase: ExampleDatabase) {
    super();
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<LinhasDocumento[]> {
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._filterChange,
    ];

    return observableMerge(...displayDataChanges).pipe(map(() => {
      return this._exampleDatabase.data.slice().filter((item: LinhasDocumento) => {
        let searchStr = (item.item + item.observacoes).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) != -1;
      });
    }));
  }

  disconnect() {}
}

export const input_HELPERS: any = {

	tsSourceInput: `
import { MatInputModule } from '@angular/material';
		@NgModule({
		  imports: [
		   MatInputModule
		})
		export class AppModule { }
		
import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'cdk-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class inputComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
 }
	`.trim(),


	htmlSourceInput: `
	<form class="example-form">
	  <mat-form-field class="example-full-width">
	    <input matInput placeholder="Favorite food" value="Sushi">
	  </mat-form-field>
	</form>

	`.trim(),
}

export const Links: Array<String> = ['/home', '/admin'];
