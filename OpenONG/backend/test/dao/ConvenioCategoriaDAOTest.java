package dao;

import dao.base.HibernateUtil;
import model.ConvenioCategoria;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class ConvenioCategoriaDAOTest {

    @Test
    public void testSalvar() {
        ConvenioCategoria convenio = addConvenioCategoria();
        assertNotNull(convenio.getId());
    }

    @Test
    public void testAlterar() {
        ConvenioCategoria convenio = primeiroRegistroDoBancoDeDados();
        convenio.setPercentual(20.0);
        Session session = HibernateUtil.abrirSessao();
        new ConvenioCategoriaDAO().salvarOuAlterar(convenio, session);
        ConvenioCategoria convenioAlterado = new ConvenioCategoriaDAO().pesquisarPorId(convenio.getId(), session);
        session.close();
        assertEquals(convenio.getPercentual(), convenioAlterado.getPercentual());
    }

    @Test
    public void testPesquisarPorId() {
        ConvenioCategoria convenio = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        ConvenioCategoria convenioPesquisado = new ConvenioCategoriaDAO().pesquisarPorId(convenio.getId(), session);
        session.close();
        assertNotNull(convenioPesquisado);
    }

    @Test
    public void excluir() {
        ConvenioCategoria convenio = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new ConvenioCategoriaDAO().excluir(convenio, session);
        ConvenioCategoria convenioExcluido = new ConvenioCategoriaDAO().pesquisarPorId(convenio.getId(), session);
        session.close();
        assertNull(convenioExcluido);
    }

    private ConvenioCategoria primeiroRegistroDoBancoDeDados() {
        return novoConvenioCategoria();
    }

    private static ConvenioCategoria addConvenioCategoria() {
        ConvenioCategoria convenio = getConvenioCategoria();

        Session session = HibernateUtil.abrirSessao();
        new ConvenioCategoriaDAO().salvarOuAlterar(convenio, session);
        session.close();
        assertNotNull(convenio.getId());

        return convenio;
    }

    public static ConvenioCategoria novoConvenioCategoria() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from ConvenioCategoria");
        consulta.setMaxResults(1);
        ConvenioCategoria convenio = (ConvenioCategoria) consulta.uniqueResult();
        session.close();
        if (convenio == null) {
            convenio = addConvenioCategoria();
        }
        return convenio;
    }

    public static ConvenioCategoria getConvenioCategoria() {

        return new ConvenioCategoria(ConvenioDAOTest.novoConvenio(), CategoriaDAOTest.novaCategoria(), 50.0);
    }
}
