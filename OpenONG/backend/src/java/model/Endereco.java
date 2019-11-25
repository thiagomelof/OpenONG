package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco implements Serializable {

    @Column(length = 200)
    private String descricaoEndereco;
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
    @Column(length = 100)
    private String cidade;
    @Column(length = 100)
    private String estado;
    @Column(length = 100)
    private String pontoDeReferencia;

    private static final long serialVersionUID = 1L;

    public Endereco() {
        this.descricaoEndereco = "";
        this.rua = "";
        this.numRua = "";
        this.complemento = "";
        this.bairro = "";
        this.cep = "";
        this.cidade = "";
        this.estado = "";
        this.pontoDeReferencia = "";
    }

    public Endereco(String descricaoEndereco, String rua, String numRua, String complemento, String bairro, String cep, String cidade, String estado, String pontoDeReferencia) {
        this.descricaoEndereco = descricaoEndereco;
        this.rua = rua;
        this.numRua = numRua;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.pontoDeReferencia = pontoDeReferencia;
    }
    
    public String getDescricaoEndereco() {
        return descricaoEndereco;
    }

    public void setDescricaoEndereco(String descricaoEndereco) {
        this.descricaoEndereco = descricaoEndereco;
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
