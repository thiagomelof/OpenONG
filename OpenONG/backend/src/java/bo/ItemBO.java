package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.ItemDAO;
import dao.base.HibernateUtil;
import dto.RetornoMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Categoria;
import model.Erro;
import model.Item;
import model.Usuario;
import org.hibernate.Session;

public class ItemBO {

    public Item getItem(Long id) {
        ItemDAO itemDAO = new ItemDAO();
        Session session = HibernateUtil.abrirSessao();
        Item item = itemDAO.pesquisarPorId(id, session);

        formatarObjeto(item);

        session.close();
        return item;
    }

    public List<Item> getItensAtivos() {
        Session session = HibernateUtil.abrirSessao();
        List<Item> itens = new ItemDAO().pesquisarTodosAtivos(session);
        session.close();
        return itens;
    }

    public List<Item> pesquisarTodosAtivosPorCategoriasDeConvenio(long idConvenio) {
        Session session = HibernateUtil.abrirSessao();
        List<Item> itens = new ItemDAO().pesquisarTodosAtivosPorCategoriasDeConvenio(idConvenio, session);
        session.close();
        return itens;
    }

    public List<Item> getItens() {
        Session session = HibernateUtil.abrirSessao();
        List<Item> itens = new ItemDAO().pesquisarTodos(session);
        session.close();
        return itens;
    }

    public RetornoMessage cadastrar(Item item) {

        RetornoMessage msg = new RetornoMessage();
        Session session = HibernateUtil.abrirSessao();

        List<Erro> erros = validacoes(item, session);

        if (erros.size() > 0) {
            msg.getErros().addAll(erros);
        } else {
            ItemDAO itemDAO = new ItemDAO();

            if (item.getDataCriacao() == null) {
                item.setDataCriacao(new Date());
            } else {
                item.setDataModificacao(new Date());
            }

            boolean retorno = itemDAO.salvarOuAlterar(item, session);

            if (retorno) {
                msg.getResultado().setId(item.getId());
                msg.getResultado().setTipoRegistro(TipoRegistro.ITEM);
            } else {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir o item no banco de dados"));
            }
        }
        session.close();
        return msg;
    }

    private List<Erro> validacoes(Item item, Session session) {
        List<Erro> erros = new ArrayList<>();
        long id = 0;

        if (item.getId() != null) {
            id = item.getId();
        }
        if (item.getNome() == "" || item.getNome() == null || item.getNome().isEmpty()) {
            erros.add(new Erro(CodigoErro.ITEMAA, "Necessário informar o nome."));
        } else {
            boolean exists = new ItemDAO().itemExists(id, item.getNome(), session);
            if (exists) {
                erros.add(new Erro(CodigoErro.ITEMAB, "Este item já existe."));
            }
        }

        if (item.getCategoria().getId() == null || item.getCategoria().getId() == -1) {
            erros.add(new Erro(CodigoErro.ITEMAC, "Necessário informar uma categoria válida."));
        }

        if (item.getTipoItem() == null) {
            erros.add(new Erro(CodigoErro.ITEMAD, "Necessário informar um tipo do item válido."));
        }

        return erros;
    }

    private void formatarObjeto(Item item) {
        if (item.getUsuarioCriacao() == null) {
            item.setUsuarioCriacao(new Usuario());
        }
        if (item.getUsuarioModificacao() == null) {
            item.setUsuarioModificacao(new Usuario());
        }
        if (item.getCategoria() == null) {
            item.setCategoria(new Categoria());
        }
    }

}
