package dao;

import dao.base.BaseDao;
import dao.interfaces.IItemDAO;
import java.io.Serializable;
import java.util.List;
import model.Item;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ItemDAO extends BaseDao<Item, Long>
        implements IItemDAO, Serializable{

    @Override
    public Item pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Item) session.get(Item.class, id);
    }

    @Override
    public List<Item> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Item");
        return consulta.list();
    }
    
    public List<Item> pesquisarTodosAtivos(Session session) throws HibernateException {        
        Query consulta = session.createQuery("from Item where status =:statusHQL");
        consulta.setParameter("statusHQL", true);

        return consulta.list();
    }

    @Override
    public List<Item> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Item.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<Item> itens = criteria.list();

        return itens;
    }
    public boolean itemExists(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Item.class);
        criteria.add(Restrictions.eq("nome", nome));
        List<Item> itens = criteria.list();

        if (itens.size() > 0) {
            return true;
        }
        return false;
    }
    
}
