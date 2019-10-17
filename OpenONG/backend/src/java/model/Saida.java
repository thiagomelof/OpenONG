package model;
import java.util.Date;
import model.objetosBase.DocumentoDeMarketing;

public class Saida extends DocumentoDeMarketing{
    private Projeto projeto;
    //rastreabilidade
    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
}
