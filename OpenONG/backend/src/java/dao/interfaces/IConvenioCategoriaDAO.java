package dao.interfaces;
import model.ConvenioCategoria;
import dao.base.IBaseDao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;


public interface IConvenioCategoriaDAO extends IBaseDao<ConvenioCategoria, Long> {
    List<ConvenioCategoria> pesquisarTodosPorConvenio(Long idConvenio, Session session) throws HibernateException;
}

