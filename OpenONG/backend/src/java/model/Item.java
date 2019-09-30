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
}
