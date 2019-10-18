package model;

import constantes.ClassificacaoItem;
import constantes.UnidadeDeMedida;
import java.util.Date;
import model.objetosBase.Rastreabilidade;

public class Item extends Rastreabilidade{

    private Integer id;
    private String descricao;    
    private GrupoDeItens grupoDeItens;
    private ClassificacaoItem classificacaoItem;
    private UnidadeDeMedida unidadeDeMedida;    
    private boolean status;    
    private String observacoes;

    public Item(Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
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

    public GrupoDeItens getGrupoDeItens() {
        return grupoDeItens;
    }

    public void setGrupoDeItens(GrupoDeItens grupoDeItens) {
        this.grupoDeItens = grupoDeItens;
    }

    public ClassificacaoItem getClassificacaoItem() {
        return classificacaoItem;
    }

    public void setClassificacaoItem(ClassificacaoItem classificacaoItem) {
        this.classificacaoItem = classificacaoItem;
    }

    public UnidadeDeMedida getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
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
