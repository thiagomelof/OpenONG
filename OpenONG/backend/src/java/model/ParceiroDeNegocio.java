package model;

import java.util.Date;
import model.objetosBase.Pessoa;

public class ParceiroDeNegocio extends Pessoa{
    private String site;
    private String cnpj;

    public ParceiroDeNegocio(Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
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
}