package dao.interfaces;

import java.util.List;
import model.ParceiroDeNegocio;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface IParceiroDeNegocioDAO extends IBaseDao<ParceiroDeNegocio, Long> {
    
    List<ParceiroDeNegocio> pesquisarTodos(Session session) throws HibernateException;
    
    List<ParceiroDeNegocio> pesquisarPorNome(String nome,Session session) throws HibernateException;
}
