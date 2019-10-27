package dao;

import dao.base.BaseDao;
import dao.interfaces.IProjetoCategoriaDAO;
import java.io.Serializable;
import java.util.List;
import model.ProjetoCategoria;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ProjetoCategoriaDAO extends BaseDao<ProjetoCategoria, Long>
        implements IProjetoCategoriaDAO, Serializable {

    @Override
    public ProjetoCategoria pesquisarPorId(Long id, Session session) throws HibernateException {
        return (ProjetoCategoria) session.get(ProjetoCategoria.class, id);
    }

    @Override
    public List<ProjetoCategoria> pesquisarTodosPorProjeto(Long idProjeto, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(ProjetoCategoria.class);
        criteria.add(Restrictions.like("despesa", "%" + idProjeto + "%"));
        List<ProjetoCategoria> categoriasProjeto = criteria.list();

        return categoriasProjeto;
    }
}
