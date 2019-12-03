package dao.interfaces;

import java.util.List;
import model.Categoria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface ICategoriaDAO extends IBaseDao<Categoria, Long> {
    
    List<Categoria> pesquisarTodos(Session session) throws HibernateException;
}
