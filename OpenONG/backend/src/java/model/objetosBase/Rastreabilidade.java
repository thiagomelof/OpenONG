package model.objetosBase;

import java.util.Date;
import model.Usuario;

public class Rastreabilidade {
    private Usuario usuarioCriacao;
    private Date dataCriacao;    
    private Usuario usuarioModificacao;
    private Date dataModificacao;   

    public Rastreabilidade(Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        this.usuarioCriacao = usuarioCriacao;
        this.dataCriacao = dataCriacao;
        this.usuarioModificacao = usuarioModificacao;
        this.dataModificacao = dataModificacao;
    }

    public Usuario getUsuarioCriacao() {
        return usuarioCriacao;
    }

    public void setUsuarioCriacao(Usuario usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuarioModificacao() {
        return usuarioModificacao;
    }

    public void setUsuarioModificacao(Usuario usuarioModificacao) {
        this.usuarioModificacao = usuarioModificacao;
    }

    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
}