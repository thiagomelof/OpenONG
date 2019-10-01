package model;

import model.objetosBase.Rastreabilidade;
import constantes.ClassificacaoItem;
import constantes.UnidadeDeMedida;
import java.util.Date;

public class Item extends Rastreabilidade{
    private Integer id;
    private String descricao;
    private GrupoDeItens grupoDeItens;
    private ClassificacaoItem classificacaoItem;
    private UnidadeDeMedida unidadeDeMedida;    
    private boolean status;
    private String observacoes;

    public Item(Integer id, String descricao, GrupoDeItens grupoDeItens, ClassificacaoItem classificacaoItem, UnidadeDeMedida unidadeDeMedida, boolean status, String observacoes, Usuario usuarioCriacao, Date dataCriacao, Usuario usuarioModificacao, Date dataModificacao) {
        super(usuarioCriacao, dataCriacao, usuarioModificacao, dataModificacao);
        this.id = id;
        this.descricao = descricao;
        this.grupoDeItens = grupoDeItens;
        this.classificacaoItem = classificacaoItem;
        this.unidadeDeMedida = unidadeDeMedida;
        this.status = status;
        this.observacoes = observacoes;
    }
}
