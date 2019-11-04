import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators, FormGroup } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { input_HELPERS, Messages, Links } from './helpers.data';
import { ParceiroDeNegocioService } from '../../parceiro-de-negocio.service';

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
	emailFormControl = new FormControl('', [Validators.email]);
	nomeFormControl = new FormControl('', [Validators.required]);
	codigoFormControl = new FormControl('', [Validators.required]);
	emailFormControls = new FormControl('', [Validators.required, Validators.pattern(EMAIL_REGEX)]);
	matcher = new MyErrorStateMatcher();

	tipoDeParceiroControl = new FormControl('', [Validators.required]);

	tiposDeParceiro = [
		{ value: 'D', name: 'Doador' },
		{ value: 'F', name: 'Fornecedor' },
		{ value: 'C', name: 'Cliente' }
	];

	estados = [
		{ name: 'Acre', value: 'AC' },
		{ name: 'Alagoas', value: 'AL' },
		{ name: 'Amapá', value: 'AP' },
		{ name: 'Amazonas', value: 'AM' },
		{ name: 'Bahia', value: 'BA' },
		{ name: 'Ceará', value: 'CE' },
		{ name: 'Distrito Federal', value: 'DF' },
		{ name: 'Espírito Santo', value: 'ES' },
		{ name: 'Goiás', value: 'GO' },
		{ name: 'Maranhão', value: 'MA' },
		{ name: 'Mato Grosso', value: 'MT' },
		{ name: 'Mato Grosso do Sul', value: 'MS' },
		{ name: 'Minas Gerais', value: 'MG' },
		{ name: 'Pará', value: 'PA' },
		{ name: 'Paraíba', value: 'PB' },
		{ name: 'Paraná', value: 'PR' },
		{ name: 'Pernambuco', value: 'PE' },
		{ name: 'Piauí', value: 'PI' },
		{ name: 'Rio de Janeiro', value: 'RJ' },
		{ name: 'Rio Grande do Norte', value: 'RN' },
		{ name: 'Rio Grande do Sul', value: 'RS' },
		{ name: 'Rondônia', value: 'RO' },
		{ name: 'Roraima', value: 'RR' },
		{ name: 'Santa Catarina', value: 'SC' },
		{ name: 'São Paulo', value: 'SP' },
		{ name: 'Sergipe', value: 'SE' },
		{ name: 'Tocantins', value: 'TO' }
	];

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

		this.parceiroDeNegocioService.criar(this.parceiro).subscribe(resposta => this.teste = resposta);

		frm.reset();
	}

}
