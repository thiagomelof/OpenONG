package model;

import model.objetosBase.Pessoa;
import java.util.Date;

public class ParceiroDeNegocio extends Pessoa{
    private String webSite;
    private String cnpj;
    private String observacoes;

    public ParceiroDeNegocio(String webSite, String cnpj, String observacoes, Integer id, String codigo, String nome, String email, String telefone, String celular, String cpf, boolean status, Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(id, codigo, nome, email, telefone, celular, cpf, status, usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
        this.webSite = webSite;
        this.cnpj = cnpj;
        this.observacoes = observacoes;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
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
    
    
}