package bo;
import dao.CategoriaDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Categoria;
import model.Usuario;
import org.hibernate.Session;

public class CategoriaBO {

    public Categoria getCategoria(Long id) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Session session = HibernateUtil.abrirSessao();
        Categoria categoria = categoriaDAO.pesquisarPorId(id, session);

        formatarObjeto(categoria);

        session.close();
        return categoria;
    }

    public List<Categoria> getCategorias() {
        Session session = HibernateUtil.abrirSessao();
        List<Categoria> categorias = new CategoriaDAO().pesquisarTodos(session);
        session.close();
        return categorias;
    }
    
    public List<Categoria> getCategoriasAtivas() {
        Session session = HibernateUtil.abrirSessao();
        List<Categoria> categorias = new CategoriaDAO().pesquisarTodosAtivos(session);
        session.close();
        return categorias;
    }
    
    public Categoria cadastrar(Categoria categoria) {
        Session session = HibernateUtil.abrirSessao();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoria.setDataCriacao(new Date());
        boolean retorno = categoriaDAO.salvarOuAlterar(categoria, session);
        session.close();
        if (retorno) {
            return categoria;
        }

        return null;
    }

    private void formatarObjeto(Categoria categoria) {
        if (categoria.getUsuarioCriacao() == null) {
            categoria.setUsuarioCriacao(new Usuario());
        }
        if (categoria.getUsuarioModificacao() == null) {
            categoria.setUsuarioModificacao(new Usuario());
        }
    }

}
