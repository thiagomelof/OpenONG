import { TipoParceiro } from './const/tipoparceiro';
import { Usuario } from "./usuario";
import { Endereco } from "./Endereco";

export class Beneficiado {
    id: number;
    nome: string;
    email: string;
    status: boolean;
    strStatus: string;
    tipoParceiro: TipoParceiro;
    strTipoParceiro: string;
    telefone: string;
    celular: string;
    cpf: string;
    observacoes: string;
    site: string;
    cnpj: string;
    endereco: Endereco;
    dataCriacao: Date;
    dataModificacao: Date;
    usuarioCriacao: Usuario;
    usuarioModificacao: Usuario;

    constructor() {
        this.nome = "";
        this.endereco = new Endereco();
    }
}