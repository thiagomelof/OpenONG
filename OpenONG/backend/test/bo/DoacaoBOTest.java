package bo;

import dao.DoacaoDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Doacao;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class DoacaoBOTest {

    public DoacaoBOTest() {
    }

    @Test
    public void testSalvar() {
        Doacao doacao = addDoacao();
        assertNotNull(doacao.getId());
    }

    @Test
    public void testAlterar() {
        Doacao doacao = primeiroRegistroDoBancoDeDados();
        doacao.setObservacoes("Teste Alteração");
        Session session = HibernateUtil.abrirSessao();
        new DoacaoDAO().salvarOuAlterar(doacao, session);
        Doacao doacaoAlterado = new DoacaoDAO().pesquisarPorId(doacao.getId(), session);
        session.close();
        assertEquals(doacao.getObservacoes(), doacaoAlterado.getObservacoes());
    }

    @Test
    public void testPesquisarPorId() {
        Doacao doacao = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        Doacao doacaoPesquisado = new DoacaoDAO().pesquisarPorId(doacao.getId(), session);
        session.close();
        assertNotNull(doacaoPesquisado);
    }

    private Doacao primeiroRegistroDoBancoDeDados() {
        return novoDoacao();
    }

    private static Doacao addDoacao() {
        Doacao doacao = getDoacao();

        Session session = HibernateUtil.abrirSessao();
        new DoacaoDAO().salvarOuAlterar(doacao, session);
        session.close();
        assertNotNull(doacao.getId());

        return doacao;
    }

    public static Doacao novoDoacao() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from Doacao");
        consulta.setMaxResults(1);
        Doacao doacao = (Doacao) consulta.uniqueResult();
        session.close();
        if (doacao == null) {
            doacao = addDoacao();
        }
        return doacao;
    }

    public static Doacao getDoacao() {
        return new Doacao(ParceiroDeNegocioBOTest.novoParceiroDeNegocio(), true, new Date(), "teste",
                ConvenioBOTest.novoConvenio(), new Date(), UsuarioBOTest.novoUsuario());
    }
}
