package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import model.Usuario;

@Embeddable
public class Rastreabilidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuarioCriacao")
    private Usuario usuariocriacao;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuarioModificacao")
    private Usuario usuariomodificacao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCriacao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataModificacao;

    private static final long serialVersionUID = 1L;

    
    
    public Usuario getUsuariocriacao() {
        return usuariocriacao;
    }

    public void setUsuariocriacao(Usuario usuariocriacao) {
        this.usuariocriacao = usuariocriacao;
    }

    public Usuario getUsuariomodificacao() {
        return usuariomodificacao;
    }

    public void setUsuariomodificacao(Usuario usuariomodificacao) {
        this.usuariomodificacao = usuariomodificacao;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
