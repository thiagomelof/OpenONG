package server;

import bo.CategoriaBO;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Categoria;

@Path("/categoria")
public class CategoriaServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> getCategorias() {
        return new CategoriaBO().getCategorias();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ativo")
    public List<Categoria> getCategoriasAtivas() {
        return new CategoriaBO().getCategoriasAtivas();
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Categoria getCategoria(@PathParam("id") Long id) {
        return new CategoriaBO().getCategoria(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Categoria cadastrar(String body) {
        Categoria categoria = new Gson().fromJson(body, Categoria.class);
        return new CategoriaBO().cadastrar(categoria);
    }
}
