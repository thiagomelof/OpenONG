package dto;

import java.util.Date;
import model.Convenio;
import model.ParceiroDeNegocio;

public class RelatorioDoacaoParameters {

    private Date dataInicio;
    private Date dataFim;
    private ParceiroDeNegocio parceiro;
    private Convenio convenio;

    public RelatorioDoacaoParameters() {
        dataInicio = new Date();
        dataInicio = new Date();
        parceiro = new ParceiroDeNegocio();
        convenio = new Convenio();
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public ParceiroDeNegocio getParceiro() {
        return parceiro;
    }

    public void setParceiro(ParceiroDeNegocio parceiro) {
        this.parceiro = parceiro;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
}
