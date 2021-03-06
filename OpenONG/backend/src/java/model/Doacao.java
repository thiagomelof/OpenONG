package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
public class Doacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parceiroDeNegocio")
    private ParceiroDeNegocio parceiroDeNegocio;
    @Column
    private boolean status;
    @Transient
    private String strStatus;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lancamento;
    @Lob
    private String observacoes;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "convenio")
    private Convenio convenio;
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
    @JsonIgnore
    @OneToMany(mappedBy = "doacao", fetch = FetchType.LAZY)
    private List<DoacaoItem> itemDoacao;

    private static final long serialVersionUID = 1L;

    public Doacao() {
    }

    public Doacao(ParceiroDeNegocio parceiroDeNegocio, boolean status, Date lancamento, String observacoes, Convenio convenio, Date dataCriacao, Usuario usuarioCriacao) {
        this.parceiroDeNegocio = parceiroDeNegocio;
        this.status = status;
        this.lancamento = lancamento;
        this.observacoes = observacoes;
        this.convenio = convenio;
        this.dataCriacao = dataCriacao;
        this.usuarioCriacao = usuarioCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParceiroDeNegocio getParceiroDeNegocio() {
        return parceiroDeNegocio;
    }

    public void setParceiroDeNegocio(ParceiroDeNegocio parceiroDeNegocio) {
        this.parceiroDeNegocio = parceiroDeNegocio;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getStrStatus() {
        if (status) {
            return "Ativo";
        }
        return "Cancelado";
    }

    public void setStrStatus(String strStatus) {
        if (status) {
            this.strStatus = "Ativo";
        }
        this.strStatus = "Cancelado";
    } 

    public Date getLancamento() {
        return lancamento;
    }

    public void setLancamento(Date lancamento) {
        this.lancamento = lancamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
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
}
