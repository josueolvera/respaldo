package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTransactionsDocumentsTypeDao;
import mx.bidg.model.CTransactionsDocumentsTypes;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 9/06/16.
 */
@Repository
public class CTransactionsDocumentsTypeDaoImpl extends AbstractDao<Integer,CTransactionsDocumentsTypes> implements CTransactionsDocumentsTypeDao {

    @Override
    public CTransactionsDocumentsTypes save(CTransactionsDocumentsTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CTransactionsDocumentsTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTransactionsDocumentsTypes> findAll() {
        return (List<CTransactionsDocumentsTypes>) createEntityCriteria().addOrder(Order.asc("idTransactionDocumentType")).list();
    }

    @Override
    public CTransactionsDocumentsTypes update(CTransactionsDocumentsTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CTransactionsDocumentsTypes entity) {
        remove(entity);
        return true;
    }
}
