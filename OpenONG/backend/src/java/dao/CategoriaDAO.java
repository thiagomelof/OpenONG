package dao;

import dao.base.BaseDao;
import dao.interfaces.ICategoriaDAO;
import java.io.Serializable;
import java.util.List;
import model.Categoria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class CategoriaDAO extends BaseDao<Categoria, Long>
        implements ICategoriaDAO, Serializable {    

    @Override
    public Categoria pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Categoria> pesquisarTodos(Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Categoria> pesquisarPorNome(String nome, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
