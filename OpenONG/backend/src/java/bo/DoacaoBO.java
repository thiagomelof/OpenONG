
package bo;

import dao.DoacaoItemDAO;
import dao.DoacaoDAO;
import dao.base.HibernateUtil;
import dto.DoacaoMessage;
import java.util.Date;
import java.util.List;
import model.Doacao;
import model.DoacaoItem;
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

    public DoacaoMessage cadastrar(DoacaoMessage doacao) {
        Session session = HibernateUtil.abrirSessao();
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        doacao.getDoacao().setDataCriacao(new Date());
        boolean salvo = doacaoDAO.salvarOuAlterar(doacao.getDoacao(), session);

        if (salvo) {
            for (DoacaoItem item : doacao.getItens()) {
                
                item.getDoacao().setId(doacao.getDoacao().getId());
                salvo = new DoacaoItemDAO().salvarOuAlterar(item, session);
                
            }
        }
        session.close();
        if (salvo) {
            return doacao;
        }

        return null;
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
