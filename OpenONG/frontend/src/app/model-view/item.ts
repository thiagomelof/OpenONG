import { Categoria } from "./categoria";
import { Usuario } from "./usuario";
import { TipoItem } from "./const/tipoitem";

export class Item {
    id: number;
    nome: string;
    categoria: Categoria;
    tipoItem: TipoItem;
    status: boolean;
    strStatus: string;
    observacoes: string;
    dataCriacao: Date;
    dataModificacao: Date;
    usuarioCriacao: Usuario;
    usuarioModificacao: Usuario;
}