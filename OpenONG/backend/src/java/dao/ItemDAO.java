package dao;

import dao.base.BaseDao;
import dao.interfaces.IItemDAO;
import java.io.Serializable;
import java.util.List;
import model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ItemDAO extends BaseDao<Item, Long>
        implements IItemDAO, Serializable{

    @Override
    public Item pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Item> pesquisarTodos(Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Item> pesquisarPorNome(String nome, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
