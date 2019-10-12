package model;
import java.util.Date;
import java.util.List;
import model.objetosBase.DocumentoDeMarketing;

public class Entrada extends DocumentoDeMarketing{
    private Projeto projeto;
    private List<LinhaEntrada> linhaEntrada;

    public Entrada(Projeto projeto, Integer id, ParceiroDeNegocio parceiroDeNegocio, boolean status, Date lancamento, Date vencimento, String observacoes, Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(id, parceiroDeNegocio, status, lancamento, vencimento, observacoes, usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
        this.projeto = projeto;
    }

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
