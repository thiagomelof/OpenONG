package bo;

import dao.ConvenioDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Convenio;
import model.ParceiroDeNegocio;
import model.Usuario;
import org.hibernate.Session;

public class ConvenioBO {

    public Convenio getConvenio(Long id) {
        ConvenioDAO convenioDAO = new ConvenioDAO();
        Session session = HibernateUtil.abrirSessao();
        Convenio convenio = convenioDAO.pesquisarPorId(id, session);

        formatarObjeto(convenio);

        session.close();
        return convenio;
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

    public Convenio cadastrar(Convenio convenio) {
        Session session = HibernateUtil.abrirSessao();
        ConvenioDAO convenioDAO = new ConvenioDAO();
        convenio.setDataCriacao(new Date());
        boolean retorno = convenioDAO.salvarOuAlterar(convenio, session);
        session.close();
        if (retorno) {
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
