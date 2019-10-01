package model;

import model.objetosBase.Rastreabilidade;
import java.util.Date;

public class GrupoDeItens extends Rastreabilidade{
    private Integer id;
    private String descricao;    
    private boolean status;
    private String observacoes;

    public GrupoDeItens(Integer id, String descricao, boolean status, String observacoes, Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.observacoes = observacoes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
