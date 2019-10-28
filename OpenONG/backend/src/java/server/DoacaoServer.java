package server;

import dao.DoacaoDAO;
import dao.base.HibernateUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Doacao;
import org.hibernate.Session;

@Path("/doacao")
public class DoacaoServer {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doacao> getDoacaos() {
        Session session = HibernateUtil.abrirSessao();
        List<Doacao> doacoes = new DoacaoDAO().pesquisarTodos(session);
        session.close();
        return doacoes;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Doacao getDoacao(@PathParam("id") Long id) {
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        Session session = HibernateUtil.abrirSessao();
        Doacao doacao = doacaoDAO.pesquisarPorId(id, session);
        session.close();
        return doacao;
    }
    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer cadastrar(@FormParam("dado") String dadosJSON) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        Doacao doacao = gson.fromJson(dadosJSON, Doacao.class);
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        Integer lastId = doacaoDAO.salvarOuAlterar(doacao, session);
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
        Doacao doacao = gson.fromJson(dadosJSON, Doacao.class);

        if (id == doacao.getId() || doacao.getId() == null) {
            doacao.setId(id);
        } else {
            return false;
        }

        Boolean res = new DoacaoDAO().salvarOuAlterar(doacao, session);
        session.close();
        return res;
    }*/
}
