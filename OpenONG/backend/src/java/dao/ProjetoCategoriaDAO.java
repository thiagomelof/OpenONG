package dao;

import dao.base.BaseDao;
import dao.interfaces.IProjetoCategoriaDAO;
import java.io.Serializable;
import java.util.List;
import model.ProjetoCategoria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ProjetoCategoriaDAO extends BaseDao<ProjetoCategoria, Long>
        implements IProjetoCategoriaDAO, Serializable {

    @Override
    public ProjetoCategoria pesquisarPorId(Long id, Session session) throws HibernateException {
        return (ProjetoCategoria) session.get(ProjetoCategoria.class, id);
    }

    @Override
    public List<ProjetoCategoria> pesquisarTodosPorProjeto(Long idProjeto, Session session) throws HibernateException {
        Query consulta = session.createQuery("from ProjetoCategoria pc join fetch pc.projeto p"
                + " where p.id =:idHQL");
        consulta.setParameter("idHQL", idProjeto);

        return consulta.list();
    }
}
