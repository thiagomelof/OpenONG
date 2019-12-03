package dao;

import dao.base.BaseDao;
import dao.interfaces.IDespesaDAO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import model.Despesa;
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
        Query consulta = session.createQuery("from Despesa order by id desc");
        return consulta.list();
    }

    public List<Despesa> pesquisarTodosAtivos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Despesa where status =:statusHQL order by lancamento asc");
        consulta.setParameter("statusHQL", true);

        return consulta.list();
    }

    public List<DespesaItem> relatorioDeDespesa(Long idParceiro, Long idConvenio, Date dtInicio, Date dtFim, Session session) throws HibernateException {

        String query = " from DespesaItem ITEM "
                + " join fetch ITEM.despesa DESPESA "
                + " join fetch DESPESA.parceiroDeNegocio PARCEIRO ";

        if (idConvenio > 0 || idConvenio == -1) {
            query += "left join fetch DESPESA.convenio CONVENIO ";
        }

        query += " where DESPESA.lancamento BETWEEN :dtInicioHQL and :dtFimHQL "
                + " and DESPESA.status =:statusHQL ";

        if (idParceiro > 0) {
            query += " AND PARCEIRO.id =:parceiroHQL ";
        }
        if (idConvenio > 0) {
            query += " AND CONVENIO.id =:convenioHQL ";
        } else if (idConvenio == -1) {
            query += " AND CONVENIO.id is null ";
        }

        query += "  order by DESPESA.lancamento asc ";

        Query consulta = session.createQuery(query).setParameter("dtInicioHQL", dtInicio).setParameter("dtFimHQL", dtFim).setParameter("statusHQL", true);

        if (idParceiro > 0) {
            consulta.setParameter("parceiroHQL", idParceiro);
        }
        if (idConvenio > 0) {
            consulta.setParameter("convenioHQL", idConvenio);
        }

        return consulta.list();
    }

    public List<DespesaItem> DespesasAtivasPorCategoria(Date dtInicio, Date dtFim, Session session) throws HibernateException {

        String query = " from DespesaItem DESPESAITEM "
                + " join fetch DESPESAITEM.despesa DESPESA "
                + " join fetch DESPESAITEM.item ITEM "
                + " join fetch ITEM.categoria CATEGORIA "
                + " where DESPESA.status =:statusHQL "
                + " and DESPESA.lancamento BETWEEN :dtInicioHQL and :dtFimHQL ";

        Query consulta = session.createQuery(query).setParameter("statusHQL", true).setParameter("dtInicioHQL", dtInicio).setParameter("dtFimHQL", dtFim);

        return consulta.list();
    }

    public List<DespesaItem> despesasPorPeriodo(Date dtInicio, Date dtFim, Session session) throws HibernateException {

        String query = " from DespesaItem DESPESAITEM "
                + " join fetch DESPESAITEM.despesa DESPESA "
                + " where DESPESA.status =:statusHQL "
                + " and DESPESA.lancamento BETWEEN :dtInicioHQL and :dtFimHQL ";

        Query consulta = session.createQuery(query).setParameter("statusHQL", true).setParameter("dtInicioHQL", dtInicio).setParameter("dtFimHQL", dtFim);

        return consulta.list();
    }

    public List<DespesaItem> despesasPorConvenio(long idConvenio, Session session) throws HibernateException {

        String query = " from DespesaItem DESPESAITEM "
                + " join fetch DESPESAITEM.item ITEM "
                + " join fetch ITEM.categoria CATEGORIA "
                + " join fetch DESPESAITEM.despesa DESPESA "
                + " join fetch DESPESA.convenio CONVENIO "
                + " where DESPESA.status =:statusHQL "
                + " and CONVENIO.id=:idConvenioHQL ";

        Query consulta = session.createQuery(query).setParameter("statusHQL", true).setParameter("idConvenioHQL", idConvenio);

        return consulta.list();
    }
}
