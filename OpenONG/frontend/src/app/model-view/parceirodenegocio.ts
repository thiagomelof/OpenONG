import { Usuario } from "./usuario";
import { Endereco } from "./Endereco";

export class ParceiroDeNegocio {
    id: number;
    nome: string;
    email: string;
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
}