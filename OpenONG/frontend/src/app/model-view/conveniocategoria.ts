import { Convenio } from "./convenio";
import { Categoria } from "./Categoria";

export class ConvenioCategoria {
    id: number;
    convenio: Convenio;
    categoria: Categoria;
    percentual: number;
}