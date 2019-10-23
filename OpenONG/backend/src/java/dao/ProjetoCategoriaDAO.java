package dao;

import dao.base.BaseDao;
import dao.interfaces.IProjetoCategoriaDAO;
import java.io.Serializable;
import java.util.List;
import model.ProjetoCategoria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ProjetoCategoriaDAO extends BaseDao<ProjetoCategoria, Long>
        implements IProjetoCategoriaDAO, Serializable {

    @Override
    public ProjetoCategoria pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProjetoCategoria> pesquisarTodosPorProjeto(Long idProjeto, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
