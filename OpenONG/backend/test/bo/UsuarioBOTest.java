package bo;

import dao.UsuarioDAO;
import dao.base.HibernateUtil;
import java.util.List;
import model.Endereco;
import model.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class UsuarioBOTest {

    public UsuarioBOTest() {
    }

    @Test
    public void testSalvar() {
        Usuario usuario = addUsuario();
        assertNotNull(usuario.getId());
    }

    @Test
    public void testAlterar() {
        Usuario usuario = primeiroRegistroDoBancoDeDados();
        usuario.setNome("Nome alterado");
        Session session = HibernateUtil.abrirSessao();
        new UsuarioDAO().salvarOuAlterar(usuario, session);
        Usuario usuarioAlterado = new UsuarioDAO().pesquisarPorId(usuario.getId(), session);
        session.close();
        assertEquals(usuario.getNome(), usuarioAlterado.getNome());
    }

    @Test
    public void testPesquisarPorId() {
        Usuario usuario = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        Usuario usuarioPesquisado = new UsuarioDAO().pesquisarPorId(usuario.getId(), session);
        session.close();
        assertNotNull(usuarioPesquisado);
    }

    /*
    @Test
    public void excluir() {
        Usuario usuario = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new UsuarioDAO().excluir(usuario, session);
        Usuario usuarioExcluido = new UsuarioDAO().pesquisarPorId(usuario.getId(), session);
        session.close();
        assertNull(usuarioExcluido);
    }*/
    @Test
    public void testPesquisarPorNome() {
        primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        List<Usuario> usuarios = new UsuarioDAO().pesquisarPorNome("Thiago", session);

        session.close();
        assertNotNull(usuarios);
    }

    private Usuario primeiroRegistroDoBancoDeDados() {
        return novoUsuario();
    }

    public static Usuario novoUsuario() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from Usuario");
        consulta.setMaxResults(1);
        Usuario usuario = (Usuario) consulta.uniqueResult();
        session.close();
        if (usuario == null) {
            usuario = addUsuario();
        }
        return usuario;
    }

    private static Usuario addUsuario() {
        Usuario usuario = getUsuario();
        Session session = HibernateUtil.abrirSessao();
        new UsuarioDAO().salvarOuAlterar(usuario, session);
        session.close();
        assertNotNull(usuario.getId());

        return usuario;
    }

    public static Endereco getEndereco() {
        return new Endereco("APTO", "Servidão valdomiro josé vieira", "740", "apto 101", "Campeche", "88063-035", "Floripa", "SC", "Colégio XYZ");
    }

    public static Usuario getUsuario() {

        return new Usuario("Thiago Melo", "thiago.melo93@hotmail.com", "(48)99999999", "(48)996501990", "033.88.890-05", "abc123", "", getEndereco());
    }
}
