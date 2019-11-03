package dao.interfaces;

import java.util.List;
import model.Convenio;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface IConvenioDAO extends IBaseDao<Convenio, Long> {

    List<Convenio> pesquisarTodos(Session session) throws HibernateException;

    List<Convenio> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException;
    
    List<Convenio> pesquisarPorNome(String nome, Session session) throws HibernateException;
}
