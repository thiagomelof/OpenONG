package dao.base;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface IBaseDao<T, IDTIPO> {

    Boolean salvarOuAlterar(T entidade, Session session) throws HibernateException;

    Boolean excluir(T entidade, Session session) throws HibernateException;

    T pesquisarPorId(IDTIPO id, Session session) throws HibernateException;
}