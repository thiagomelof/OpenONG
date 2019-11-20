import { Convenio } from './../convenio';
import { ParceiroDeNegocio } from './../parceiro-de-negocio';
export class DoacaoParameters {
    parceiro: ParceiroDeNegocio;
    convenio: Convenio;
    dataInicio: Date;
    dataFim: Date;

    constructor() { }
}