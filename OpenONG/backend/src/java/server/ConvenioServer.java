package server;

import dao.ConvenioDAO;
import dao.base.HibernateUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Convenio;
import org.hibernate.Session;

@Path("/convenio")
public class ConvenioServer {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Convenio> getConvenios() {
        Session session = HibernateUtil.abrirSessao();
        List<Convenio> convenios = new ConvenioDAO().pesquisarTodos(session);
        session.close();
        return convenios;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Convenio getConvenio(@PathParam("id") Long id) {
        ConvenioDAO convenioDAO = new ConvenioDAO();
        Session session = HibernateUtil.abrirSessao();
        Convenio convenio = convenioDAO.pesquisarPorId(id, session);
        session.close();
        return convenio;
    }
    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer cadastrar(@FormParam("dado") String dadosJSON) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        Convenio convenio = gson.fromJson(dadosJSON, Convenio.class);
        ConvenioDAO convenioDAO = new ConvenioDAO();
        Integer lastId = convenioDAO.salvarOuAlterar(convenio, session);
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
        Convenio convenio = gson.fromJson(dadosJSON, Convenio.class);

        if (id == convenio.getId() || convenio.getId() == null) {
            convenio.setId(id);
        } else {
            return false;
        }

        Boolean res = new ConvenioDAO().salvarOuAlterar(convenio, session);
        session.close();
        return res;
    }*/
}
