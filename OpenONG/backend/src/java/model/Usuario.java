package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Usuario implements Serializable {

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
    @JsonIgnore
    @Column(length = 30)
    private String senha;
    @Lob
    private String observacoes;
    @Embedded
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY)
    private List<Categoria> usuarioCriacaoCategoria;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioModificacao", fetch = FetchType.LAZY)
    private List<Categoria> usuarioModificacaoCategoria;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY)
    private List<Item> usuarioCriacaoItem;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioModificacao", fetch = FetchType.LAZY)
    private List<Item> usuarioModificacaoItem;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY)
    private List<ParceiroDeNegocio> usuarioCriacaoParceiroDeNegocio;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioModificacao", fetch = FetchType.LAZY)
    private List<ParceiroDeNegocio> usuarioModificacaoParceiroDeNegocio;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY)
    private List<Despesa> usuarioCriacaoDespesa;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioModificacao", fetch = FetchType.LAZY)
    private List<Despesa> usuarioModificacaoDespesa;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY)
    private List<Doacao> usuarioCriacaoDoacao;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioModificacao", fetch = FetchType.LAZY)
    private List<Doacao> usuarioModificacaoDoacao;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY)
    private List<Convenio> usuarioCriacaoConvenio;
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioModificacao", fetch = FetchType.LAZY)
    private List<Convenio> usuarioModificacaoConvenio;

    private static final long serialVersionUID = 1L;

    public Usuario() {
        this.nome = "";
        this.email = "";
        this.telefone = "";
        this.celular = "";
        this.cpf = "";
        this.senha = "";
        this.observacoes = "";
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(String nome, String email, String telefone, String celular, String cpf, String senha, String observacoes) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.cpf = cpf;
        this.senha = senha;
        this.observacoes = observacoes;
    }

    public Usuario(String nome, String email, String telefone, String celular, String cpf, String senha, String observacoes, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.cpf = cpf;
        this.senha = senha;
        this.observacoes = observacoes;
        this.endereco = endereco;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Categoria> getUsuarioCriacaoCategoria() {
        return usuarioCriacaoCategoria;
    }

    public void setUsuarioCriacaoCategoria(List<Categoria> usuarioCriacaoCategoria) {
        this.usuarioCriacaoCategoria = usuarioCriacaoCategoria;
    }

    public List<Categoria> getUsuarioModificacaoCategoria() {
        return usuarioModificacaoCategoria;
    }

    public void setUsuarioModificacaoCategoria(List<Categoria> usuarioModificacaoCategoria) {
        this.usuarioModificacaoCategoria = usuarioModificacaoCategoria;
    }

    public List<Item> getUsuarioCriacaoItem() {
        return usuarioCriacaoItem;
    }

    public void setUsuarioCriacaoItem(List<Item> usuarioCriacaoItem) {
        this.usuarioCriacaoItem = usuarioCriacaoItem;
    }

    public List<Item> getUsuarioModificacaoItem() {
        return usuarioModificacaoItem;
    }

    public void setUsuarioModificacaoItem(List<Item> usuarioModificacaoItem) {
        this.usuarioModificacaoItem = usuarioModificacaoItem;
    }

    public List<ParceiroDeNegocio> getUsuarioCriacaoParceiroDeNegocio() {
        return usuarioCriacaoParceiroDeNegocio;
    }

    public void setUsuarioCriacaoParceiroDeNegocio(List<ParceiroDeNegocio> usuarioCriacaoParceiroDeNegocio) {
        this.usuarioCriacaoParceiroDeNegocio = usuarioCriacaoParceiroDeNegocio;
    }

    public List<ParceiroDeNegocio> getUsuarioModificacaoParceiroDeNegocio() {
        return usuarioModificacaoParceiroDeNegocio;
    }

    public void setUsuarioModificacaoParceiroDeNegocio(List<ParceiroDeNegocio> usuarioModificacaoParceiroDeNegocio) {
        this.usuarioModificacaoParceiroDeNegocio = usuarioModificacaoParceiroDeNegocio;
    }

    public List<Despesa> getUsuarioCriacaoDespesa() {
        return usuarioCriacaoDespesa;
    }

    public void setUsuarioCriacaoDespesa(List<Despesa> usuarioCriacaoDespesa) {
        this.usuarioCriacaoDespesa = usuarioCriacaoDespesa;
    }

    public List<Despesa> getUsuarioModificacaoDespesa() {
        return usuarioModificacaoDespesa;
    }

    public void setUsuarioModificacaoDespesa(List<Despesa> usuarioModificacaoDespesa) {
        this.usuarioModificacaoDespesa = usuarioModificacaoDespesa;
    }

    public List<Doacao> getUsuarioCriacaoDoacao() {
        return usuarioCriacaoDoacao;
    }

    public void setUsuarioCriacaoDoacao(List<Doacao> usuarioCriacaoDoacao) {
        this.usuarioCriacaoDoacao = usuarioCriacaoDoacao;
    }

    public List<Doacao> getUsuarioModificacaoDoacao() {
        return usuarioModificacaoDoacao;
    }

    public void setUsuarioModificacaoDoacao(List<Doacao> usuarioModificacaoDoacao) {
        this.usuarioModificacaoDoacao = usuarioModificacaoDoacao;
    }

    public List<Convenio> getUsuarioCriacaoConvenio() {
        return usuarioCriacaoConvenio;
    }

    public void setUsuarioCriacaoConvenio(List<Convenio> usuarioCriacaoConvenio) {
        this.usuarioCriacaoConvenio = usuarioCriacaoConvenio;
    }

    public List<Convenio> getUsuarioModificacaoConvenio() {
        return usuarioModificacaoConvenio;
    }

    public void setUsuarioModificacaoConvenio(List<Convenio> usuarioModificacaoConvenio) {
        this.usuarioModificacaoConvenio = usuarioModificacaoConvenio;
    }

}
