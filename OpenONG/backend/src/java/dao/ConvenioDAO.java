package dao;

import dao.base.BaseDao;
import java.io.Serializable;
import java.util.List;
import model.Convenio;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import dao.interfaces.IConvenioDAO;

public class ConvenioDAO extends BaseDao<Convenio, Long>
        implements IConvenioDAO, Serializable {

    @Override
    public Convenio pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Convenio) session.get(Convenio.class, id);
    }

    @Override
    public List<Convenio> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Convenio");
        return consulta.list();
    }

    @Override
    public List<Convenio> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Convenio.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<Convenio> convenios = criteria.list();

        return convenios;
    }

    @Override
    public List<Convenio> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Convenio.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<Convenio> convenios = criteria.list();

        return convenios;
    }
}
