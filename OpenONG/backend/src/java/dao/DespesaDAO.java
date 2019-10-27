package dao;

import dao.base.BaseDao;
import dao.interfaces.IDespesaDAO;
import java.io.Serializable;
import java.util.List;
import model.Despesa;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class DespesaDAO extends BaseDao<Despesa, Long>
        implements IDespesaDAO, Serializable {

    @Override
    public Despesa pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Despesa) session.get(Despesa.class, id);
    }

    @Override
    public List<Despesa> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Despesa");
        return consulta.list();
    }

    @Override
    public List<Despesa> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Despesa d join fetch d.parceiroDeNegocio pn"
                        + " where pn.nome like :pnHQL");
        consulta.setParameter("pnHQL", "%" + nome + "%");
        
        return consulta.list();
    }
}
