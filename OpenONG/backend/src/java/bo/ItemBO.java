
package bo;

import dao.ItemDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Categoria;
import model.Item;
import model.Usuario;
import org.hibernate.Session;

public class ItemBO {
    
    public Item getItem(Long id) {
        ItemDAO itemDAO = new ItemDAO();
        Session session = HibernateUtil.abrirSessao();
        Item item = itemDAO.pesquisarPorId(id, session);

        formatarObjeto(item);

        session.close();
        return item;
    }

    public List<Item> getItens() {
        Session session = HibernateUtil.abrirSessao();
        List<Item> itens = new ItemDAO().pesquisarTodos(session);
        session.close();
        return itens;
    }
    
    public Item cadastrar(Item item) {
        Session session = HibernateUtil.abrirSessao();
        ItemDAO itemDAO = new ItemDAO();
        item.setDataCriacao(new Date());
        boolean retorno = itemDAO.salvarOuAlterar(item, session);
        session.close();
        if (retorno) {
            return item;
        }

        return null;
    }

    private void formatarObjeto(Item item) {
        if (item.getUsuarioCriacao() == null) {
            item.setUsuarioCriacao(new Usuario());
        }
        if (item.getUsuarioModificacao() == null) {
            item.setUsuarioModificacao(new Usuario());
        }        
        if (item.getCategoria()== null) {
            item.setCategoria(new Categoria());
        }
    }
    
}
