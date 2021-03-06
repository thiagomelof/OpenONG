package bo;

import constantes.TipoItem;
import dao.ItemDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import model.Categoria;
import model.Item;
import model.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ItemBOTest {

    public ItemBOTest() {
    }

    @Test
    public void testSalvar() {
        Item item = addItem();
        assertNotNull(item.getId());
    }

    @Test
    public void testAlterar() {
        Item item = primeiroRegistroDoBancoDeDados();
        item.setNome("Arroz Parabolizado");
        Session session = HibernateUtil.abrirSessao();
        new ItemDAO().salvarOuAlterar(item, session);
        Item itemAlterado = new ItemDAO().pesquisarPorId(item.getId(), session);
        session.close();
        assertEquals(item.getNome(), itemAlterado.getNome());
    }

    @Test
    public void testPesquisarPorId() {
        Item item = primeiroRegistroDoBancoDeDados();
        Session session = HibernateUtil.abrirSessao();
        Item itemPesquisado = new ItemDAO().pesquisarPorId(item.getId(), session);
        session.close();
        assertNotNull(itemPesquisado);
    }

    private Item primeiroRegistroDoBancoDeDados() {
        return novoItem();
    }

    private static Item addItem() {
        Item item = getItem();

        Session session = HibernateUtil.abrirSessao();
        new ItemDAO().salvarOuAlterar(item, session);
        session.close();
        assertNotNull(item.getId());

        return item;
    }

    public static Item novoItem() {
        Session session = HibernateUtil.abrirSessao();
        Query consulta = session.createQuery("from Item");
        consulta.setMaxResults(1);
        Item item = (Item) consulta.uniqueResult();
        session.close();
        if (item == null) {
            item = addItem();
        }
        return item;
    }

    private static Item getItem() {
        Usuario usuario = UsuarioBOTest.novoUsuario();
        Categoria categoria = CategoriaBOTest.novaCategoria();
        return new Item("Arroz Branco", categoria, TipoItem.D, true, "teste", new Date(), usuario);
    }
}
