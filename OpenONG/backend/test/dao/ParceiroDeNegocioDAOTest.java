package dao;

import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Endereco;
import model.ParceiroDeNegocio;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class ParceiroDeNegocioDAOTest {

    public ParceiroDeNegocioDAOTest() {
    }

    @Test
    public void testSalvar() {
        ParceiroDeNegocio parceiroDeNegocio = addParceiroDeNegocio();
        assertNotNull(parceiroDeNegocio.getId());
    }

    @Test
    public void testAlterar() {
        ParceiroDeNegocio parceiroDeNegocio = primeiroRegistroDoBancoDeDados();
        parceiroDeNegocio.setNome("Nome alterado");
        Session session = HibernateUtil.abrirSessao();
        new ParceiroDeNegocioDAO().salvarOuAlterar(parceiroDeNegocio, session);
        ParceiroDeNegocio parceiroDeNegocioAlterado = new ParceiroDeNegocioDAO().pesquisarPorId(parceiroDeNegocio.getId(), session);
        session.close();
        assertEquals(parceiroDeNegocio.getNome(), parceiroDeNegocioAlterado.getNome());
    }

    @Test
    public void testPesquisarPorId() {
        ParceiroDeNegocio parceiroDeNegocio = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        ParceiroDeNegocio parceiroDeNegocioPesquisado = new ParceiroDeNegocioDAO().pesquisarPorId(parceiroDeNegocio.getId(), session);
        session.close();
        assertNotNull(parceiroDeNegocioPesquisado);
    }
    /*
    @Test
    public void excluir() {
        ParceiroDeNegocio parceiroDeNegocio = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new ParceiroDeNegocioDAO().excluir(parceiroDeNegocio, session);
        ParceiroDeNegocio parceiroDeNegocioExcluido = new ParceiroDeNegocioDAO().pesquisarPorId(parceiroDeNegocio.getId(), session);
        session.close();
        assertNull(parceiroDeNegocioExcluido);
    }*/

    @Test
    public void testPesquisarPorNome() {
        primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        List<ParceiroDeNegocio> parceiroDeNegocios = new ParceiroDeNegocioDAO().pesquisarPorNome("Thiago", session);

        session.close();
        assertNotNull(parceiroDeNegocios);
    }

    private ParceiroDeNegocio primeiroRegistroDoBancoDeDados() {
        return novoParceiroDeNegocio();
    }

    private static ParceiroDeNegocio addParceiroDeNegocio() {
        ParceiroDeNegocio parceiroDeNegocio = getParceiroDeNegocio();

        Session session = HibernateUtil.abrirSessao();
        new ParceiroDeNegocioDAO().salvarOuAlterar(parceiroDeNegocio, session);
        session.close();
        assertNotNull(parceiroDeNegocio.getId());

        return parceiroDeNegocio;
    }

    public static ParceiroDeNegocio novoParceiroDeNegocio() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from ParceiroDeNegocio");
        consulta.setMaxResults(1);
        ParceiroDeNegocio parceiroDeNegocio = (ParceiroDeNegocio) consulta.uniqueResult();
        session.close();
        if (parceiroDeNegocio == null) {
            parceiroDeNegocio = addParceiroDeNegocio();
        }
        return parceiroDeNegocio;
    }

    public static ParceiroDeNegocio getParceiroDeNegocio() {
        return new ParceiroDeNegocio("Thiago Melo", "thiago.melo93@hotmail.com", "(48)99999999", "(48)996501990", "033.88.890-05", "", "", "100.111.1213",
                getEndereco(), new Date(), UsuarioDAOTest.novoUsuario());
    }

    public static Endereco getEndereco() {
        return new Endereco("APTO", "Servidão valdomiro josé vieira", "740", "apto 101", "Campeche", "88063-035", "Floripa", "SC", "Colégio XYZ");
    }
}
