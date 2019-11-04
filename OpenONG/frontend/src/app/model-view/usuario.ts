import { Endereco } from "./Endereco";

export class Usuario {
    id: number;
    nome: string;
    senha: string;
    email: string;
    telefone: string;
    celular: string;
    cpf: string;
    observacoes: string;
    endereco: Endereco;

    constructor() {
        this.endereco = new Endereco();
    }
}