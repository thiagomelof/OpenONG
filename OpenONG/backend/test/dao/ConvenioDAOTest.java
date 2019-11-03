package dao;

import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Convenio;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ConvenioDAOTest {

    public ConvenioDAOTest() {
    }

    @Test
    public void testSalvar() {
        Convenio convenio = addConvenio();
        assertNotNull(convenio.getId());
    }

    @Test
    public void testAlterar() {
        Convenio convenio = primeiroRegistroDoBancoDeDados();
        convenio.setNome("GOV 2019/02");
        Session session = HibernateUtil.abrirSessao();
        new ConvenioDAO().salvarOuAlterar(convenio, session);
        Convenio convenioAlterado = new ConvenioDAO().pesquisarPorId(convenio.getId(), session);
        session.close();
        assertEquals(convenio.getNome(), convenioAlterado.getNome());
    }

    @Test
    public void testPesquisarPorId() {
        Convenio convenio = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        Convenio convenioPesquisado = new ConvenioDAO().pesquisarPorId(convenio.getId(), session);
        session.close();
        assertNotNull(convenioPesquisado);
    }
    /*
    @Test
    public void excluir() {
        Convenio convenio = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new ConvenioDAO().excluir(convenio, session);
        Convenio convenioExcluido = new ConvenioDAO().pesquisarPorId(convenio.getId(), session);
        session.close();
        assertNull(convenioExcluido);
    }*/

    @Test
    public void testPesquisarPorNome() {
        primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        List<Convenio> convenios = new ConvenioDAO().pesquisarPorNome("Governo 2019/01", session);

        session.close();
        assertNotNull(convenios);
    }

    private Convenio primeiroRegistroDoBancoDeDados() {
        return novoConvenio();
    }

    private static Convenio addConvenio() {
        Convenio convenio = getConvenio();

        Session session = HibernateUtil.abrirSessao();
        new ConvenioDAO().salvarOuAlterar(convenio, session);
        session.close();
        assertNotNull(convenio.getId());

        return convenio;
    }

    public static Convenio novoConvenio() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from Convenio");
        consulta.setMaxResults(1);
        Convenio convenio = (Convenio) consulta.uniqueResult();
        session.close();
        if (convenio == null) {
            convenio = addConvenio();
        }
        return convenio;
    }

    public static Convenio getConvenio() {
        return new Convenio("Governo 2019/01", ParceiroDeNegocioDAOTest.novoParceiroDeNegocio(),
                true, "obs", new Date(), new Date(), new Date(), UsuarioDAOTest.novoUsuario());
    }
}
