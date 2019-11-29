package dao;

import dao.base.BaseDao;
import dao.interfaces.IDoacaoDAO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import model.Doacao;
import model.DoacaoItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class DoacaoDAO extends BaseDao<Doacao, Long>
        implements IDoacaoDAO, Serializable {

    @Override
    public Doacao pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Doacao) session.get(Doacao.class, id);
    }

    @Override
    public List<Doacao> pesquisarTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Doacao order by id desc");
        return consulta.list();
    }

    public List<Doacao> pesquisarTodosAtivos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Doacao where status =:statusHQL order by id desc");
        consulta.setParameter("statusHQL", true);

        return consulta.list();
    }

    @Override
    public List<Doacao> pesquisarPorNomeDoParceiroDeNegocio(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Doacao d join fetch d.parceiroDeNegocio pn"
                + " where pn.nome like :pnHQL");
        consulta.setParameter("pnHQL", "%" + nome + "%");

        return consulta.list();
    }

    public List<DoacaoItem> relatorioDeDoacao(Long idParceiro, Long idConvenio, Date dtInicio, Date dtFim, Session session) throws HibernateException {

        String query = " from DoacaoItem ITEM "
                + " join fetch ITEM.doacao DOACAO "
                + " join fetch DOACAO.parceiroDeNegocio PARCEIRO "
                + " join fetch DOACAO.convenio CONVENIO "
                + " where DOACAO.lancamento BETWEEN :dtInicioHQL and :dtFimHQL "
                + " and DOACAO.status =:statusHQL ";

        if (idParceiro > 0) {
            query += " AND PARCEIRO.id =:parceiroHQL ";
        }
        if (idConvenio > 0) {
            query += " AND CONVENIO.id =:convenioHQL ";
        }

        query += "  order by CONVENIO.lancamento asc ";

        Query consulta = session.createQuery(query).setParameter("dtInicioHQL", dtInicio).setParameter("dtFimHQL", dtFim).setParameter("statusHQL", true);

        if (idParceiro > 0) {
            consulta.setParameter("parceiroHQL", idParceiro);
        }
        if (idConvenio > 0) {
            consulta.setParameter("convenioHQL", idConvenio);
        }

        return consulta.list();
    }

    public List<DoacaoItem> doacoesPorPeriodo(Date dtInicio, Date dtFim, Session session) throws HibernateException {

        String query = " from DoacaoItem DOACAOITEM "
                + " join fetch DOACAOITEM.doacao DOACAO "
                + " where DOACAO.status =:statusHQL "
                + " and DOACAO.lancamento BETWEEN :dtInicioHQL and :dtFimHQL ";

        Query consulta = session.createQuery(query).setParameter("statusHQL", true).setParameter("dtInicioHQL", dtInicio).setParameter("dtFimHQL", dtFim);

        return consulta.list();
    }
}
