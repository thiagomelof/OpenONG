package dao;

import dao.base.BaseDao;
import dao.interfaces.ICategoriaDAO;
import java.io.Serializable;
import java.util.List;
import model.Categoria;
import model.Usuario;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class CategoriaDAO extends BaseDao<Categoria, Long>
        implements ICategoriaDAO, Serializable {    

    @Override
    public Categoria pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Categoria) session.get(Categoria.class, id);
    }

    @Override
    public List<Categoria> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Categoria");
        return consulta.list();
    }

    @Override
    public List<Categoria> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<Categoria> categorias = criteria.list();

        return categorias;
    }
}