import { Endereco } from "./Endereco";

export class Usuario {
    id: number;
    nome: string;
    senha: string;
    email: string;
    status: boolean;
    strStatus: string;
    telefone: string;
    celular: string;
    cpf: string;
    observacoes: string;
    endereco: Endereco;

    constructor() {
        this.nome = "";
        this.endereco = new Endereco();
    }
}