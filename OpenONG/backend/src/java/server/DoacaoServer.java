package server;

import bo.DespesaBO;
import bo.DoacaoBO;
import com.google.gson.Gson;
import dto.DespesasPorCategoriaMessage;
import dto.DoacaoMessage;
import dto.DoacoesPorPeriodoMessage;
import dto.RelatorioDoacaoParameters;
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
import model.Doacao;
import model.DoacaoItem;

@Path("/doacao")
public class DoacaoServer {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doacao> getDoacaos() {
        return new DoacaoBO().getDoacoes();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ativo")
    public List<Doacao> getDoacaosAtivas() {
        return new DoacaoBO().getDoacoesAtivos();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public DoacaoMessage getDoacao(@PathParam("id") Long id) {
        return new DoacaoBO().getDoacao(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RetornoMessage cadastrar(String body) {
        DoacaoMessage doacao = new Gson().fromJson(body, DoacaoMessage.class);
        return new DoacaoBO().cadastrar(doacao);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/relatorio")
    public List<DoacaoItem> relatorioDoacao(@QueryParam("dtinicio") String dtinicio, @QueryParam("dtfim") String dtfim, @QueryParam("parceiro") long idParceiro, @QueryParam("convenio") long idConvenio) {
        RelatorioDoacaoParameters parametros = new RelatorioDoacaoParameters();

        Date _dtInicio = new Date();
        Date _dtFim = new Date();
        try {
            _dtInicio = new SimpleDateFormat("yyyy-MM-dd").parse(dtinicio);
            _dtFim = new SimpleDateFormat("yyyy-MM-dd").parse(dtfim);
        } catch (ParseException ex) {
            Logger.getLogger(DoacaoServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        parametros.getConvenio().setId(idConvenio);
        parametros.getParceiro().setId(idParceiro);
        parametros.setDataInicio(_dtInicio);
        parametros.setDataFim(_dtFim);

        return new DoacaoBO().relatorioDeDoacao(parametros);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/porperiodo")
    public List<DoacoesPorPeriodoMessage> doacoesPorPeriodo(@QueryParam("ano") String ano) {
        return new DoacaoBO().doacoesPorPeriodo(Integer.parseInt(ano));
    }
}
