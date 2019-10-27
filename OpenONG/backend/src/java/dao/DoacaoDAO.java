package dao;

import dao.base.BaseDao;
import dao.interfaces.IDoacaoDAO;
import java.io.Serializable;
import java.util.List;
import model.Doacao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class DoacaoDAO extends BaseDao<Doacao, Long>
        implements IDoacaoDAO, Serializable {

    @Override
    public Doacao pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Doacao) session.get(Doacao.class, id);
    }

    @Override
    public List<Doacao> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Doacao");
        return consulta.list();
    }

    @Override
    public List<Doacao> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Doacao d join fetch d.parceiroDeNegocio pn"
                        + " where pn.nome like :pnHQL");
        consulta.setParameter("pnHQL", "%" + nome + "%");
        
        return consulta.list();
    }
}
