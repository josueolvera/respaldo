package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.TransactionsDocumentsDao;
import mx.bidg.model.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 9/06/16.
 */
@Repository
public class TransactionsDocumentsDaoImpl extends AbstractDao<Integer,TransactionsDocuments> implements TransactionsDocumentsDao {
    @Override
    public TransactionsDocuments save(TransactionsDocuments entity) {
        persist(entity);
        return entity;
    }

    @Override
    public TransactionsDocuments findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<TransactionsDocuments> findAll() {
        return (List<TransactionsDocuments>) createEntityCriteria().list();
    }

    @Override
    public TransactionsDocuments update(TransactionsDocuments entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(TransactionsDocuments entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<TransactionsDocuments> findByIdAccountPayable(Integer idTransaction) {
        return (List<TransactionsDocuments>) createEntityCriteria()
                .add(Restrictions.eq("idTransaction",idTransaction))
                .addOrder(Order.asc("idTransactionDocumentType"))
                .list();
    }

    @Override
    public TransactionsDocuments findBy(Transactions transactions, CTransactionsDocumentsTypes cTransactionsDocumentsTypes) {
        return (TransactionsDocuments) createEntityCriteria()
                .add(Restrictions.eq("idTransaction",transactions.getIdTransaction()))
                .add(Restrictions.eq("idTransactionDocumentType",cTransactionsDocumentsTypes.getIdTransactionDocumentType()))
                .uniqueResult();
    }

    @Override
    public List<TransactionsDocuments> findRecordBy(Transactions transactions) {
        return (List<TransactionsDocuments>) createEntityCriteria()
                .add(Restrictions.eq("idTransaction",transactions.getIdTransaction()))
                .addOrder(Order.asc("idTransactionDocumentType"))
                .addOrder(Order.desc("uploadingDate"))
                .list();
    }
}
