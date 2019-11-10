import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators, FormGroup } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { input_HELPERS, Messages, Links } from './helpers.data';
import { ParceiroDeNegocioService } from '../../services/parceiro-de-negocio.service';

export class MyErrorStateMatcher implements ErrorStateMatcher {
	isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
		const isSubmitted = form && form.submitted;
		return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
	}
}
const EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
@Component({
	selector: 'cdk-input',
	templateUrl: './input.component.html',
	styleUrls: ['./input.component.scss']
})
export class InputComponent implements OnInit {
	InputHelpers: any = input_HELPERS;
	links = Links;
	selectedValue;
	showMultiListCode: boolean = false;
	messages = Messages;
	valueEmail = '';
	valueTeste = '';
	nomeFormControl = new FormControl('', [Validators.required]);
	codigoFormControl = new FormControl('', [Validators.required]);
	emailFormControl = new FormControl('', [Validators.required, Validators.pattern(EMAIL_REGEX)]);
	tipoDeParceiroControl = new FormControl('', [Validators.required]);
	matcher = new MyErrorStateMatcher();

	

	

	parceiros: Array<any>;
	parceiro: any;
	teste: any;

	constructor(private parceiroDeNegocioService: ParceiroDeNegocioService) { }

	ngOnInit() {
		this.listar();
		this.parceiro = {};
	}


	listar() {
		this.parceiroDeNegocioService.listar().subscribe(dados => this.parceiros = dados);
	}

	criar(frm: FormGroup) {

		this.parceiroDeNegocioService.add(this.parceiro).subscribe(resposta => this.teste = resposta);

		frm.reset();
	}

}
