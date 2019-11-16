import { ParceiroDeNegocio } from "./parceiro-de-negocio";
import { Usuario } from "./usuario";

export class Convenio {
    id: number;
    nome: string;
    parceiroDeNegocio: ParceiroDeNegocio;
    status: boolean;
    strStatus: string;
    observacoes: string;
    validoDe: Date;
    validoAte: Date;
    dataCriacao: Date;
    dataModificacao: Date;
    usuarioCriacao: Usuario;
    usuarioModificacao: Usuario;

    constructor(){
        
    }
}