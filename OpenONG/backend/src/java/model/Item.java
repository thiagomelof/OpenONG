package model;

import constantes.ClassificacaoItem;
import constantes.UnidadeDeMedida;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Item implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100,nullable = false)
    private String descricao;
    
    private GrupoDeItens grupoDeItens;
    private ClassificacaoItem classificacaoItem;
    private UnidadeDeMedida unidadeDeMedida;    
    private boolean status;
    
    @Lob
    private String observacoes;
    
    
    private Date dataCriacao;
    private Date dataModificacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
    
    
    
}
