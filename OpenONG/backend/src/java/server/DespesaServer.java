package server;

import bo.DespesaBO;
import com.google.gson.Gson;
import dto.DespesaMessage;
import dto.RetornoMessage;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Despesa;

@Path("/despesa")
public class DespesaServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Despesa> getDespesas() {
        return new DespesaBO().getDoacoes();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ativo")
    public List<Despesa> getDespesasAtivas() {
        return new DespesaBO().getDoacoesAtivos();
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public DespesaMessage getDespesa(@PathParam("id") Long id) {
        return new DespesaBO().getDespesa(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RetornoMessage cadastrar(String body) {
        DespesaMessage despesa = new Gson().fromJson(body, DespesaMessage.class);
        return new DespesaBO().cadastrar(despesa);
    }
}
