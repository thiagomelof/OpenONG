package model;

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

@Entity
public class Projeto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nome;    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parceiroDeNegocio")
    private ParceiroDeNegocio parceiroDeNegocio;

    private boolean status;
    @Lob
    private String observacoes;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date validoDe;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date validoAte;

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
    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY)
    private List<Despesa> despesas;
    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY)
    private List<Doacao> doacoes;
    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY)
    private List<ProjetoCategoria> projetoCategoria;

    private static final long serialVersionUID = 1L;

    public Projeto() {
    }

    public Projeto(String nome, ParceiroDeNegocio parceiroDeNegocio, boolean status, String observacoes, Date validoDe, Date validoAte, Date dataCriacao, Usuario usuarioCriacao) {
        this.nome = nome;
        this.parceiroDeNegocio = parceiroDeNegocio;
        this.status = status;
        this.observacoes = observacoes;
        this.validoDe = validoDe;
        this.validoAte = validoAte;
        this.dataCriacao = dataCriacao;
        this.usuarioCriacao = usuarioCriacao;
    }

    
    
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getValidoDe() {
        return validoDe;
    }

    public void setValidoDe(Date validoDe) {
        this.validoDe = validoDe;
    }

    public Date getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(Date validoAte) {
        this.validoAte = validoAte;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public List<Doacao> getDoacoes() {
        return doacoes;
    }

    public void setDoacoes(List<Doacao> doacoes) {
        this.doacoes = doacoes;
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

    public List<ProjetoCategoria> getProjetoCategoria() {
        return projetoCategoria;
    }

    public void setProjetoCategoria(List<ProjetoCategoria> projetoCategoria) {
        this.projetoCategoria = projetoCategoria;
    }

}
