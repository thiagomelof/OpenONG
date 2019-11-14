package bo;

import dao.ConvenioCategoriaDAO;
import dao.ConvenioDAO;
import dao.base.HibernateUtil;
import dto.ConvenioMessage;
import java.util.Date;
import java.util.List;
import model.Convenio;
import model.ConvenioCategoria;
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

    public ConvenioMessage cadastrar(ConvenioMessage convenio) {
        Session session = HibernateUtil.abrirSessao();
        ConvenioDAO convenioDAO = new ConvenioDAO();
        convenio.getConvenio().setDataCriacao(new Date());
        boolean salvo = convenioDAO.salvarOuAlterar(convenio.getConvenio(), session);

        if (salvo) {
            for (ConvenioCategoria categoria : convenio.getCategorias()) {
                
                categoria.getConvenio().setId(convenio.getConvenio().getId());
                salvo = new ConvenioCategoriaDAO().salvarOuAlterar(categoria, session);
                
            }
        }
        session.close();
        if (salvo) {
            return convenio;
        }

        return null;
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
