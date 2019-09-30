package model.objetosBase;
import java.util.Date;
import model.ParceiroDeNegocio;
import model.Projeto;

public class DocumentoDeMarketing extends Rastreabilidade{
    private Integer id;
    private ParceiroDeNegocio parceiroDeNegocio;
    private boolean status;
    private Date lancamento;
    private Date vencimento;
    private String observacoes;
}
