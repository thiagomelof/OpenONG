package dto;

import java.util.List;
import model.Doacao;
import model.DoacaoItem;

public class DoacaoMessage {
    private Doacao doacao;
    private List<DoacaoItem> itens;

    public DoacaoMessage() {
    }

    public DoacaoMessage(Doacao doacao, List<DoacaoItem> itens) {
        this.doacao = doacao;
        this.itens = itens;
    }

    public Doacao getDoacao() {
        return doacao;
    }

    public void setDoacao(Doacao doacao) {
        this.doacao = doacao;
    }

    public List<DoacaoItem> getItens() {
        return itens;
    }

    public void setItens(List<DoacaoItem> itens) {
        this.itens = itens;
    }
}
