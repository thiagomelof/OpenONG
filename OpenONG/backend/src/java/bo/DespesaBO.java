package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.DespesaItemDAO;
import dao.DespesaDAO;
import dao.base.HibernateUtil;
import dto.ConsumoConvenioMessage;
import dto.ConvenioMessage;
import dto.DespesaMessage;
import dto.DespesasPorCategoriaMessage;
import dto.DespesasPorPeriodoMessage;
import dto.RelatorioDespesaParameters;
import dto.RetornoMessage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
            if (parametros.getConvenio().getId() > 0 || parametros.getConvenio().getId() == -1) {
                idConvenio = parametros.getConvenio().getId();
            }
        }
        List<DespesaItem> despesas = new DespesaDAO().relatorioDeDespesa(idParceiro, idConvenio, parametros.getDataInicio(), parametros.getDataFim(), session);

        session.close();
        return despesas;
    }

    public List<DespesasPorPeriodoMessage> despesasPorPeriodo(int ano) {
        Session session = HibernateUtil.abrirSessao();
        List<DespesaItem> despesas = new DespesaDAO().despesasPorPeriodo(DataUtils.primeiroDiaDoAno(ano), DataUtils.ultimoDiaDoAno(ano), session);
        session.close();

        List<DespesasPorPeriodoMessage> msg = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            DespesasPorPeriodoMessage d = new DespesasPorPeriodoMessage();
            d.setMes(i);
            d.setValor(0);
            msg.add(d);
        }

        for (DespesaItem despesa : despesas) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("america/sao_paulo"));
            cal.setTime(despesa.getDespesa().getLancamento());
            int mes = (cal.get(Calendar.MONTH) + 1);
            for (DespesasPorPeriodoMessage despesasPorPeriodoMessage : msg) {
                if (despesasPorPeriodoMessage.getMes() == mes) {
                    double soma = despesasPorPeriodoMessage.getValor() + (despesa.getQuantidade() * despesa.getValorUnitario());
                    despesasPorPeriodoMessage.setValor(soma);
                    break;
                }
            }
        }

        Collections.sort(msg, new Comparator<DespesasPorPeriodoMessage>() {
            @Override
            public int compare(DespesasPorPeriodoMessage c1, DespesasPorPeriodoMessage c2) {
                return Double.compare(c1.getMes(), c2.getMes());
            }
        });

        return msg;
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

    public List<DespesasPorCategoriaMessage> DespesasAtivasPorCategoriaMes(String mes, String ano) {
        Session session = HibernateUtil.abrirSessao();
        List<DespesaItem> despesas = new DespesaDAO().DespesasAtivasPorCategoria(DataUtils.primeiroDiaDoMes(mes, ano), DataUtils.ultimoDiaDoMes(mes, ano), session);
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

        long idConvenio = 0;
        if (despesa.getDespesa().getConvenio() != null) {
            if (despesa.getDespesa().getConvenio().getId() > 0) {
                idConvenio = despesa.getDespesa().getConvenio().getId();

                ConvenioMessage convenio = new ConvenioBO().getConvenio(idConvenio);

                if (despesa.getDespesa().getLancamento().before(convenio.getConvenio().getValidoDe())
                        || despesa.getDespesa().getLancamento().after(convenio.getConvenio().getValidoAte())) {
                    erros.add(new Erro(CodigoErro.DESPESAAE, "Este lançamento está fora do período de validade do convênio."));
                }
            }
        }

        List<Erro> errosItens = validacoesItens(despesa.getItens(), idConvenio, session);

        if (errosItens.size() > 0) {
            erros.addAll(errosItens);
        }

        return erros;
    }

    private List<Erro> validacoesItens(List<DespesaItem> itens, long idConvenio, Session session) {
        List<Erro> erros = new ArrayList<>();
        if (itens.size() == 0) {
            erros.add(new Erro(CodigoErro.DESPESAAB, "Necessário informar ao menos um item de despesa."));
        }

        if (idConvenio > 0) {
            List<ConsumoConvenioMessage> consumo = new ConvenioBO().GetConsumoConvenio(idConvenio);

            double valorTotal = itens.stream().mapToDouble((item) -> item.getQuantidade() * item.getValorUnitario()).sum();

            double valorConsumo = consumo.stream().mapToDouble((item) -> item.getDespesa()).sum();

            double valorDoacao = consumo.get(0).getDoacao();

            if ((valorTotal + valorConsumo) > valorDoacao) {
                erros.add(new Erro(CodigoErro.DESPESAAF, "Convênio não há fundos o suficente para esta despesa."));
            }
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
        if (despesa.getConvenio() == null) {
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
