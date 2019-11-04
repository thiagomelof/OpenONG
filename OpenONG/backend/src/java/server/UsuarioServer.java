package server;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import dao.base.HibernateUtil;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Usuario;
import org.hibernate.Session;

@Path("/usuario")
public class UsuarioServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getUsuarios() {
        Session session = HibernateUtil.abrirSessao();
        List<Usuario> usuarios = new UsuarioDAO().pesquisarTodos(session);
        session.close();
        return usuarios;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Usuario getUsuario(@PathParam("id") Long id) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Session session = HibernateUtil.abrirSessao();
        Usuario usuario = usuarioDAO.pesquisarPorId(id, session);
        session.close();
        return usuario;
    }
    
    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer cadastrar(@FormParam("dado") String dadosJSON) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        Usuario usuario = gson.fromJson(dadosJSON, Usuario.class);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Integer lastId = usuarioDAO.salvarOuAlterar(usuario, session);
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
        Usuario usuario = gson.fromJson(dadosJSON, Usuario.class);

        if (id == usuario.getId() || usuario.getId() == null) {
            usuario.setId(id);
        } else {
            return false;
        }

        Boolean res = new UsuarioDAO().salvarOuAlterar(usuario, session);
        session.close();
        return res;
    }*/
}
