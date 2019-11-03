package dao;

import dao.base.BaseDao;
import java.io.Serializable;
import java.util.List;
import model.ConvenioCategoria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import dao.interfaces.IConvenioCategoriaDAO;

public class ConvenioCategoriaDAO extends BaseDao<ConvenioCategoria, Long>
        implements IConvenioCategoriaDAO, Serializable {

    @Override
    public ConvenioCategoria pesquisarPorId(Long id, Session session) throws HibernateException {
        return (ConvenioCategoria) session.get(ConvenioCategoria.class, id);
    }

    @Override
    public List<ConvenioCategoria> pesquisarTodosPorConvenio(Long idConvenio, Session session) throws HibernateException {
        Query consulta = session.createQuery("from ConvenioCategoria pc join fetch pc.convenio p"
                + " where p.id =:idHQL");
        consulta.setParameter("idHQL", idConvenio);

        return consulta.list();
    }
}
