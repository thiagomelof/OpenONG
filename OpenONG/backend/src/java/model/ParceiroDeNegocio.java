package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constantes.TipoParceiro;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import javax.persistence.Transient;

@Entity
public class ParceiroDeNegocio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column
    private boolean status;
    @Transient
    private String strStatus;
    @Column(length = 100)
    private String email;
    @Column(length = 30)
    private String telefone;
    @Column(length = 30)
    private String celular;
    @Column(length = 30)
    private String cpf;
    @Enumerated(EnumType.STRING)
    @Column
    private TipoParceiro tipoParceiro;
    @Transient
    private String strTipoParceiro;
    @Lob
    private String observacoes;
    @Column(length = 200)
    private String site;
    @Column(length = 30)
    private String cnpj;
    @Embedded
    private Endereco endereco;
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
    @OneToMany(mappedBy = "parceiroDeNegocio", fetch = FetchType.LAZY)
    private List<Despesa> despesas;
    @JsonIgnore
    @OneToMany(mappedBy = "parceiroDeNegocio", fetch = FetchType.LAZY)
    private List<Doacao> doacoes;
    @JsonIgnore
    @OneToMany(mappedBy = "parceiroDeNegocio", fetch = FetchType.LAZY)
    private List<Convenio> convenios;

    private static final long serialVersionUID = 1L;

    public ParceiroDeNegocio() {
    }

    public ParceiroDeNegocio(String nome, boolean status, TipoParceiro tipoParceiro, String email, String telefone, String celular, String cpf, String observacoes, String site, String cnpj, Endereco endereco, Date dataCriacao, Usuario usuarioCriacao) {
        this.nome = nome;
        this.email = email;
        this.status = status;
        this.telefone = telefone;
        this.celular = celular;
        this.cpf = cpf;
        this.observacoes = observacoes;
        this.site = site;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.dataCriacao = dataCriacao;
        this.usuarioCriacao = usuarioCriacao;
        this.tipoParceiro = tipoParceiro;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
        return "Inativo";
    }

    public void setStrStatus(String strStatus) {
        if (status) {
            this.strStatus = "Ativo";
        }
        this.strStatus = "Inativo";
    }

    public TipoParceiro getTipoParceiro() {
        return tipoParceiro;
    }

    public void setTipoParceiro(TipoParceiro tipoParceiro) {
        this.tipoParceiro = tipoParceiro;
    }

    public void setStrTipoParceiro(String strTipoParceiro) {
        if (tipoParceiro == TipoParceiro.B) {
            this.strTipoParceiro = "Beneficiado";
        } else if (tipoParceiro == TipoParceiro.D) {
            this.strTipoParceiro = "Doador";
        } else if (tipoParceiro == TipoParceiro.F) {
            this.strTipoParceiro = "Fornecedor";
        }

        this.strStatus = "";
    }

    public String getStrTipoParceiro() {
        if (tipoParceiro == TipoParceiro.B) {
            return "Beneficiado";
        } else if (tipoParceiro == TipoParceiro.D) {
            return "Doador";
        } else if (tipoParceiro == TipoParceiro.F) {
            return "Fornecedor";
        }

        return "";
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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

    public List<Convenio> getConvenios() {
        return convenios;
    }

    public void setConvenios(List<Convenio> convenios) {
        this.convenios = convenios;
    }

}
