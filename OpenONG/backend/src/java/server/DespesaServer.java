package server;

import dao.DespesaDAO;
import dao.base.HibernateUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Despesa;
import org.hibernate.Session;

@Path("/despesa")
public class DespesaServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Despesa> getDespesas() {
        Session session = HibernateUtil.abrirSessao();
        List<Despesa> despesas = new DespesaDAO().pesquisarTodos(session);
        session.close();
        return despesas;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Despesa getDespesa(@PathParam("id") Long id) {
        DespesaDAO despesaDAO = new DespesaDAO();
        Session session = HibernateUtil.abrirSessao();
        Despesa despesa = despesaDAO.pesquisarPorId(id, session);
        session.close();
        return despesa;
    }
    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer cadastrar(@FormParam("dado") String dadosJSON) {
        Gson gson = new Gson();
        Session session = HibernateUtil.abrirSessao();
        Despesa despesa = gson.fromJson(dadosJSON, Despesa.class);
        DespesaDAO despesaDAO = new DespesaDAO();
        Integer lastId = despesaDAO.salvarOuAlterar(despesa, session);
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
        Despesa despesa = gson.fromJson(dadosJSON, Despesa.class);

        if (id == despesa.getId() || despesa.getId() == null) {
            despesa.setId(id);
        } else {
            return false;
        }

        Boolean res = new DespesaDAO().salvarOuAlterar(despesa, session);
        session.close();
        return res;
    }*/
}
