package dao;

import dao.base.BaseDao;
import dao.interfaces.IProjetoDAO;
import java.io.Serializable;
import java.util.List;
import model.Projeto;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ProjetoDAO extends BaseDao<Projeto, Long>
        implements IProjetoDAO, Serializable {

    @Override
    public Projeto pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Projeto> pesquisarTodos(Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Projeto> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
