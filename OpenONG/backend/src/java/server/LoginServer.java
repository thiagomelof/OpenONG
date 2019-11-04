package server;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import dao.base.HibernateUtil;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Usuario;
import org.hibernate.Session;

@Path("/login")
public class LoginServer {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Usuario login(String body) {
        Gson gson = new Gson();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = gson.fromJson(body, Usuario.class);
        Session session = HibernateUtil.abrirSessao();
        usuario = usuarioDAO.Login(usuario.getEmail(), usuario.getSenha(), session);
        session.close();
        return usuario;
    }
    
}
