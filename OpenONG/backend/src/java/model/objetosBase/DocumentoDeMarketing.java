package model.objetosBase;
import java.util.Date;
import model.ParceiroDeNegocio;
import model.Projeto;
import model.Usuario;

public class DocumentoDeMarketing extends Rastreabilidade{
    private Integer id;
    private ParceiroDeNegocio parceiroDeNegocio;
    private boolean status;
    private Date lancamento;
    private Date vencimento;
    private String observacoes;

    public DocumentoDeMarketing(Integer id, ParceiroDeNegocio parceiroDeNegocio, boolean status, Date lancamento, Date vencimento, String observacoes, Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
        this.id = id;
        this.parceiroDeNegocio = parceiroDeNegocio;
        this.status = status;
        this.lancamento = lancamento;
        this.vencimento = vencimento;
        this.observacoes = observacoes;
    }
    
    
}
