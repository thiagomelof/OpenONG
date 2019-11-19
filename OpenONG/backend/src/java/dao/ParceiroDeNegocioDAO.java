package dao;

import dao.base.BaseDao;
import dao.interfaces.IParceiroDeNegocioDAO;
import java.io.Serializable;
import java.util.List;
import model.ParceiroDeNegocio;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ParceiroDeNegocioDAO extends BaseDao<ParceiroDeNegocio, Long>
        implements IParceiroDeNegocioDAO, Serializable {

    @Override
    public ParceiroDeNegocio pesquisarPorId(Long id, Session session) throws HibernateException {
        return (ParceiroDeNegocio) session.get(ParceiroDeNegocio.class, id);
    }

    @Override
    public List<ParceiroDeNegocio> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from ParceiroDeNegocio");
        return consulta.list();
    }

    public List<ParceiroDeNegocio> pesquisarTodosAtivos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from ParceiroDeNegocio where status =:statusHQL");
        consulta.setParameter("statusHQL", true);

        return consulta.list();
    }

    @Override
    public List<ParceiroDeNegocio> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ParceiroDeNegocio.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<ParceiroDeNegocio> parceiros = criteria.list();

        return parceiros;
    }

    public boolean parceiroExists(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ParceiroDeNegocio.class);
        criteria.add(Restrictions.eq("nome", nome));
        List<ParceiroDeNegocio> parceiros = criteria.list();

        if (parceiros.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean cnpjExists(String cnpj, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ParceiroDeNegocio.class);
        criteria.add(Restrictions.eq("cnpj", cnpj));
        List<ParceiroDeNegocio> parceiros = criteria.list();

        if (parceiros.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean cpfExists(String cpf, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ParceiroDeNegocio.class);
        criteria.add(Restrictions.eq("cpf",cpf));
        List<ParceiroDeNegocio> parceiros = criteria.list();

        if (parceiros.size() > 0) {
            return true;
        }
        return false;
    }
}
