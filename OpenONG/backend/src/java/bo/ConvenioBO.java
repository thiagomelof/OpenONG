package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.ConvenioCategoriaDAO;
import dao.ConvenioDAO;
import dao.DespesaDAO;
import dao.DoacaoDAO;
import dao.base.HibernateUtil;
import dto.ConvenioMessage;
import dto.ConsumoConvenioMessage;
import dto.RetornoMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Convenio;
import model.ConvenioCategoria;
import model.DespesaItem;
import model.DoacaoItem;
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

    public List<Convenio> GetConveniosPorParceiroDeNegocio(long id) {
        Session session = HibernateUtil.abrirSessao();
        List<Convenio> convenios = new ConvenioDAO().pesquisarPorParceiroDeNegocio(id, session);
        session.close();
        return convenios;
    }

    public List<ConsumoConvenioMessage> GetConsumoConvenio(long id) {
        List<ConsumoConvenioMessage> msg = new ArrayList<>();

        Session session = HibernateUtil.abrirSessao();

        Convenio convenio = new ConvenioDAO().pesquisarPorId(id, session);
        List<DespesaItem> despesas = new DespesaDAO().despesasPorConvenio(id, session);
        List<DoacaoItem> doacoes = new DoacaoDAO().doacoesPorConvenio(id, session);

        for (ConvenioCategoria di : convenio.getConvenioCategoria()) {
            ConsumoConvenioMessage defaultitem = new ConsumoConvenioMessage();
            long idCategoria = di.getCategoria().getId();

            ConsumoConvenioMessage item = msg.stream().filter((categoria) -> categoria.getIdCategoria() == idCategoria).findFirst().orElseGet(() -> defaultitem);

            if (item.getIdCategoria() != null) {
                if (item.getIdCategoria() > 0) {
                    for (ConsumoConvenioMessage doacaodespesa : msg) {
                        if (doacaodespesa.getIdCategoria() == idCategoria) {
                            doacaodespesa.setPercentualAplicado(di.getPercentual());
                        }
                    }
                }
            } else {
                defaultitem.setIdCategoria(di.getCategoria().getId());
                defaultitem.setNomeCategoria(di.getCategoria().getNome());
                defaultitem.setPercentualAplicado(di.getPercentual());
                msg.add(defaultitem);
            }
        }

        for (DespesaItem di : despesas) {
            ConsumoConvenioMessage defaultitem = new ConsumoConvenioMessage();
            long idCategoria = di.getItem().getCategoria().getId();

            ConsumoConvenioMessage item = msg.stream().filter((categoria) -> categoria.getIdCategoria() == idCategoria).findFirst().orElseGet(() -> defaultitem);

            if (item.getIdCategoria() != null) {
                if (item.getIdCategoria() > 0) {
                    for (ConsumoConvenioMessage despesadespesa : msg) {
                        if (despesadespesa.getIdCategoria() == idCategoria) {
                            despesadespesa.setDespesa(despesadespesa.getDespesa() + (di.getValorUnitario() * di.getQuantidade()));
                        }
                    }
                }
            } else {
                defaultitem.setIdCategoria(di.getItem().getCategoria().getId());
                defaultitem.setNomeCategoria(di.getItem().getCategoria().getNome());
                defaultitem.setDespesa((di.getValorUnitario() * di.getQuantidade()));
                msg.add(defaultitem);
            }
        }
        session.close();

        for (DoacaoItem di : doacoes) {

            for (ConsumoConvenioMessage doacaodespesa : msg) {
                doacaodespesa.setDoacao((di.getValorUnitario() * di.getQuantidade()));

            }
        }
        for (ConsumoConvenioMessage consumo : msg) {
            if (consumo.getDespesa() > 0) {
                consumo.setPercentualUtilizado((consumo.getDespesa() * 100) / consumo.getDoacao());
            } else {
                consumo.setPercentualUtilizado(0);
            }
        }

        return msg;
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
                resetLinhasConvenio(convenio.getConvenio().getId(), session);
                for (ConvenioCategoria categoria : convenio.getCategorias()) {
                    categoria.setId(null);
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
        long id = 0;

        if (convenio.getConvenio().getId() != null) {
            id = convenio.getConvenio().getId();
        }

        if (convenio.getConvenio().getNome() == "" || convenio.getConvenio().getNome() == null || convenio.getConvenio().getNome().isEmpty()) {
            erros.add(new Erro(CodigoErro.CONVENIOAA, "Necessário informar o nome."));
        } else {
            boolean exists = new ConvenioDAO().convenioExists(id, convenio.getConvenio().getNome(), session);
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

        List<Erro> errosCategorias = validacoesCategorias(convenio.getCategorias());

        if (errosCategorias.size() > 0) {
            erros.addAll(errosCategorias);
        }

        return erros;
    }

    private List<Erro> validacoesCategorias(List<ConvenioCategoria> categorias) {
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

    private void resetLinhasConvenio(Long idDocao, Session session) {
        List<ConvenioCategoria> categorias = new ConvenioCategoriaDAO().pesquisarTodosPorConvenio(idDocao, session);
        for (ConvenioCategoria cat : categorias) {
            if (cat.getId() != null) {
                if (cat.getId() > 0) {
                    new ConvenioCategoriaDAO().excluir(cat, session);
                    cat.setId(null);
                }
            }
        }
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
