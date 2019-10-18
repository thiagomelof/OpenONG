package model;
import java.util.Date;
import java.util.List;
import model.objetosBase.Documento;

public class Projeto extends Documento{
    private Date validoDe;
    private Date validoAte;  
     private List<GrupoDeItens> gruposDeItens; 
     private List<Item> itens; 

    public Projeto(Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
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

    public List<GrupoDeItens> getGruposDeItens() {
        return gruposDeItens;
    }

    public void setGruposDeItens(List<GrupoDeItens> gruposDeItens) {
        this.gruposDeItens = gruposDeItens;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}