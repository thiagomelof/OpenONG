package bo;

import bo.CategoriaBO;
import dao.CategoriaDAO;
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

public class CategoriaBOTest {

    public CategoriaBOTest() {
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

        new CategoriaBO().cadastrar(categoria);
        Categoria categoriaAlterado = new CategoriaBO().getCategoria(categoria.getId());

        assertEquals(categoria.getNome(), categoriaAlterado.getNome());
    }

    @Test
    public void testPesquisarPorId() {
        Categoria categoria = primeiroRegistroDoBancoDeDados();
        Categoria categoriaPesquisado = new CategoriaBO().getCategoria(categoria.getId());
        assertNotNull(categoriaPesquisado);
    }

    private Categoria primeiroRegistroDoBancoDeDados() {
        return novaCategoria();
    }

    private static Categoria addCategoria() {
        Categoria categoria = getCategoria();
        Session session = HibernateUtil.abrirSessao();
        new CategoriaDAO().salvarOuAlterar(categoria,session);
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
        Usuario usuario = UsuarioBOTest.novoUsuario();
        return new Categoria("Material Escolar", true, "teste", new Date(), usuario);
    }
}
