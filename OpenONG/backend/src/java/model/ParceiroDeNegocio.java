package model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import model.objetosBase.Endereco;

@Entity
public class ParceiroDeNegocio implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100,nullable = false)
    private String codigo;    
    
    @Column(length = 100,nullable = false)
    private String nome;  
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 30)
    private String telefone;
    
    @Column(length = 30)
    private String celular;  
    
    @Column(length = 30)
    private String cpf;
    
    @Column(length = 30)
    private String senha;
    
    @Lob
    private String observacoes;
    
    @Column(length = 200)
    private String site;
    
    @Column(length = 30)
    private String cnpj;

    @Embedded
    private Endereco endereco;
    
    @ManyToOne
    @JoinColumn(name = "usuario_criacao")
    private Usuario usuario_criacao;
    
    @ManyToOne
    @JoinColumn(name = "usuario_modificacao")
    private Usuario usuario_modificacao;
    
    private Date dataCriacao;
    private Date dataModificacao; 
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getWebSite() {
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUsuario_criacao() {
        return usuario_criacao;
    }

    public void setUsuario_criacao(Usuario usuario_criacao) {
        this.usuario_criacao = usuario_criacao;
    }

    public Usuario getUsuario_modificacao() {
        return usuario_modificacao;
    }

    public void setUsuario_modificacao(Usuario usuario_modificacao) {
        this.usuario_modificacao = usuario_modificacao;
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