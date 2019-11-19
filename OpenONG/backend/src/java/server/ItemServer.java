package server;

import bo.ItemBO;
import com.google.gson.Gson;
import dto.RetornoMessage;
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
    public List<Item> getItems() {
        return new ItemBO().getItens();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ativo")
    public List<Item> getItemsAtivas() {
        return new ItemBO().getItensAtivos();
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
    public RetornoMessage cadastrar(String body) {
        Item item = new Gson().fromJson(body, Item.class);
        return new ItemBO().cadastrar(item);
    }
}
