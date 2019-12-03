package bo;

import dao.DoacaoItemDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.DoacaoItem;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class DoacaoItemBOTest {

    public DoacaoItemBOTest() {
    }

    @Test
    public void testSalvar() {
        DoacaoItem doacaoItem = addDoacaoItem();
        assertNotNull(doacaoItem.getId());
    }

    @Test
    public void testAlterar() {
        DoacaoItem doacaoItem = primeiroRegistroDoBancoDeDados();
        doacaoItem.setObservacoes("Teste Alteração");
        Session session = HibernateUtil.abrirSessao();
        new DoacaoItemDAO().salvarOuAlterar(doacaoItem, session);
        DoacaoItem doacaoItemAlterado = new DoacaoItemDAO().pesquisarPorId(doacaoItem.getId(), session);
        session.close();
        assertEquals(doacaoItem.getObservacoes(), doacaoItemAlterado.getObservacoes());
    }

    @Test
    public void testPesquisarPorId() {
        DoacaoItem doacaoItem = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        DoacaoItem doacaoItemPesquisado = new DoacaoItemDAO().pesquisarPorId(doacaoItem.getId(), session);
        session.close();
        assertNotNull(doacaoItemPesquisado);
    }

    @Test
    public void excluir() {
        DoacaoItem doacaoItem = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        new DoacaoItemDAO().excluir(doacaoItem, session);
        DoacaoItem doacaoItemExcluido = new DoacaoItemDAO().pesquisarPorId(doacaoItem.getId(), session);
        session.close();
        assertNull(doacaoItemExcluido);
    }

    @Test
    public void testPesquisarPorDoacao() {
        primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        List<DoacaoItem> doacaoItems = new DoacaoItemDAO().pesquisarTodosPorDoacao(Long.parseLong("1"), session);

        session.close();
        assertNotNull(doacaoItems);
    }

    private DoacaoItem primeiroRegistroDoBancoDeDados() {
        return novoDoacaoItem();
    }

    private static DoacaoItem addDoacaoItem() {
        DoacaoItem doacaoItem = getDoacaoItem();

        Session session = HibernateUtil.abrirSessao();
        new DoacaoItemDAO().salvarOuAlterar(doacaoItem, session);
        session.close();
        assertNotNull(doacaoItem.getId());

        return doacaoItem;
    }

    public static DoacaoItem novoDoacaoItem() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from DoacaoItem");
        consulta.setMaxResults(1);
        DoacaoItem doacaoItem = (DoacaoItem) consulta.uniqueResult();
        session.close();
        if (doacaoItem == null) {
            doacaoItem = addDoacaoItem();
        }
        return doacaoItem;
    }

    public static DoacaoItem getDoacaoItem() {
        return new DoacaoItem("abc123", DoacaoBOTest.novoDoacao(), ItemBOTest.novoItem(), 20.5, 2, new Date(), "obs");
    }    
}
