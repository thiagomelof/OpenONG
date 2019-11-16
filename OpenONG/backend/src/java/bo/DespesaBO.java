
package bo;

import dao.DespesaItemDAO;
import dao.DespesaDAO;
import dao.base.HibernateUtil;
import dto.DespesaMessage;
import java.util.Date;
import java.util.List;
import model.Despesa;
import model.DespesaItem;
import model.ParceiroDeNegocio;
import model.Usuario;
import org.hibernate.Session;

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

    public List<Despesa> getDoacoes() {
        Session session = HibernateUtil.abrirSessao();
        List<Despesa> doacoes = new DespesaDAO().pesquisarTodos(session);
        session.close();
        return doacoes;
    }

    public List<Despesa> getDoacoesAtivos() {
        Session session = HibernateUtil.abrirSessao();
        List<Despesa> doacoes = new DespesaDAO().pesquisarTodosAtivos(session);
        session.close();
        return doacoes;
    }

    public DespesaMessage cadastrar(DespesaMessage despesa) {
        Session session = HibernateUtil.abrirSessao();
        DespesaDAO despesaDAO = new DespesaDAO();
        despesa.getDespesa().setDataCriacao(new Date());
        boolean salvo = despesaDAO.salvarOuAlterar(despesa.getDespesa(), session);

        if (salvo) {
            for (DespesaItem item : despesa.getItens()) {
                
                item.getDespesa().setId(despesa.getDespesa().getId());
                salvo = new DespesaItemDAO().salvarOuAlterar(item, session);
                
            }
        }
        session.close();
        if (salvo) {
            return despesa;
        }

        return null;
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
    }    
}
