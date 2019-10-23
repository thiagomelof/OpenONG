package dao.interfaces;

import model.DoacaoItem;
import dao.base.IBaseDao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface IDoacaoItemDAO extends IBaseDao<DoacaoItem, Long> {
    List<DoacaoItem> pesquisarTodosPorDoacao(Long idDoacao, Session session) throws HibernateException;
}
