package bo;

import dao.DespesaDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import model.Despesa;
import model.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DespesaBOTest {

    public DespesaBOTest() {
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
        Usuario teste = UsuarioBOTest.novoUsuario();
        
        return new Despesa(ParceiroDeNegocioBOTest.novoParceiroDeNegocio(), true, new Date(), "teste", ConvenioBOTest.novoConvenio(), new Date(), teste);
    }
}
