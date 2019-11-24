package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.DoacaoItemDAO;
import dao.DoacaoDAO;
import dao.base.HibernateUtil;
import dto.DespesasPorCategoriaMessage;
import dto.DoacaoMessage;
import dto.DoacoesPorPeriodoMessage;
import dto.RelatorioDoacaoParameters;
import dto.RetornoMessage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import model.Doacao;
import model.DoacaoItem;
import model.Erro;
import model.ParceiroDeNegocio;
import model.Usuario;
import org.hibernate.Session;
import util.DataUtils;

public class DoacaoBO {

    public DoacaoMessage getDoacao(Long id) {
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        DoacaoItemDAO itensDAO = new DoacaoItemDAO();
        Session session = HibernateUtil.abrirSessao();
        Doacao doacao = doacaoDAO.pesquisarPorId(id, session);
        List<DoacaoItem> list = itensDAO.pesquisarTodosPorDoacao(doacao.getId(), session);
        formatarObjeto(doacao);

        DoacaoMessage msg = new DoacaoMessage(doacao, list);

        session.close();
        return msg;
    }

    public List<Doacao> getDoacoes() {
        Session session = HibernateUtil.abrirSessao();
        List<Doacao> doacoes = new DoacaoDAO().pesquisarTodos(session);
        session.close();
        return doacoes;
    }

    public List<Doacao> getDoacoesAtivos() {
        Session session = HibernateUtil.abrirSessao();
        List<Doacao> doacoes = new DoacaoDAO().pesquisarTodosAtivos(session);
        session.close();
        return doacoes;
    }

    public List<DoacaoItem> relatorioDeDoacao(RelatorioDoacaoParameters parametros) {
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
        List<DoacaoItem> doacoes = new DoacaoDAO().relatorioDeDoacao(idParceiro, idConvenio, parametros.getDataInicio(), parametros.getDataFim(), session);

        session.close();
        return doacoes;
    }

    public List<DoacoesPorPeriodoMessage> doacoesPorPeriodo(int ano) {
        Session session = HibernateUtil.abrirSessao();
        List<DoacaoItem> doacaos = new DoacaoDAO().doacoesPorPeriodo(DataUtils.primeiroDiaDoAno(ano), DataUtils.ultimoDiaDoAno(ano), session);
        session.close();

        List<DoacoesPorPeriodoMessage> msg = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            DoacoesPorPeriodoMessage d = new DoacoesPorPeriodoMessage();
            d.setMes(i);
            d.setValor(0);
            msg.add(d);
        }

        for (DoacaoItem doacao : doacaos) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("america/sao_paulo"));
            cal.setTime(doacao.getDoacao().getLancamento());
            int mes = (cal.get(Calendar.MONTH) + 1);
            for (DoacoesPorPeriodoMessage doacoesPorPeriodoMessage : msg) {
                if (doacoesPorPeriodoMessage.getMes() == mes) {
                    double soma = doacoesPorPeriodoMessage.getValor() + (doacao.getQuantidade() * doacao.getValorUnitario());
                    doacoesPorPeriodoMessage.setValor(soma);
                    break;
                }
            }
        }

        Collections.sort(msg, new Comparator<DoacoesPorPeriodoMessage>() {
            @Override
            public int compare(DoacoesPorPeriodoMessage c1, DoacoesPorPeriodoMessage c2) {
                return Double.compare(c1.getMes(), c2.getMes());
            }
        });

        return msg;
    }

    public RetornoMessage cadastrar(DoacaoMessage doacao) {
        RetornoMessage msg = new RetornoMessage();
        Session session = HibernateUtil.abrirSessao();

        List<Erro> erros = validacoes(doacao, session);

        if (erros.size() > 0) {
            msg.getErros().addAll(erros);
        } else {
            DoacaoDAO doacaoDAO = new DoacaoDAO();

            if (doacao.getDoacao().getDataCriacao() == null) {
                doacao.getDoacao().setDataCriacao(new Date());
            } else {
                doacao.getDoacao().setDataModificacao(new Date());
            }
            boolean retorno = doacaoDAO.salvarOuAlterar(doacao.getDoacao(), session);

            if (!retorno) {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir a doação no banco de dados"));
            }

            if (retorno) {
                resetLinhasDoacao(doacao.getDoacao().getId(), session);
                for (DoacaoItem item : doacao.getItens()) {
                    item.setId(null);
                    item.getDoacao().setId(doacao.getDoacao().getId());
                    retorno = new DoacaoItemDAO().salvarOuAlterar(item, session);
                }
            }

            if (retorno) {
                msg.getResultado().setId(doacao.getDoacao().getId());
                msg.getResultado().setTipoRegistro(TipoRegistro.DOACAO);
            } else {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir os itens da doação no banco de dados"));
            }
        }
        session.close();
        return msg;
    }

    private List<Erro> validacoes(DoacaoMessage doacao, Session session) {
        List<Erro> erros = new ArrayList<>();
        if (doacao.getDoacao().getParceiroDeNegocio().getId() == null || doacao.getDoacao().getParceiroDeNegocio().getId() == -1) {
            erros.add(new Erro(CodigoErro.DOACAOAA, "Necessário informar um fornecedor válido."));
        }

        List<Erro> errosItens = validacoesItens(doacao.getItens(), session);

        if (errosItens.size() > 0) {
            erros.addAll(errosItens);
        }

        return erros;
    }

    private List<Erro> validacoesItens(List<DoacaoItem> itens, Session session) {
        List<Erro> erros = new ArrayList<>();
        if (itens.size() == 0) {
            erros.add(new Erro(CodigoErro.DOACAOAB, "Necessário informar ao menos um item de doação"));
        }
        return erros;
    }

    private void formatarObjeto(Doacao doacao) {
        if (doacao.getUsuarioCriacao() == null) {
            doacao.setUsuarioCriacao(new Usuario());
        }
        if (doacao.getUsuarioModificacao() == null) {
            doacao.setUsuarioModificacao(new Usuario());
        }
        if (doacao.getParceiroDeNegocio() == null) {
            doacao.setParceiroDeNegocio(new ParceiroDeNegocio());
        }
    }

    private void resetLinhasDoacao(Long idDocao, Session session) {
        List<DoacaoItem> itens = new DoacaoItemDAO().pesquisarTodosPorDoacao(idDocao, session);
        for (DoacaoItem item : itens) {
            if (item.getId() != null) {
                if (item.getId() > 0) {
                    new DoacaoItemDAO().excluir(item, session);
                    item.setId(null);
                }
            }
        }
    }
}
