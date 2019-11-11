import { ParceiroDeNegocio } from "./parceiro-de-negocio";
import { Convenio } from "./convenio";
import { Usuario } from "./usuario";

export class Doacao {
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