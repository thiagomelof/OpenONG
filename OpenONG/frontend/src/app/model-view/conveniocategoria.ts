import { Convenio } from "./convenio";
import { Categoria } from "./categoria";

export class ConvenioCategoria {
    id: number;
    linha: number;
    convenio: Convenio;
    categoria: Categoria;
    percentual: number;

    constructor(){
        
    }
}