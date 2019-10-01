package model;
import java.util.Date;
import model.objetosBase.DocumentoDeMarketing;

public class Entrada extends DocumentoDeMarketing{
    private Projeto projeto;

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
}
