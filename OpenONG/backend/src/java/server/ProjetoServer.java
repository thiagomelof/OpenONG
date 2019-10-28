package server;

import dao.ProjetoDAO;
import dao.base.HibernateUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Projeto;
import org.hibernate.Session;

@Path("/projeto")
public class ProjetoServer {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Projeto> getProjetos() {
        Session session = HibernateUtil.abrirSessao();
        List<Projeto> projetos = new ProjetoDAO().pesquisarTodos(session);
        session.close();
        return projetos;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Projeto getProjeto(@PathParam("id") Long id) {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Session session = HibernateUtil.abrirSessao();
        Projeto projeto = projetoDAO.pesquisarPorId(id, session);
        session.close();
        return projeto;
    }
    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer cadastrar(@FormParam("dado") String dadosJSON) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        Projeto projeto = gson.fromJson(dadosJSON, Projeto.class);
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Integer lastId = projetoDAO.salvarOuAlterar(projeto, session);
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
        Projeto projeto = gson.fromJson(dadosJSON, Projeto.class);

        if (id == projeto.getId() || projeto.getId() == null) {
            projeto.setId(id);
        } else {
            return false;
        }

        Boolean res = new ProjetoDAO().salvarOuAlterar(projeto, session);
        session.close();
        return res;
    }*/
}
