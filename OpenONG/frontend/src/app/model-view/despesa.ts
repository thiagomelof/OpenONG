import { ParceiroDeNegocio } from "./parceirodenegocio";
import { Convenio } from "./convenio";
import { Usuario } from "./usuario";

export class Despesa {
    id: number;
    parceiroDeNegocio: ParceiroDeNegocio;
    status: boolean;
    lancamento: Date;
    observacoes: string;
    convenio: Convenio;
    dataCriacao: Date;
    dataModificacao: Date;
    usuarioCriacao: Usuario;
    usuarioModificacao: Usuario;
}