package server;

import bo.ConvenioBO;
import com.google.gson.Gson;
import dto.ConvenioMessage;
import dto.RetornoMessage;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Convenio;

@Path("/convenio")
public class ConvenioServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Convenio> getConvenios() {
        return new ConvenioBO().getConvenios();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ativo")
    public List<Convenio> getConveniosAtivas() {
        return new ConvenioBO().getConveniosAtivos();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public ConvenioMessage getConvenio(@PathParam("id") Long id) {
        return new ConvenioBO().getConvenio(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RetornoMessage cadastrar(String body) {
        ConvenioMessage convenio = new Gson().fromJson(body, ConvenioMessage.class);
        return new ConvenioBO().cadastrar(convenio);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/porparceiro")
    public List<Convenio> GetConveniosPorParceiroDeNegocio(@QueryParam("id") String id) {
        return new ConvenioBO().GetConveniosPorParceiroDeNegocio(Long.parseLong(id));
    }
}
