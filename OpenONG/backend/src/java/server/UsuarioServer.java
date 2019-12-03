package server;

import dao.UsuarioDAO;
import dao.base.HibernateUtil;
import java.util.List;
import javax.ws.rs.GET;
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
}
