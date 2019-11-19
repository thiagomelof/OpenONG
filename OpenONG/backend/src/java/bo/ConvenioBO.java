package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.ConvenioCategoriaDAO;
import dao.ConvenioDAO;
import dao.base.HibernateUtil;
import dto.ConvenioMessage;
import dto.RetornoMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Convenio;
import model.ConvenioCategoria;
import model.Erro;
import model.ParceiroDeNegocio;
import model.Usuario;
import org.hibernate.Session;

public class ConvenioBO {

    public ConvenioMessage getConvenio(Long id) {
        ConvenioDAO convenioDAO = new ConvenioDAO();
        ConvenioCategoriaDAO categoriasDAO = new ConvenioCategoriaDAO();
        Session session = HibernateUtil.abrirSessao();
        Convenio convenio = convenioDAO.pesquisarPorId(id, session);
        List<ConvenioCategoria> list = categoriasDAO.pesquisarTodosPorConvenio(convenio.getId(), session);
        formatarObjeto(convenio);

        ConvenioMessage msg = new ConvenioMessage(convenio, list);

        session.close();
        return msg;
    }

    public List<Convenio> getConvenios() {
        Session session = HibernateUtil.abrirSessao();
        List<Convenio> convenios = new ConvenioDAO().pesquisarTodos(session);
        session.close();
        return convenios;
    }

    public List<Convenio> getConveniosAtivos() {
        Session session = HibernateUtil.abrirSessao();
        List<Convenio> convenios = new ConvenioDAO().pesquisarTodosAtivos(session);
        session.close();
        return convenios;
    }

    public RetornoMessage cadastrar(ConvenioMessage convenio) {
        RetornoMessage msg = new RetornoMessage();
        Session session = HibernateUtil.abrirSessao();

        List<Erro> erros = validacoes(convenio, session);

        if (erros.size() > 0) {
            msg.getErros().addAll(erros);
        } else {

            ConvenioDAO convenioDAO = new ConvenioDAO();

            if (convenio.getConvenio().getDataCriacao() == null) {
                convenio.getConvenio().setDataCriacao(new Date());
            } else {
                convenio.getConvenio().setDataModificacao(new Date());
            }

            boolean retorno = convenioDAO.salvarOuAlterar(convenio.getConvenio(), session);

            if (!retorno) {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir o convênio no banco de dados"));
            }

            if (retorno) {
                for (ConvenioCategoria categoria : convenio.getCategorias()) {
                    categoria.getConvenio().setId(convenio.getConvenio().getId());
                    retorno = new ConvenioCategoriaDAO().salvarOuAlterar(categoria, session);
                }
            }

            if (retorno) {
                msg.getResultado().setId(convenio.getConvenio().getId());
                msg.getResultado().setTipoRegistro(TipoRegistro.CONVENIO);
            } else {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir as categorias do convênio no banco de dados"));
            }
        }
        session.close();
        return msg;
    }

    private List<Erro> validacoes(ConvenioMessage convenio, Session session) {
        List<Erro> erros = new ArrayList<>();
        if (convenio.getConvenio().getNome() == "" || convenio.getConvenio().getNome() == null || convenio.getConvenio().getNome().isEmpty()) {
            erros.add(new Erro(CodigoErro.CONVENIOAA, "Necessário informar o nome."));
        } else {
            boolean exists = new ConvenioDAO().convenioExists(convenio.getConvenio().getNome(), session);
            if (exists) {
                erros.add(new Erro(CodigoErro.CONVENIOAB, "Este convênio já existe."));
            }
        }

        if (convenio.getConvenio().getParceiroDeNegocio().getId() == null || convenio.getConvenio().getParceiroDeNegocio().getId() == -1) {
            erros.add(new Erro(CodigoErro.CONVENIOAC, "Necessário informar um doador válido."));
        }

        if (convenio.getConvenio().getValidoDe() == null) {
            erros.add(new Erro(CodigoErro.CONVENIOAD, "Necessário informar uma data inicial de validade."));
        }
        
        if (convenio.getConvenio().getValidoAte() == null) {
            erros.add(new Erro(CodigoErro.CONVENIOAE, "Necessário informar uma data final de validade."));
        }

        List<Erro> errosCategorias = validacoesCategorias(convenio.getCategorias(), session);

        if (errosCategorias.size() > 0) {
            erros.addAll(errosCategorias);
        }

        return erros;
    }

    private List<Erro> validacoesCategorias(List<ConvenioCategoria> categorias, Session session) {
        List<Erro> erros = new ArrayList<>();
        if (categorias.size() == 0) {
            erros.add(new Erro(CodigoErro.CONVENIOCATEGORIAAA, "Necessário informar ao menos uma categoria."));
        } else {
            double percentual = 0;
            for (int i = 0; i < categorias.size(); i++) {
                ConvenioCategoria cat = categorias.get(i);
                percentual += cat.getPercentual();
            }
            if (percentual != 100) {
                erros.add(new Erro(CodigoErro.CONVENIOCATEGORIAAB, "A soma do percentual de rateio das categorias não é válido. (" + percentual + "%)"));
            }
        }
        return erros;
    }

    private void formatarObjeto(Convenio convenio) {
        if (convenio.getUsuarioCriacao() == null) {
            convenio.setUsuarioCriacao(new Usuario());
        }
        if (convenio.getUsuarioModificacao() == null) {
            convenio.setUsuarioModificacao(new Usuario());
        }
        if (convenio.getParceiroDeNegocio() == null) {
            convenio.setParceiroDeNegocio(new ParceiroDeNegocio());
        }
    }
}
