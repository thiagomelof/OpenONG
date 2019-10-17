package model;
import java.util.Date;
import model.objetosBase.DocumentoDeMarketing;

public class Projeto extends DocumentoDeMarketing{
    private Date validoDe;
    private Date validoAte;  
    //rastreabilidade
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
