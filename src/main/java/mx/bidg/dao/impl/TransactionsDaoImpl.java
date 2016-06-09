package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.TransactionsDao;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.COperationTypes;
import mx.bidg.model.CTransactionsStatus;
import mx.bidg.model.Transactions;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Repository
public class TransactionsDaoImpl extends AbstractDao<Integer,Transactions> implements TransactionsDao {
    @Override
    public Transactions save(Transactions entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Transactions findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Transactions> findAll() {
        return (List<Transactions>)createEntityCriteria().list();
    }

    @Override
    public Transactions update(Transactions entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Transactions entity) {
        remove(entity);
        return true;
    }

    @Override
    public Transactions findByAccount(AccountsPayable accountsPayable) {
        Criteria criteria = createEntityCriteria();
        return (Transactions) criteria.add(Restrictions.eq("accountsPayable",accountsPayable)).uniqueResult();
    }

    @Override
    public List<Transactions> findTransactionByDate(LocalDateTime ofDate, LocalDateTime untilDate) {
        Criteria criteria = createEntityCriteria();
        return (List<Transactions>) criteria
                .add(Restrictions.between("creationDate",ofDate,untilDate))
                .add(Restrictions.eq("operationTypes", COperationTypes.INGRESO))
                .list();
    }

    @Override
    public List<Transactions> findTransactionByDateAndExit(LocalDateTime ofDate, LocalDateTime untilDate) {
        Criteria criteria = createEntityCriteria();
        return (List<Transactions>) criteria
                .add(Restrictions.between("creationDate",ofDate,untilDate))
                .add(Restrictions.eq("operationTypes", COperationTypes.EGRESO))
                .list();
    }
}
