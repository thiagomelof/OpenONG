package server;

import bo.ItemBO;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Item;

@Path("/item")
public class ItemServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItens() {
        return new ItemBO().getItens();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Item getItem(@PathParam("id") Long id) {
        return new ItemBO().getItem(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Item cadastrar(String body) {
        Item item = new Gson().fromJson(body, Item.class);
        return new ItemBO().cadastrar(item);
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
