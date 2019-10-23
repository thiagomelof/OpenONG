package dao.interfaces;
import model.ProjetoCategoria;
import dao.base.IBaseDao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;


public interface IProjetoCategoriaDAO extends IBaseDao<ProjetoCategoria, Long> {
    List<ProjetoCategoria> pesquisarTodosPorProjeto(Long idProjeto, Session session) throws HibernateException;
}

