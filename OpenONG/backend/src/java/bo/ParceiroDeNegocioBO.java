package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.ParceiroDeNegocioDAO;
import dao.base.HibernateUtil;
import dto.RetornoMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Endereco;
import model.Erro;
import model.ParceiroDeNegocio;
import model.Usuario;
import org.hibernate.Session;

public class ParceiroDeNegocioBO {

    public ParceiroDeNegocio getParceiroDeNegocio(Long id) {
        ParceiroDeNegocioDAO parceirodenegocioDAO = new ParceiroDeNegocioDAO();
        Session session = HibernateUtil.abrirSessao();
        ParceiroDeNegocio parceirodenegocio = parceirodenegocioDAO.pesquisarPorId(id, session);

        formatarObjeto(parceirodenegocio);

        session.close();
        return parceirodenegocio;
    }

    public List<ParceiroDeNegocio> getParceirosDeNegocio() {
        Session session = HibernateUtil.abrirSessao();
        List<ParceiroDeNegocio> parceirosdenegocio = new ParceiroDeNegocioDAO().pesquisarTodos(session);
        session.close();
        return parceirosdenegocio;
    }

    public List<ParceiroDeNegocio> getParceirosDeNegocioAtivos() {
        Session session = HibernateUtil.abrirSessao();
        List<ParceiroDeNegocio> parceirosdenegocio = new ParceiroDeNegocioDAO().pesquisarTodosAtivos(session);
        session.close();
        return parceirosdenegocio;
    }

    public RetornoMessage cadastrar(ParceiroDeNegocio parceirodenegocio) {
        Session session = HibernateUtil.abrirSessao();
        RetornoMessage msg = new RetornoMessage();

        List<Erro> erros = validacoes(parceirodenegocio, session);

        if (erros.size() > 0) {
            msg.getErros().addAll(erros);
        } else {
            ParceiroDeNegocioDAO parceirodenegocioDAO = new ParceiroDeNegocioDAO();

            if (parceirodenegocio.getDataCriacao() == null) {
                parceirodenegocio.setDataCriacao(new Date());
            } else {
                parceirodenegocio.setDataModificacao(new Date());
            }

            boolean retorno = parceirodenegocioDAO.salvarOuAlterar(parceirodenegocio, session);

            if (retorno) {
                msg.getResultado().setId(parceirodenegocio.getId());
                msg.getResultado().setTipoRegistro(TipoRegistro.PARCEIRODENEGOCIO);
            } else {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir parceiro no banco de dados"));
            }
        }

        session.close();

        return msg;
    }

    private List<Erro> validacoes(ParceiroDeNegocio pn, Session session) {
        List<Erro> erros = new ArrayList<>();
        long id = 0;

        if (pn.getId() != null) {
            id = pn.getId();
        }

        if (pn.getNome() == "" || pn.getNome() == null || pn.getNome().isEmpty()) {
            erros.add(new Erro(CodigoErro.PARCEIROAA, "Necessário informar o nome."));
        } else {

            boolean exists = new ParceiroDeNegocioDAO().parceiroExists(id, pn.getNome(), session);

            if (exists) {
                erros.add(new Erro(CodigoErro.PARCEIROAB, "Este parceiro já existe."));
            }
        }

        if (pn.getTipoParceiro() == null) {
            erros.add(new Erro(CodigoErro.PARCEIROAC, "Necessário informar o tipo de parceiro."));
        }

        if (pn.getCnpj() != null) {
            if (pn.getCnpj().length() == 14 || pn.getCnpj().length() == 18) {
                boolean exists = new ParceiroDeNegocioDAO().cnpjExists(id, pn.getCnpj(), session);
                if (exists) {
                    erros.add(new Erro(CodigoErro.PARCEIROAD, "Este CNPJ já está cadastrado."));
                }
            }
        }

        if (pn.getCpf() != null) {
            if (pn.getCpf().length() == 11 || pn.getCpf().length() == 14) {
                boolean exists = new ParceiroDeNegocioDAO().cpfExists(id, pn.getCpf(), session);
                if (exists) {
                    erros.add(new Erro(CodigoErro.PARCEIROAE, "Este CPF já está cadastrado."));
                }
            }
        }

        return erros;
    }

    private void formatarObjeto(ParceiroDeNegocio parceirodenegocio) {
        if (parceirodenegocio.getUsuarioCriacao() == null) {
            parceirodenegocio.setUsuarioCriacao(new Usuario());
        }
        if (parceirodenegocio.getUsuarioModificacao() == null) {
            parceirodenegocio.setUsuarioModificacao(new Usuario());
        }
        if (parceirodenegocio.getEndereco() == null) {
            parceirodenegocio.setEndereco(new Endereco());
        }
    }

}
