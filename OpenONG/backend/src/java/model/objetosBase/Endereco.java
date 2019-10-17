package model.objetosBase;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco {
    @Column(length = 200)
    private String descricao;    
    @Column(length = 50)
    private String logradouro;
    @Column(length = 200)
    private String rua;
    @Column(length = 20)
    private String numRua;
    @Column(length = 200)
    private String complemento;
    @Column(length = 100)
    private String bairro;
    @Column(length = 20)
    private String cep;
    @Column(length = 11)
    private String cidade;
    @Column(length = 11)
    private String estado;
    @Column(length = 100)
    private String pontoDeReferencia;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumRua() {
        return numRua;
    }

    public void setNumRua(String numRua) {
        this.numRua = numRua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPontoDeReferencia() {
        return pontoDeReferencia;
    }

    public void setPontoDeReferencia(String pontoDeReferencia) {
        this.pontoDeReferencia = pontoDeReferencia;
    }
}
