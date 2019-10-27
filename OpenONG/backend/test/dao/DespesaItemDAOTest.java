package dao;

import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.DespesaItem;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class DespesaItemDAOTest {

    public DespesaItemDAOTest() {
    }

    @Test
    public void testSalvar() {
        DespesaItem despesaItem = addDespesaItem();
        assertNotNull(despesaItem.getId());
    }

    @Test
    public void testAlterar() {
        DespesaItem despesaItem = primeiroRegistroDoBancoDeDados();
        despesaItem.setObservacoes("Teste Alteração");
        Session session = HibernateUtil.abrirSessao();
        new DespesaItemDAO().salvarOuAlterar(despesaItem, session);
        DespesaItem despesaItemAlterado = new DespesaItemDAO().pesquisarPorId(despesaItem.getId(), session);
        session.close();
        assertEquals(despesaItem.getObservacoes(), despesaItemAlterado.getObservacoes());
    }

    @Test
    public void testPesquisarPorId() {
        DespesaItem despesaItem = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        DespesaItem despesaItemPesquisado = new DespesaItemDAO().pesquisarPorId(despesaItem.getId(), session);
        session.close();
        assertNotNull(despesaItemPesquisado);
    }
    
    @Test
    public void excluir() {
        DespesaItem despesaItem = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new DespesaItemDAO().excluir(despesaItem, session);
        DespesaItem despesaItemExcluido = new DespesaItemDAO().pesquisarPorId(despesaItem.getId(), session);
        session.close();
        assertNull(despesaItemExcluido);
    }

    @Test
    public void testPesquisarPorDespesa() {
        primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        List<DespesaItem> despesaItems = new DespesaItemDAO().pesquisarTodosPorDespesa(Long.parseLong("1"), session);

        session.close();
        assertNotNull(despesaItems);
    }

    private DespesaItem primeiroRegistroDoBancoDeDados() {
        return novoDespesaItem();
    }

    private static DespesaItem addDespesaItem() {
        DespesaItem despesaItem = getDespesaItem();

        Session session = HibernateUtil.abrirSessao();
        new DespesaItemDAO().salvarOuAlterar(despesaItem, session);
        session.close();
        assertNotNull(despesaItem.getId());

        return despesaItem;
    }

    public static DespesaItem novoDespesaItem() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from DespesaItem");
        consulta.setMaxResults(1);
        DespesaItem despesaItem = (DespesaItem) consulta.uniqueResult();
        session.close();
        if (despesaItem == null) {
            despesaItem = addDespesaItem();
        }
        return despesaItem;
    }

    public static DespesaItem getDespesaItem() {
        return new DespesaItem(1, DespesaDAOTest.novoDespesa(), ItemDAOTest.novoItem(), 20.5, 2, new Date(), "obs");
    }
}
