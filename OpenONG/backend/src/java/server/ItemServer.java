package server;

import dao.ItemDAO;
import dao.base.HibernateUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Item;
import org.hibernate.Session;

@Path("/Item")
public class ItemServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems() {
        Session session = HibernateUtil.abrirSessao();
        List<Item> itens = new ItemDAO().pesquisarTodos(session);
        session.close();
        return itens;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Item getItem(@PathParam("id") Long id) {
        ItemDAO itemDAO = new ItemDAO();
        Session session = HibernateUtil.abrirSessao();
        Item item = itemDAO.pesquisarPorId(id, session);
        session.close();
        return item;
    }
    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer cadastrar(@FormParam("dado") String dadosJSON) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        Item item = gson.fromJson(dadosJSON, Item.class);
        ItemDAO itemDAO = new ItemDAO();
        Integer lastId = itemDAO.salvarOuAlterar(item, session);
        session.close();
        return lastId;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{id}")
    public Boolean alterar(@PathParam("id") Long id, @FormParam("dado") String dadosJSON) {
        Session session = HibernateUtil.abrirSessao();
        Gson gson = new Gson();
        Item item = gson.fromJson(dadosJSON, Item.class);

        if (id == item.getId() || item.getId() == null) {
            item.setId(id);
        } else {
            return false;
        }

        Boolean res = new ItemDAO().salvarOuAlterar(item, session);
        session.close();
        return res;
    }*/
}
