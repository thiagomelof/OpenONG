import { ParceiroDeNegocio } from "./parceirodenegocio";
import { Usuario } from "./usuario";

export class Convenio {
    id: number;
    nome: string;
    parceiroDeNegocio: ParceiroDeNegocio;
    status: boolean;
    observacoes: string;
    validoDe: Date;
    validoAte: Date;
    dataCriacao: Date;
    dataModificacao: Date;
    usuarioCriacao: Usuario;
    usuarioModificacao: Usuario;
}