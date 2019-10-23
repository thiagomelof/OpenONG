package dao.interfaces;

import java.util.List;
import model.DespesaItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.base.IBaseDao;

public interface IDespesaItemDAO extends IBaseDao<DespesaItem, Long> {
    
    List<DespesaItem> pesquisarTodosPorDespesa(Long idDespesa, Session session) throws HibernateException;
}
