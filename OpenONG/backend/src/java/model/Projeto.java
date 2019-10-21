package model;

import java.util.Date;
import java.util.List;
import javax.persistence.Embedded;

public class Projeto {

    private Long id;

    private ParceiroDeNegocio parceiroDeNegocio;

    private boolean status;

    private String observacoes;

    private Date validoDe;

    private Date validoAte;
    @Embedded
    private Rastreabilidade rastreabilidade;

    List<Categoria> categorias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Rastreabilidade getRastreabilidade() {
        return rastreabilidade;
    }

    public void setRastreabilidade(Rastreabilidade rastreabilidade) {
        this.rastreabilidade = rastreabilidade;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
