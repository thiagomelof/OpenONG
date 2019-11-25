package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.DespesaItemDAO;
import dao.DespesaDAO;
import dao.base.HibernateUtil;
import dto.DespesaMessage;
import dto.DespesasPorCategoriaMessage;
import dto.RelatorioDespesaParameters;
import dto.RetornoMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import model.Convenio;
import model.Despesa;
import model.DespesaItem;
import model.Erro;
import model.ParceiroDeNegocio;
import model.Usuario;
import org.hibernate.Session;
import util.DataUtils;

public class DespesaBO {

    public DespesaMessage getDespesa(Long id) {
        DespesaDAO despesaDAO = new DespesaDAO();
        DespesaItemDAO itensDAO = new DespesaItemDAO();
        Session session = HibernateUtil.abrirSessao();
        Despesa despesa = despesaDAO.pesquisarPorId(id, session);
        List<DespesaItem> list = itensDAO.pesquisarTodosPorDespesa(despesa.getId(), session);
        formatarObjeto(despesa);

        DespesaMessage msg = new DespesaMessage(despesa, list);

        session.close();
        return msg;
    }

    public List<Despesa> getDespesas() {
        Session session = HibernateUtil.abrirSessao();
        List<Despesa> despesas = new DespesaDAO().pesquisarTodos(session);
        session.close();
        return despesas;
    }

    public List<Despesa> getDespesasAtivas() {
        Session session = HibernateUtil.abrirSessao();
        List<Despesa> despesas = new DespesaDAO().pesquisarTodosAtivos(session);
        session.close();
        return despesas;
    }

    public List<DespesaItem> relatorioDeDespesa(RelatorioDespesaParameters parametros) {
        Session session = HibernateUtil.abrirSessao();
        long idParceiro = 0;
        long idConvenio = 0;

        if (parametros.getParceiro().getId() != null) {
            if (parametros.getParceiro().getId() > 0) {
                idParceiro = parametros.getParceiro().getId();
            }
        }

        if (parametros.getConvenio().getId() != null) {
            if (parametros.getConvenio().getId() > 0) {
                idConvenio = parametros.getConvenio().getId();
            }
        }
        List<DespesaItem> doacoes = new DespesaDAO().relatorioDeDespesa(idParceiro, idConvenio, parametros.getDataInicio(), parametros.getDataFim(), session);

        session.close();
        return doacoes;
    }

    public List<DespesasPorCategoriaMessage> DespesasAtivasPorCategoria() {
        Session session = HibernateUtil.abrirSessao();
        List<DespesaItem> despesas = new DespesaDAO().DespesasAtivasPorCategoria(DataUtils.primeiroDiaDoMes(), DataUtils.ultimoDiaDoMes(), session);
        session.close();
        List<DespesasPorCategoriaMessage> msg = new ArrayList<>();

        for (DespesaItem despesa : despesas) {

            Boolean exists = false;
            for (DespesasPorCategoriaMessage despesasPorCategoriaMessage : msg) {
                if (despesasPorCategoriaMessage.getCategoria() == despesa.getItem().getCategoria().getNome()) {
                    double soma = despesasPorCategoriaMessage.getValor() + (despesa.getQuantidade() * despesa.getValorUnitario());
                    despesasPorCategoriaMessage.setValor(soma);
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                DespesasPorCategoriaMessage item = new DespesasPorCategoriaMessage();
                item.setCategoria(despesa.getItem().getCategoria().getNome());
                item.setValor((despesa.getQuantidade() * despesa.getValorUnitario()));
                msg.add(item);
            }
        }

        Collections.sort(msg, new Comparator<DespesasPorCategoriaMessage>() {
            @Override
            public int compare(DespesasPorCategoriaMessage c1, DespesasPorCategoriaMessage c2) {
                return Double.compare(c2.getValor(), c1.getValor());
            }
        });

        return msg;
    }

    public RetornoMessage cadastrar(DespesaMessage despesa) {
        RetornoMessage msg = new RetornoMessage();
        Session session = HibernateUtil.abrirSessao();

        List<Erro> erros = validacoes(despesa, session);

        if (erros.size() > 0) {
            msg.getErros().addAll(erros);
        } else {
            DespesaDAO despesaDAO = new DespesaDAO();

            if (despesa.getDespesa().getDataCriacao() == null) {
                despesa.getDespesa().setDataCriacao(new Date());
            } else {
                despesa.getDespesa().setDataModificacao(new Date());
            }
            boolean retorno = despesaDAO.salvarOuAlterar(despesa.getDespesa(), session);

            if (!retorno) {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir a despesa no banco de dados"));
            }

            if (retorno) {
                resetLinhasDespesa(despesa.getDespesa().getId(), session);
                for (DespesaItem item : despesa.getItens()) {
                    item.setId(null);
                    item.getDespesa().setId(despesa.getDespesa().getId());
                    retorno = new DespesaItemDAO().salvarOuAlterar(item, session);
                }
            }

            if (retorno) {
                msg.getResultado().setId(despesa.getDespesa().getId());
                msg.getResultado().setTipoRegistro(TipoRegistro.DESPESA);
            } else {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir os itens da despesa no banco de dados"));
            }
        }
        session.close();
        return msg;
    }

    private List<Erro> validacoes(DespesaMessage despesa, Session session) {
        
        if (despesa.getDespesa().getConvenio().getId() == null) {
            despesa.getDespesa().setConvenio(null);
        }

        if (despesa.getDespesa().getConvenio() != null && despesa.getDespesa().getConvenio().getId() != null) {
            if (despesa.getDespesa().getConvenio().getId() == 0) {
                despesa.getDespesa().setConvenio(null);
            }
        }
        
        List<Erro> erros = new ArrayList<>();
        if (despesa.getDespesa().getParceiroDeNegocio().getId() == null || despesa.getDespesa().getParceiroDeNegocio().getId() == -1) {
            erros.add(new Erro(CodigoErro.DESPESAAA, "Necessário informar um fornecedor válido."));
        }

        List<Erro> errosItens = validacoesItens(despesa.getItens(), session);

        if (errosItens.size() > 0) {
            erros.addAll(errosItens);
        }

        return erros;
    }

    private List<Erro> validacoesItens(List<DespesaItem> itens, Session session) {
        List<Erro> erros = new ArrayList<>();
        if (itens.size() == 0) {
            erros.add(new Erro(CodigoErro.DESPESAAB, "Necessário informar ao menos um item de despesa."));
        }
        return erros;
    }

    private void formatarObjeto(Despesa despesa) {
        if (despesa.getUsuarioCriacao() == null) {
            despesa.setUsuarioCriacao(new Usuario());
        }
        if (despesa.getUsuarioModificacao() == null) {
            despesa.setUsuarioModificacao(new Usuario());
        }
        if (despesa.getParceiroDeNegocio() == null) {
            despesa.setParceiroDeNegocio(new ParceiroDeNegocio());
        }
        if (despesa.getConvenio()== null) {
            despesa.setConvenio(new Convenio());
        }
    }

    private void resetLinhasDespesa(Long idDocao, Session session) {
        List<DespesaItem> itens = new DespesaItemDAO().pesquisarTodosPorDespesa(idDocao, session);
        for (DespesaItem item : itens) {
            if (item.getId() != null) {
                if (item.getId() > 0) {
                    new DespesaItemDAO().excluir(item, session);
                    item.setId(null);
                }
            }
        }
    }
}