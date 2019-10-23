package dao.interfaces;

import java.util.List;
import model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface IItemDAO extends IBaseDao<Item, Long> {

    List<Item> pesquisarTodos(Session session) throws HibernateException;

    List<Item> pesquisarPorNome(String nome, Session session) throws HibernateException;
}
