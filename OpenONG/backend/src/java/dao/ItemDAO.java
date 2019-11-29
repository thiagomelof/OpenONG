package dao;

import dao.base.BaseDao;
import dao.interfaces.IItemDAO;
import java.io.Serializable;
import java.util.List;
import model.Item;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ItemDAO extends BaseDao<Item, Long>
        implements IItemDAO, Serializable {

    @Override
    public Item pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Item) session.get(Item.class, id);
    }

    @Override
    public List<Item> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Item order by nome asc");
        return consulta.list();
    }

    public List<Item> pesquisarTodosAtivos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Item where status =:statusHQL order by nome asc");
        consulta.setParameter("statusHQL", true);

        return consulta.list();
    }

    public List<Item> pesquisarTodosAtivosPorCategoriasDeConvenio(long idConvenio, Session session) throws HibernateException {
        Query categorias = session.createQuery("select c.categoria.id from ConvenioCategoria c where c.convenio.id=:idConvenioHQL").setParameter("idConvenioHQL", idConvenio);

        Query consulta = session.createQuery("from Item ITEM"
                + " join fetch ITEM.categoria CAT  "
                + " where CAT.id in (:idsHQL)").setParameterList("idsHQL", categorias.list());

        return consulta.list();
    }

    @Override
    public List<Item> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Item.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<Item> itens = criteria.list();

        return itens;
    }

    public boolean itemExists(long id, String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Item.class);
        criteria.add(Restrictions.eq("nome", nome));
        if (id > 0) {
            criteria.add(Restrictions.ne("id", id));
        }
        List<Item> itens = criteria.list();

        if (itens.size() > 0) {
            return true;
        }
        return false;
    }

}
