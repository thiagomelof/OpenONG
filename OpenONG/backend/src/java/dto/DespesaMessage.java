package dto;

import java.util.List;
import model.Despesa;
import model.DespesaItem;

public class DespesaMessage {

    private Despesa despesa;
    private List<DespesaItem> itens;

    public DespesaMessage() {
    }

    public DespesaMessage(Despesa despesa, List<DespesaItem> itens) {
        this.despesa = despesa;
        this.itens = itens;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public List<DespesaItem> getItens() {
        return itens;
    }

    public void setItens(List<DespesaItem> itens) {
        this.itens = itens;
    }
}
