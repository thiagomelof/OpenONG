import { Doacao } from "./doacao";
import { Item } from "./item";

export class DoacaoItem {
    id: number;
    linha: string;
    doacao: Doacao;
    item: Item;
    valorUnitario: number;
    quantidade: number;
    vencimento: Date;
    observacoes: string;

    constructor(){
        
    }
}