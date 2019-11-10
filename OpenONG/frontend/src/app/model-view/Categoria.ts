import { Usuario } from "./usuario";


export class Categoria {
    id: number;
    nome: string;
    status: boolean;
    strStatus: string;
    observacoes: string;
    dataCriacao: Date;
    dataModificacao: Date;
    usuarioCriacao: Usuario;
    usuarioModificacao: Usuario;

    constructor(id?: number, nome?: string, observacoes?: string,
        status?: boolean, strStatus?: string, dataCriacao?: Date, dataModificacao?: Date,
        usuarioCriacao?: Usuario, usuarioModificacao?: Usuario
    ) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.strStatus = strStatus;
        this.observacoes = observacoes;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        this.usuarioCriacao = usuarioCriacao;
        this.usuarioModificacao = usuarioModificacao;

    }
}
