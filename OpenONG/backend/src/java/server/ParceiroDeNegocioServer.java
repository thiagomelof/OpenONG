package server;

import bo.ParceiroDeNegocioBO;
import com.google.gson.Gson;
import constantes.TipoParceiro;
import constantes.Status;
import dto.ParceirosPorPeriodoMessage;
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
import model.ParceiroDeNegocio;

@Path("/parceirodenegocio")
public class ParceiroDeNegocioServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParceiroDeNegocio> getParceiroDeNegocio() {
        return new ParceiroDeNegocioBO().getParceirosDeNegocio();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ativo")
    public List<ParceiroDeNegocio> getParceiroDeNegocioAtivos() {
        return new ParceiroDeNegocioBO().getParceirosDeNegocioAtivos();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public ParceiroDeNegocio getParceiroDeNegocio(@PathParam("id") Long id) {
        return new ParceiroDeNegocioBO().getParceiroDeNegocio(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RetornoMessage cadastrar(String body) {
        ParceiroDeNegocio parceirodenegocio = new Gson().fromJson(body, ParceiroDeNegocio.class);
        return new ParceiroDeNegocioBO().cadastrar(parceirodenegocio);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/tipo")
    public List<ParceiroDeNegocio> getParceirosDeNegocioPorTipoAtivos(@QueryParam("tipo") String tipo, @QueryParam("status") String status) {

        return new ParceiroDeNegocioBO().pesquisarPorTipo(TipoParceiro.valueOf(tipo), Status.valueOf(status));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/porperiodo")
    public List<ParceirosPorPeriodoMessage> getParceirosPorPeriodo(@QueryParam("tipo") String tipo, @QueryParam("status") String status) {

        return new ParceiroDeNegocioBO().pesquisarParceirosPorPeriodo(TipoParceiro.valueOf(tipo), Status.valueOf(status));
    }
}
