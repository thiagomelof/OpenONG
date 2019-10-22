package model;

import constantes.TipoItem;
import constantes.UnidadeDeMedida;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 150, nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoItem tipoItem;
    @Enumerated(EnumType.STRING)
    @Column
    private UnidadeDeMedida unidadeDeMedida;

    private boolean status;
    @Lob
    private String observacoes;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCriacao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataModificacao;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioCriacao")
    private Usuario usuarioCriacao;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioModificacao")
    private Usuario usuarioModificacao;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<DoacaoItem> itemDoacao;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<DespesaItem> itemDespesa;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
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

    public Usuario getUsuarioCriacao() {
        return usuarioCriacao;
    }

    public void setUsuarioCriacao(Usuario usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public Usuario getUsuarioModificacao() {
        return usuarioModificacao;
    }

    public void setUsuarioModificacao(Usuario usuarioModificacao) {
        this.usuarioModificacao = usuarioModificacao;
    }

    public List<DoacaoItem> getItemDoacao() {
        return itemDoacao;
    }

    public void setItemDoacao(List<DoacaoItem> itemDoacao) {
        this.itemDoacao = itemDoacao;
    }

    public List<DespesaItem> getItemDespesa() {
        return itemDespesa;
    }

    public void setItemDespesa(List<DespesaItem> itemDespesa) {
        this.itemDespesa = itemDespesa;
    }

}
