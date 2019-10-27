package dao;

import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Categoria;
import model.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class CategoriaDAOTest {

    public CategoriaDAOTest() {
    }

    @Test
    public void testSalvar() {
        Categoria categoria = addCategoria();
        assertNotNull(categoria.getId());
    }

    @Test
    public void testAlterar() {
        Categoria categoria = primeiroRegistroDoBancoDeDados();
        categoria.setNome("Nome alterado");
        Session session = HibernateUtil.abrirSessao();
        new CategoriaDAO().salvarOuAlterar(categoria, session);
        Categoria categoriaAlterado = new CategoriaDAO().pesquisarPorId(categoria.getId(), session);
        session.close();
        assertEquals(categoria.getNome(), categoriaAlterado.getNome());
    }

    @Test
    public void testPesquisarPorId() {
        Categoria categoria = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        Categoria categoriaPesquisado = new CategoriaDAO().pesquisarPorId(categoria.getId(), session);
        session.close();
        assertNotNull(categoriaPesquisado);
    }

    /*
    @Test
    public void excluir() {
        Categoria categoria = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new CategoriaDAO().excluir(categoria, session);
        Categoria categoriaExcluido = new CategoriaDAO().pesquisarPorId(categoria.getId(), session);
        session.close();
        assertNull(categoriaExcluido);
    }*/

    @Test
    public void testPesquisarPorNome() {
        primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        List<Categoria> categorias = new CategoriaDAO().pesquisarPorNome("Material Escolar", session);

        session.close();
        assertNotNull(categorias);
    }

    private Categoria primeiroRegistroDoBancoDeDados() {
        return novaCategoria();
    }

    private static Categoria addCategoria() {
        Categoria categoria = getCategoria();

        Session session = HibernateUtil.abrirSessao();
        new CategoriaDAO().salvarOuAlterar(categoria, session);
        session.close();
        assertNotNull(categoria.getId());

        return categoria;
    }

    public static Categoria novaCategoria() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from Categoria");
        consulta.setMaxResults(1);
        Categoria categoria = (Categoria) consulta.uniqueResult();
        session.close();
        if (categoria == null) {
            categoria = addCategoria();
        }
        return categoria;
    }

    private static Categoria getCategoria() {
        Usuario usuario = UsuarioDAOTest.novoUsuario();
        return new Categoria("Material Escolar", true, "teste", new Date(), usuario);
    }
}
