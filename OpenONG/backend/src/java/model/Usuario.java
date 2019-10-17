package model;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import model.objetosBase.Endereco;

@Entity
public class Usuario implements Serializable {
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
    
    @Embedded
    private Endereco endereco;
    
    @OneToMany(mappedBy = "usuario_modificacao", fetch = FetchType.LAZY)
    private ParceiroDeNegocio usuarioModificacaoPn;
    
    @OneToMany(mappedBy = "usuario_criacao", fetch = FetchType.LAZY)
    private ParceiroDeNegocio usuarioCriacaoPn;
    
    
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

    public ParceiroDeNegocio getUsuarioModificacaoPn() {
        return usuarioModificacaoPn;
    }

    public void setUsuarioModificacaoPn(ParceiroDeNegocio usuarioModificacaoPn) {
        this.usuarioModificacaoPn = usuarioModificacaoPn;
    }

    public ParceiroDeNegocio getUsuarioCriacaoPn() {
        return usuarioCriacaoPn;
    }

    public void setUsuarioCriacaoPn(ParceiroDeNegocio usuarioCriacaoPn) {
        this.usuarioCriacaoPn = usuarioCriacaoPn;
    }    
}