package dao.base;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.transaction.spi.LocalStatus;

public abstract class BaseDao<T, ID> implements IBaseDao<T, ID> {

    private Transaction transacao;

    @Override
    public Boolean salvarOuAlterar(T entidade, Session session) throws HibernateException {
        transacao = session.beginTransaction();
        session.saveOrUpdate(entidade);
        transacao.commit();

        if (transacao.getLocalStatus() == LocalStatus.COMMITTED) {
            return true;
        }
        if (transacao.getLocalStatus() == LocalStatus.ROLLED_BACK || transacao.getLocalStatus() == LocalStatus.FAILED_COMMIT) {
            return false;
        }

        return false;
    }

    @Override
    public Boolean excluir(T entidade, Session session) throws HibernateException {
        transacao = session.beginTransaction();
        session.delete(entidade);
        transacao.commit();

        if (transacao.getLocalStatus() == LocalStatus.COMMITTED) {
            return true;
        }
        if (transacao.getLocalStatus() == LocalStatus.ROLLED_BACK || transacao.getLocalStatus() == LocalStatus.FAILED_COMMIT) {
            return false;
        }

        return false;
    }
}
