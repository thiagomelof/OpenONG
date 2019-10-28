package dao;

import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Projeto;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ProjetoDAOTest {

    public ProjetoDAOTest() {
    }

    @Test
    public void testSalvar() {
        Projeto projeto = addProjeto();
        assertNotNull(projeto.getId());
    }

    @Test
    public void testAlterar() {
        Projeto projeto = primeiroRegistroDoBancoDeDados();
        projeto.setNome("GOV 2019/02");
        Session session = HibernateUtil.abrirSessao();
        new ProjetoDAO().salvarOuAlterar(projeto, session);
        Projeto projetoAlterado = new ProjetoDAO().pesquisarPorId(projeto.getId(), session);
        session.close();
        assertEquals(projeto.getNome(), projetoAlterado.getNome());
    }

    @Test
    public void testPesquisarPorId() {
        Projeto projeto = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        Projeto projetoPesquisado = new ProjetoDAO().pesquisarPorId(projeto.getId(), session);
        session.close();
        assertNotNull(projetoPesquisado);
    }
    /*
    @Test
    public void excluir() {
        Projeto projeto = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new ProjetoDAO().excluir(projeto, session);
        Projeto projetoExcluido = new ProjetoDAO().pesquisarPorId(projeto.getId(), session);
        session.close();
        assertNull(projetoExcluido);
    }*/

    @Test
    public void testPesquisarPorNome() {
        primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        List<Projeto> projetos = new ProjetoDAO().pesquisarPorNome("Governo 2019/01", session);

        session.close();
        assertNotNull(projetos);
    }

    private Projeto primeiroRegistroDoBancoDeDados() {
        return novoProjeto();
    }

    private static Projeto addProjeto() {
        Projeto projeto = getProjeto();

        Session session = HibernateUtil.abrirSessao();
        new ProjetoDAO().salvarOuAlterar(projeto, session);
        session.close();
        assertNotNull(projeto.getId());

        return projeto;
    }

    public static Projeto novoProjeto() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from Projeto");
        consulta.setMaxResults(1);
        Projeto projeto = (Projeto) consulta.uniqueResult();
        session.close();
        if (projeto == null) {
            projeto = addProjeto();
        }
        return projeto;
    }

    public static Projeto getProjeto() {
        return new Projeto("Governo 2019/01", ParceiroDeNegocioDAOTest.novoParceiroDeNegocio(),
                true, "obs", new Date(), new Date(), new Date(), UsuarioDAOTest.novoUsuario());
    }
}
