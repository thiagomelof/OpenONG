package dao;

import dao.base.BaseDao;
import dao.interfaces.IDoacaoItemDAO;
import java.io.Serializable;
import java.util.List;
import model.DoacaoItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class DoacaoItemDAO extends BaseDao<DoacaoItem, Long>
        implements IDoacaoItemDAO, Serializable {

    @Override
    public DoacaoItem pesquisarPorId(Long id, Session session) throws HibernateException {
        return (DoacaoItem) session.get(DoacaoItem.class, id);
    }

    @Override
    public List<DoacaoItem> pesquisarTodosPorDoacao(Long idDoacao, Session session) throws HibernateException {
        Query consulta = session.createQuery("from DoacaoItem di join fetch di.doacao d"
                + " where d.id =:idHQL");
        consulta.setParameter("idHQL", idDoacao);

        return consulta.list();
    }
}
