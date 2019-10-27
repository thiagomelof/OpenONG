package dao;

import dao.base.BaseDao;
import dao.interfaces.IDespesaItemDAO;
import java.io.Serializable;
import java.util.List;
import model.DespesaItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class DespesaItemDAO extends BaseDao<DespesaItem, Long>
        implements IDespesaItemDAO, Serializable {

    @Override
    public DespesaItem pesquisarPorId(Long id, Session session) throws HibernateException {
        return (DespesaItem) session.get(DespesaItem.class, id);
    }

    @Override
    public List<DespesaItem> pesquisarTodosPorDespesa(Long idDespesa, Session session) throws HibernateException {
        Query consulta = session.createQuery("from DespesaItem di join fetch di.despesa d"
                + " where d.id =:idHQL");
        consulta.setParameter("idHQL", idDespesa);

        return consulta.list();
    }
}
