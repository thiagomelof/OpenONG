package server;

import dao.ParceiroDeNegocioDAO;
import dao.base.HibernateUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.ParceiroDeNegocio;
import org.hibernate.Session;

@Path("/parceirodenegocio")
public class ParceiroDeNegocioServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParceiroDeNegocio> getParceiroDeNegocios() {
        Session session = HibernateUtil.abrirSessao();
        List<ParceiroDeNegocio> parceirosDeNegocio = new ParceiroDeNegocioDAO().pesquisarTodos(session);
        session.close();
        return parceirosDeNegocio;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public ParceiroDeNegocio getParceiroDeNegocio(@PathParam("id") Long id) {
        ParceiroDeNegocioDAO parceiroDeNegocioDAO = new ParceiroDeNegocioDAO();
        Session session = HibernateUtil.abrirSessao();
        ParceiroDeNegocio parceiroDeNegocio = parceiroDeNegocioDAO.pesquisarPorId(id, session);
        session.close();
        return parceiroDeNegocio;
    }
    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer cadastrar(@FormParam("dado") String dadosJSON) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        ParceiroDeNegocio parceiroDeNegocio = gson.fromJson(dadosJSON, ParceiroDeNegocio.class);
        ParceiroDeNegocioDAO parceiroDeNegocioDAO = new ParceiroDeNegocioDAO();
        Integer lastId = parceiroDeNegocioDAO.salvarOuAlterar(parceiroDeNegocio, session);
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
        ParceiroDeNegocio parceiroDeNegocio = gson.fromJson(dadosJSON, ParceiroDeNegocio.class);

        if (id == parceiroDeNegocio.getId() || parceiroDeNegocio.getId() == null) {
            parceiroDeNegocio.setId(id);
        } else {
            return false;
        }

        Boolean res = new ParceiroDeNegocioDAO().salvarOuAlterar(parceiroDeNegocio, session);
        session.close();
        return res;
    }*/
}
