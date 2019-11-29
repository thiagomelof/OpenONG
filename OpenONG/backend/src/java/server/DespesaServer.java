package server;

import bo.DespesaBO;
import com.google.gson.Gson;
import dto.DespesaMessage;
import dto.DespesasPorCategoriaMessage;
import dto.DespesasPorPeriodoMessage;
import dto.RelatorioDespesaParameters;
import dto.RetornoMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Despesa;
import model.DespesaItem;

@Path("/despesa")
public class DespesaServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Despesa> getDespesas() {
        return new DespesaBO().getDespesas();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ativo")
    public List<Despesa> getDespesasAtivas() {
        return new DespesaBO().getDespesasAtivas();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public DespesaMessage getDespesa(@PathParam("id") Long id) {
        return new DespesaBO().getDespesa(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RetornoMessage cadastrar(String body) {
        DespesaMessage despesa = new Gson().fromJson(body, DespesaMessage.class);
        return new DespesaBO().cadastrar(despesa);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/relatorio")
    public List<DespesaItem> relatorioDespesa(@QueryParam("dtinicio") String dtinicio, @QueryParam("dtfim") String dtfim, @QueryParam("parceiro") long idParceiro, @QueryParam("convenio") long idConvenio) {
        RelatorioDespesaParameters parametros = new RelatorioDespesaParameters();

        Date _dtInicio = new Date();
        Date _dtFim = new Date();
        try {
            _dtInicio = new SimpleDateFormat("yyyy-MM-dd").parse(dtinicio);
            _dtFim = new SimpleDateFormat("yyyy-MM-dd").parse(dtfim);
        } catch (ParseException ex) {
            Logger.getLogger(DespesaServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        parametros.getConvenio().setId(idConvenio);
        parametros.getParceiro().setId(idParceiro);
        parametros.setDataInicio(_dtInicio);
        parametros.setDataFim(_dtFim);

        return new DespesaBO().relatorioDeDespesa(parametros);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/porcategoria")
    public List<DespesasPorCategoriaMessage> DespesasAtivasPorCategoria() {
        return new DespesaBO().DespesasAtivasPorCategoria();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/porcategoriames")
    public List<DespesasPorCategoriaMessage> DespesasAtivasPorCategoriaMes(@QueryParam("mes") String mes, @QueryParam("ano") String ano) {
        return new DespesaBO().DespesasAtivasPorCategoriaMes(mes, ano);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/porperiodo")
    public List<DespesasPorPeriodoMessage> DespesasPorPeriodo(@QueryParam("ano") String ano) {
        return new DespesaBO().despesasPorPeriodo(Integer.parseInt(ano));
    }
}
