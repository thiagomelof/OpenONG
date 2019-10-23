package dao.interfaces;

import java.util.List;
import model.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface IUsuarioDAO extends IBaseDao<Usuario, Long> {
    
    List<Usuario> pesquisarTodos(Session session) throws HibernateException;
    
    List<Usuario> pesquisarPorNome(String nome,Session session) throws HibernateException;
}
