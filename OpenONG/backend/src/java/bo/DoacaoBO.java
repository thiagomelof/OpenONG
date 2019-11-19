package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.DoacaoItemDAO;
import dao.DoacaoDAO;
import dao.base.HibernateUtil;
import dto.DoacaoMessage;
import dto.RetornoMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Doacao;
import model.DoacaoItem;
import model.Erro;
import model.ParceiroDeNegocio;
import model.Usuario;
import org.hibernate.Session;

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
                for (DoacaoItem item : doacao.getItens()) {
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
}
