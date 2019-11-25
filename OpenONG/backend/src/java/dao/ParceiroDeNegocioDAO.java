package dao;

import constantes.Status;
import constantes.TipoParceiro;
import dao.base.BaseDao;
import dao.interfaces.IParceiroDeNegocioDAO;
import java.io.Serializable;
import java.util.Date;
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

    @Override
    public List<ParceiroDeNegocio> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ParceiroDeNegocio.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<ParceiroDeNegocio> parceiros = criteria.list();

        return parceiros;
    }

    public List<ParceiroDeNegocio> pesquisarTodosAtivos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from ParceiroDeNegocio where status =:statusHQL");
        consulta.setParameter("statusHQL", true);

        return consulta.list();
    }

    public List<ParceiroDeNegocio> pesquisarPorTipo(TipoParceiro tipoParceiro, Status status, Date dtInicio, Date dtFim, Session session) throws HibernateException {
        String query = " from ParceiroDeNegocio where 1=1 ";

        if (dtInicio != null || dtFim != null) {
            query += " and dataCriacao BETWEEN :dtInicioHQL and :dtFimHQL ";
        }

        if (tipoParceiro == TipoParceiro.B || tipoParceiro == TipoParceiro.D) {
            query += " and tipoParceiro =:tipoHQL ";
        }

        if (tipoParceiro == TipoParceiro.DF) {
            query += " and ( tipoParceiro !=:tipoHQL ) ";
        }

        if (status == Status.A || status == Status.I) {
            query += " and status=:statusHQL ";
        }

        Query consulta = session.createQuery(query);

        if (dtInicio != null || dtFim != null) {
            consulta.setParameter("dtInicioHQL", dtInicio);
            consulta.setParameter("dtFimHQL", dtFim);
        }

        if (tipoParceiro == TipoParceiro.B || tipoParceiro == TipoParceiro.D) {
            consulta.setParameter("tipoHQL", tipoParceiro);
        }

        if (tipoParceiro == TipoParceiro.DF) {
            consulta.setParameter("tipoHQL", TipoParceiro.B);
        }

        if (status == Status.A) {
            consulta.setParameter("statusHQL", true);
        }

        if (status == Status.I) {
            consulta.setParameter("statusHQL", false);
        }

        return consulta.list();
    }

    public boolean parceiroExists(Long id, String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ParceiroDeNegocio.class);
        criteria.add(Restrictions.eq("nome", nome));
        if (id > 0) {
            criteria.add(Restrictions.ne("id", id));
        }
        List<ParceiroDeNegocio> parceiros = criteria.list();

        if (parceiros.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean cnpjExists(Long id, String cnpj, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ParceiroDeNegocio.class);
        criteria.add(Restrictions.eq("cnpj", cnpj));
        if (id > 0) {
            criteria.add(Restrictions.ne("id", id));
        }
        List<ParceiroDeNegocio> parceiros = criteria.list();

        if (parceiros.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean cpfExists(Long id, String cpf, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ParceiroDeNegocio.class);
        criteria.add(Restrictions.eq("cpf", cpf));
        if (id > 0) {
            criteria.add(Restrictions.ne("id", id));
        }
        List<ParceiroDeNegocio> parceiros = criteria.list();

        if (parceiros.size() > 0) {
            return true;
        }
        return false;
    }
}
