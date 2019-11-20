import { Convenio } from './../convenio';
import { ParceiroDeNegocio } from './../parceiro-de-negocio';
export class DespesaParameters {
    parceiro: ParceiroDeNegocio;
    convenio: Convenio;
    dataInicio: Date;
    dataFim: Date;

    constructor() { }
}