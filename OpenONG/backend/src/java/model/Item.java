package model;

import constantes.ClassificacaoItem;
import constantes.UnidadeDeMedida;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100, nullable = false)
    private String descricao;

    private Categoria categoria;

    private ClassificacaoItem classificacaoItem;

    private UnidadeDeMedida unidadeDeMedida;

    private boolean status;
    @Lob
    private String observacoes;

    @Embedded
    private Rastreabilidade rastreabilidade;

    private static final long serialVersionUID = 1L;
    
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public Rastreabilidade getRastreabilidade() {
        return rastreabilidade;
    }

    public void setRastreabilidade(Rastreabilidade rastreabilidade) {
        this.rastreabilidade = rastreabilidade;
    }
}
