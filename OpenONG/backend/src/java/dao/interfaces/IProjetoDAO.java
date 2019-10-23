package dao.interfaces;

import java.util.List;
import model.Projeto;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface IProjetoDAO extends IBaseDao<Projeto, Long> {

    List<Projeto> pesquisarTodos(Session session) throws HibernateException;

    List<Projeto> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException;
}
