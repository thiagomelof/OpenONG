package dao;

import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Despesa;
import model.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class DespesaDAOTest {

    public DespesaDAOTest() {
    }

    @Test
    public void testSalvar() {
        Despesa despesa = addDespesa();
        assertNotNull(despesa.getId());
    }

    @Test
    public void testAlterar() {
        Despesa despesa = primeiroRegistroDoBancoDeDados();
        despesa.setObservacoes("Teste Alteração");
        Session session = HibernateUtil.abrirSessao();
        new DespesaDAO().salvarOuAlterar(despesa, session);
        Despesa despesaAlterado = new DespesaDAO().pesquisarPorId(despesa.getId(), session);
        session.close();
        assertEquals(despesa.getObservacoes(), despesaAlterado.getObservacoes());
    }

    @Test
    public void testPesquisarPorId() {
        Despesa despesa = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        Despesa despesaPesquisado = new DespesaDAO().pesquisarPorId(despesa.getId(), session);
        session.close();
        assertNotNull(despesaPesquisado);
    }

    /*
    @Test
    public void excluir() {
        Despesa despesa = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new DespesaDAO().excluir(despesa, session);
        Despesa despesaExcluido = new DespesaDAO().pesquisarPorId(despesa.getId(), session);
        session.close();
        assertNull(despesaExcluido);
    }*/

    @Test
    public void testPesquisarPorNome() {
        primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        List<Despesa> despesas = new DespesaDAO().pesquisarPorNomeDoParceiroDeNegocio("Thiago", session);

        session.close();
        assertNotNull(despesas);
    }

    private Despesa primeiroRegistroDoBancoDeDados() {
        return novoDespesa();
    }

    private static Despesa addDespesa() {
        Despesa despesa = getDespesa();

        Session session = HibernateUtil.abrirSessao();
        new DespesaDAO().salvarOuAlterar(despesa, session);
        session.close();
        assertNotNull(despesa.getId());

        return despesa;
    }

    public static Despesa novoDespesa() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from Despesa");
        consulta.setMaxResults(1);
        Despesa despesa = (Despesa) consulta.uniqueResult();
        session.close();
        if (despesa == null) {
            despesa = addDespesa();
        }
        return despesa;
    }

    public static Despesa getDespesa() {
        Usuario teste = UsuarioDAOTest.novoUsuario();
        
        return new Despesa(ParceiroDeNegocioDAOTest.novoParceiroDeNegocio(), true, new Date(), "teste", ConvenioDAOTest.novoConvenio(), new Date(), teste);
    }
}
