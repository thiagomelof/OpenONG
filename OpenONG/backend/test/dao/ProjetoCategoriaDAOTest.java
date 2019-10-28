package dao;

import dao.base.HibernateUtil;
import model.ProjetoCategoria;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class ProjetoCategoriaDAOTest {

    @Test
    public void testSalvar() {
        ProjetoCategoria projeto = addProjetoCategoria();
        assertNotNull(projeto.getId());
    }

    @Test
    public void testAlterar() {
        ProjetoCategoria projeto = primeiroRegistroDoBancoDeDados();
        projeto.setPercentual(20.0);
        Session session = HibernateUtil.abrirSessao();
        new ProjetoCategoriaDAO().salvarOuAlterar(projeto, session);
        ProjetoCategoria projetoAlterado = new ProjetoCategoriaDAO().pesquisarPorId(projeto.getId(), session);
        session.close();
        assertEquals(projeto.getPercentual(), projetoAlterado.getPercentual());
    }

    @Test
    public void testPesquisarPorId() {
        ProjetoCategoria projeto = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        ProjetoCategoria projetoPesquisado = new ProjetoCategoriaDAO().pesquisarPorId(projeto.getId(), session);
        session.close();
        assertNotNull(projetoPesquisado);
    }

    @Test
    public void excluir() {
        ProjetoCategoria projeto = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new ProjetoCategoriaDAO().excluir(projeto, session);
        ProjetoCategoria projetoExcluido = new ProjetoCategoriaDAO().pesquisarPorId(projeto.getId(), session);
        session.close();
        assertNull(projetoExcluido);
    }

    private ProjetoCategoria primeiroRegistroDoBancoDeDados() {
        return novoProjetoCategoria();
    }

    private static ProjetoCategoria addProjetoCategoria() {
        ProjetoCategoria projeto = getProjetoCategoria();

        Session session = HibernateUtil.abrirSessao();
        new ProjetoCategoriaDAO().salvarOuAlterar(projeto, session);
        session.close();
        assertNotNull(projeto.getId());

        return projeto;
    }

    public static ProjetoCategoria novoProjetoCategoria() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from ProjetoCategoria");
        consulta.setMaxResults(1);
        ProjetoCategoria projeto = (ProjetoCategoria) consulta.uniqueResult();
        session.close();
        if (projeto == null) {
            projeto = addProjetoCategoria();
        }
        return projeto;
    }

    public static ProjetoCategoria getProjetoCategoria() {

        return new ProjetoCategoria(ProjetoDAOTest.novoProjeto(), CategoriaDAOTest.novaCategoria(), 50.0);
    }
}
