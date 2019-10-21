package model;

import java.util.Date;
import javax.persistence.Embedded;

public class Despesa {

    private Integer id;

    private ParceiroDeNegocio parceiroDeNegocio;

    private boolean status;

    private Date lancamento;

    private Date vencimento;

    private String observacoes;

    private Projeto projeto;

    @Embedded
    private Rastreabilidade rastreabilidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getLancamento() {
        return lancamento;
    }

    public void setLancamento(Date lancamento) {
        this.lancamento = lancamento;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Rastreabilidade getRastreabilidade() {
        return rastreabilidade;
    }

    public void setRastreabilidade(Rastreabilidade rastreabilidade) {
        this.rastreabilidade = rastreabilidade;
    }

}
