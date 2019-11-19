package server;

import bo.DoacaoBO;
import com.google.gson.Gson;
import dao.DoacaoDAO;
import dao.base.HibernateUtil;
import dto.DoacaoMessage;
import dto.RetornoMessage;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Doacao;

@Path("/doacao")
public class DoacaoServer {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doacao> getDoacaos() {
        return new DoacaoBO().getDoacoes();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ativo")
    public List<Doacao> getDoacaosAtivas() {
        return new DoacaoBO().getDoacoesAtivos();
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public DoacaoMessage getDoacao(@PathParam("id") Long id) {
        return new DoacaoBO().getDoacao(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RetornoMessage cadastrar(String body) {
        DoacaoMessage doacao = new Gson().fromJson(body, DoacaoMessage.class);
        return new DoacaoBO().cadastrar(doacao);
    }
}
