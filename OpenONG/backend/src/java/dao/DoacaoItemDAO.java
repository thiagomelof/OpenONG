package dao;

import dao.base.BaseDao;
import dao.interfaces.IDoacaoItemDAO;
import java.io.Serializable;
import java.util.List;
import model.DoacaoItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class DoacaoItemDAO extends BaseDao<DoacaoItem, Long>
        implements IDoacaoItemDAO, Serializable {

    @Override
    public DoacaoItem pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DoacaoItem> pesquisarTodosPorDoacao(Long idDoacao, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
