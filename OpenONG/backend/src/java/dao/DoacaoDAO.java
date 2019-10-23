package dao;

import dao.base.BaseDao;
import dao.interfaces.IDoacaoDAO;
import java.io.Serializable;
import java.util.List;
import model.Doacao;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class DoacaoDAO extends BaseDao<Doacao, Long>
        implements IDoacaoDAO, Serializable {

    @Override
    public Doacao pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Doacao> pesquisarTodos(Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Doacao> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
