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

    constructor(id?: number, nome?: string, observacoes?: string, 
        status?: boolean, dataCriacao?: Date, dataModificacao?: Date,
        usuarioCriacao?: Usuario, usuarioModificacao?: Usuario
    ) {
this.id = id;
this.nome = nome;
this.status = status;
this.observacoes = observacoes;
this.dataCriacao = dataCriacao;
this.dataModificacao = dataModificacao;
this.usuarioCriacao = usuarioCriacao;
this.usuarioModificacao = usuarioModificacao;

    }
}
