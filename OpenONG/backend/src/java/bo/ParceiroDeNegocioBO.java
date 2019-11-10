package bo;

import dao.ParceiroDeNegocioDAO;
import dao.base.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Endereco;
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
    
    public ParceiroDeNegocio cadastrar(ParceiroDeNegocio parceirodenegocio) {
        Session session = HibernateUtil.abrirSessao();
        ParceiroDeNegocioDAO parceirodenegocioDAO = new ParceiroDeNegocioDAO();
        parceirodenegocio.setDataCriacao(new Date());
        boolean retorno = parceirodenegocioDAO.salvarOuAlterar(parceirodenegocio, session);
        session.close();
        if (retorno) {
            return parceirodenegocio;
        }

        return null;
    }

    private void formatarObjeto(ParceiroDeNegocio parceirodenegocio) {
        if (parceirodenegocio.getUsuarioCriacao() == null) {
            parceirodenegocio.setUsuarioCriacao(new Usuario());
        }
        if (parceirodenegocio.getUsuarioModificacao() == null) {
            parceirodenegocio.setUsuarioModificacao(new Usuario());
        }
        
        if (parceirodenegocio.getEndereco()== null) {
            parceirodenegocio.setEndereco(new Endereco());
        }
    }
    
}
