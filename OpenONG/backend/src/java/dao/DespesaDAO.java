package dao;

import dao.base.BaseDao;
import dao.interfaces.IDespesaDAO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import model.Despesa;
import model.DespesaItem;
import model.DespesaItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class DespesaDAO extends BaseDao<Despesa, Long>
        implements IDespesaDAO, Serializable {

    @Override
    public Despesa pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Despesa) session.get(Despesa.class, id);
    }

    @Override
    public List<Despesa> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Despesa");
        return consulta.list();
    }
    
    public List<Despesa> pesquisarTodosAtivos(Session session) throws HibernateException {        
        Query consulta = session.createQuery("from Despesa where status =:statusHQL");
        consulta.setParameter("statusHQL", true);

        return consulta.list();
    }

    @Override
    public List<Despesa> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Despesa d join fetch d.parceiroDeNegocio pn"
                        + " where pn.nome like :pnHQL");
        consulta.setParameter("pnHQL", "%" + nome + "%");
        
        return consulta.list();
    }
    
    public List<DespesaItem> relatorioDeDespesa(Session session) throws HibernateException {
        long id =1;
        Query consulta = session.createQuery("from DespesaItem item join fetch item.despesa d"
                + " join fetch d.parceiroDeNegocio pn "
                        //+ " where d.id =:idHQL "
                        +" where pn.id=:pnHQL "
        );
        //consulta.setParameter("idHQL", id);
        consulta.setParameter("pnHQL", id);
        
        return consulta.list();
    }
    
    public List<DespesaItem> relatorioDeDespesa(Long idParceiro, Long idConvenio, Date dtInicio, Date dtFim, Session session) throws HibernateException {

        String query = " from DespesaItem ITEM "
                + " join fetch ITEM.despesa DESPESA "
                + " join fetch DESPESA.parceiroDeNegocio PARCEIRO "
                + " join fetch DESPESA.convenio CONVENIO "
                
                + " where DESPESA.lancamento BETWEEN :dtInicioHQL and :dtFimHQL ";

        if (idParceiro > 0) {
            query += " AND PARCEIRO.id =:parceiroHQL ";
        }
        if (idConvenio > 0) {
            query += " AND CONVENIO.id =:convenioHQL ";
        }

        Query consulta = session.createQuery(query).setParameter("dtInicioHQL", dtInicio).setParameter("dtFimHQL", dtFim);
            
        if (idParceiro > 0) {
            consulta.setParameter("parceiroHQL", idParceiro);
        }
        if (idConvenio > 0) {
            consulta.setParameter("convenioHQL", idConvenio);
        }
        
        return consulta.list();
    }
}
