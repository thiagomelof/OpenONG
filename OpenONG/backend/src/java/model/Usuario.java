package model;
import model.objetosBase.Pessoa;
import java.util.Date;

public class Usuario extends Pessoa{       
    private String senha;
    private String observacoes;

    public Usuario(String senha, String observacoes, Integer id, String codigo, String nome, String email, String telefone, String celular, String cpf, boolean status, Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(id, codigo, nome, email, telefone, celular, cpf, status, usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
        this.senha = senha;
        this.observacoes = observacoes;
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
}