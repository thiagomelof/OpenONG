package dao;

import dao.base.BaseDao;
import dao.interfaces.IUsuarioDAO;
import java.io.Serializable;
import java.util.List;
import model.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class UsuarioDAO extends BaseDao<Usuario, Long>
        implements IUsuarioDAO, Serializable {    

    @Override
    public Usuario pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> pesquisarTodos(Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> pesquisarPorNome(String nome, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
