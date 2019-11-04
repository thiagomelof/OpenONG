import { Usuario } from "./usuario";

export class Categoria {
    id: number;
    nome: string;
    status: boolean;
    observacoes: string;
    dataCriacao: Date;
    dataModificacao: Date;
    usuarioCriacao: Usuario;
    usuarioModificacao: Usuario;
}