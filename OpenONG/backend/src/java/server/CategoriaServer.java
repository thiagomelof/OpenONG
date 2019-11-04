package server;

import com.google.gson.Gson;
import dao.CategoriaDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Categoria;
import org.hibernate.Session;

@Path("/categoria")
public class CategoriaServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> getCategorias() {
        Session session = HibernateUtil.abrirSessao();
        List<Categoria> categorias = new CategoriaDAO().pesquisarTodos(session);
        session.close();
        return categorias;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Categoria getCategoria(@PathParam("id") Long id) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Session session = HibernateUtil.abrirSessao();
        Categoria categoria = categoriaDAO.pesquisarPorId(id, session);
        session.close();
        return categoria;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Categoria cadastrar(String body) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        Categoria categoria = gson.fromJson(body, Categoria.class);
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoria.setDataCriacao(new Date());
        boolean retorno = categoriaDAO.salvarOuAlterar(categoria, session);
        session.close();
        if (retorno) {
            return categoria;
        }

        return null;
    }
    
    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer cadastrar(@FormParam("dado") String dadosJSON) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        Categoria categoria = gson.fromJson(dadosJSON, Categoria.class);
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Integer lastId = categoriaDAO.salvarOuAlterar(categoria, session);
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
        Categoria categoria = gson.fromJson(dadosJSON, Categoria.class);

        if (id == categoria.getId() || categoria.getId() == null) {
            categoria.setId(id);
        } else {
            return false;
        }

        Boolean res = new CategoriaDAO().salvarOuAlterar(categoria, session);
        session.close();
        return res;
    }*/
}
