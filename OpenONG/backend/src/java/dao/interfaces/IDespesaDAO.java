package dao.interfaces;

import java.util.List;
import model.Despesa;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface IDespesaDAO extends IBaseDao<Despesa, Long> {

    List<Despesa> pesquisarTodos(Session session) throws HibernateException;

    List<Despesa> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException;
    
}
