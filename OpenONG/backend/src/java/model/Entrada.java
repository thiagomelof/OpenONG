package model;
import java.util.Date;
import java.util.List;
import model.objetosBase.DocumentoDeMarketing;

public class Entrada extends DocumentoDeMarketing{
    private Projeto projeto;
    private List<LinhaEntrada> linhaEntrada;
    //rastreabilidade

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public List<LinhaEntrada> getLinhaEntrada() {
        return linhaEntrada;
    }

    public void setLinhaEntrada(List<LinhaEntrada> linhaEntrada) {
        this.linhaEntrada = linhaEntrada;
    }
    
    
}
