package dao;

import dao.base.BaseDao;
import dao.interfaces.IDespesaDAO;
import dao.interfaces.IDespesaItemDAO;
import java.io.Serializable;
import java.util.List;
import model.Despesa;
import model.DespesaItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class DespesaItemDAO extends BaseDao<DespesaItem, Long>
        implements IDespesaItemDAO, Serializable {    

    @Override
    public DespesaItem pesquisarPorId(Long id, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DespesaItem> pesquisarTodosPorDespesa(Long idDespesa, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
