import { Categoria } from "./Categoria";
import { Usuario } from "./usuario";
import { TipoItem } from "./const/tipoitem";
import { UnidadeDeMedida } from "./const/unidadedemedida";

export class Item {
    id: number;
    nome: string;
    categoria: Categoria;
    tipoItem: TipoItem;
    unidadeDeMedida: UnidadeDeMedida;
    status: boolean;
    observacoes: string;
    dataCriacao: Date;
    dataModificacao: Date;
    usuarioCriacao: Usuario;
    usuarioModificacao: Usuario;
}