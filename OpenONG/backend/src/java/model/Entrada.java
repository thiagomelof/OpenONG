package model;
import java.util.Date;
import java.util.List;
import model.objetosBase.Documento;
import model.objetosBase.LinhaDocumento;

public class Entrada extends Documento{
    private Projeto projeto;
    private List<LinhaDocumento> linhas;

    public Entrada(Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
    }
    
    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public List<LinhaDocumento> getLinhas() {
        return linhas;
    }

    public void setLinhas(List<LinhaDocumento> linhas) {
        this.linhas = linhas;
    }
}