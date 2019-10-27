package dao;

import dao.base.BaseDao;
import dao.interfaces.IProjetoDAO;
import java.io.Serializable;
import java.util.List;
import model.Projeto;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ProjetoDAO extends BaseDao<Projeto, Long>
        implements IProjetoDAO, Serializable {

    @Override
    public Projeto pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Projeto) session.get(Projeto.class, id);
    }

    @Override
    public List<Projeto> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Projeto");
        return consulta.list();
    }

    @Override
    public List<Projeto> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Projeto.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<Projeto> projetos = criteria.list();

        return projetos;
    }

    @Override
    public List<Projeto> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Projeto.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<Projeto> projetos = criteria.list();

        return projetos;
    }
}
