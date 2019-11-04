package dao;

import dao.base.BaseDao;
import dao.interfaces.IUsuarioDAO;
import java.io.Serializable;
import java.util.List;
import model.Usuario;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAO extends BaseDao<Usuario, Long>
        implements IUsuarioDAO, Serializable {

    @Override
    public Usuario pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Usuario) session.get(Usuario.class, id);
    }

    @Override
    public List<Usuario> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Usuario");
        return consulta.list();
    }

    @Override
    public List<Usuario> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
        List<Usuario> usuarios = criteria.list();

        return usuarios;
    }

    //TODO: retorna um token
    public Usuario Login(String email, String senha, Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.like("email", email));
        criteria.add(Restrictions.like("senha", senha));
        List<Usuario> usuarios = criteria.list();

        if (usuarios.size() == 1) {
            return usuarios.get(0);
        }
        return new Usuario();
    }
}
