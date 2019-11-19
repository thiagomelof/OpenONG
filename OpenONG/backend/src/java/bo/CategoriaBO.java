package bo;

import constantes.CodigoErro;
import constantes.TipoRegistro;
import dao.CategoriaDAO;
import dao.base.HibernateUtil;
import dto.RetornoMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Categoria;
import model.Erro;
import model.Usuario;
import org.hibernate.Session;

public class CategoriaBO {

    public Categoria getCategoria(Long id) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Session session = HibernateUtil.abrirSessao();
        Categoria categoria = categoriaDAO.pesquisarPorId(id, session);

        formatarObjeto(categoria);

        session.close();
        return categoria;
    }

    public List<Categoria> getCategorias() {
        Session session = HibernateUtil.abrirSessao();
        List<Categoria> categorias = new CategoriaDAO().pesquisarTodos(session);
        session.close();
        return categorias;
    }

    public List<Categoria> getCategoriasAtivas() {
        Session session = HibernateUtil.abrirSessao();
        List<Categoria> categorias = new CategoriaDAO().pesquisarTodosAtivos(session);
        session.close();
        return categorias;
    }

    public RetornoMessage cadastrar(Categoria categoria) {
        RetornoMessage msg = new RetornoMessage();
        Session session = HibernateUtil.abrirSessao();
        
        List<Erro> erros = validacoes(categoria, session);

        if (erros.size() > 0) {
            msg.getErros().addAll(erros);
        } else {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            
            if (categoria.getDataCriacao() == null) {
                categoria.setDataCriacao(new Date());
            } else {
                categoria.setDataModificacao(new Date());
            }
            
            boolean retorno = categoriaDAO.salvarOuAlterar(categoria, session);

            if (retorno) {
                msg.getResultado().setId(categoria.getId());
                msg.getResultado().setTipoRegistro(TipoRegistro.CATEGORIA);
            } else {
                msg.getErros().add(new Erro(CodigoErro.ERROBANCO, "Erro ao inserir categoria no banco de dados"));
            }
        }
        session.close();

        return msg;
    }

    private void formatarObjeto(Categoria categoria) {
        if (categoria.getUsuarioCriacao() == null) {
            categoria.setUsuarioCriacao(new Usuario());
        }
        if (categoria.getUsuarioModificacao() == null) {
            categoria.setUsuarioModificacao(new Usuario());
        }
    }

    private List<Erro> validacoes(Categoria cat, Session session) {
        List<Erro> erros = new ArrayList<>();
        if (cat.getNome() == "" || cat.getNome() == null || cat.getNome().isEmpty()) {
            erros.add(new Erro(CodigoErro.CATEGORIAAA, "Necessário informar o nome."));
        } else {
            boolean exists = new CategoriaDAO().categoriaExists(cat.getNome(), session);
            if (exists) {
                erros.add(new Erro(CodigoErro.CATEGORIAAB, "Está categoria já existe."));
            }
        }
        return erros;
    }
}
