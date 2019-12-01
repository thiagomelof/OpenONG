package dto;

import model.Categoria;

public class ConsumoConvenioMessage {
    private Long idCategoria;
    private String nomeCategoria;
    private double doacao;
    private double despesa;
    private double percentualAplicado;
    private double percentualUtilizado;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    
    public double getDoacao() {
        return doacao;
    }

    public void setDoacao(double doacao) {
        this.doacao = doacao;
    }

    public double getDespesa() {
        return despesa;
    }

    public void setDespesa(double despesa) {
        this.despesa = despesa;
    }

    public double getPercentualAplicado() {
        return percentualAplicado;
    }

    public void setPercentualAplicado(double percentualAplicado) {
        this.percentualAplicado = percentualAplicado;
    }

    public double getPercentualUtilizado() {
        return percentualUtilizado;
    }

    public void setPercentualUtilizado(double percentualUtilizado) {
        this.percentualUtilizado = percentualUtilizado;
    }
}
