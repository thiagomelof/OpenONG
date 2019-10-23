package dao;

import dao.base.BaseDao;
import dao.interfaces.IDespesaDAO;
import java.io.Serializable;
import java.util.List;
import model.Despesa;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class DespesaDAO extends BaseDao<Despesa, Long>
        implements IDespesaDAO, Serializable {

    @Override
    public Despesa pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Despesa> pesquisarTodos(Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Despesa> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
