package dao.interfaces;

import java.util.List;
import model.Doacao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface IDoacaoDAO extends IBaseDao<Doacao, Long> {

    List<Doacao> pesquisarTodos(Session session) throws HibernateException;

    List<Doacao> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException;
}
