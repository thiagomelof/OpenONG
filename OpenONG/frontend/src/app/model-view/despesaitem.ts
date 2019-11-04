import { Despesa } from "./Despesa";
import { Item } from "./item";

export class DespesaItem {
    id: number;
    linha: number;
    despesa: Despesa;
    item: Item;
    valorUnitario: number;
    quantidade: number;
    vencimento: Date;
    observacoes: string;
}