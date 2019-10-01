package model;
import java.util.Date;
import model.objetosBase.DocumentoDeMarketing;

public class Projeto extends DocumentoDeMarketing{
    private Date validoDe;
    private Date validoAte;  

    public Projeto(Date validoDe, Date validoAte, Integer id, ParceiroDeNegocio parceiroDeNegocio, boolean status, Date lancamento, Date vencimento, String observacoes, Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(id, parceiroDeNegocio, status, lancamento, vencimento, observacoes, usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
        this.validoDe = validoDe;
        this.validoAte = validoAte;
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
}
