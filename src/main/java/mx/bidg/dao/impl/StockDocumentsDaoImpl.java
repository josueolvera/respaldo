package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.StockDocumentsDao;
import mx.bidg.model.CStockDocumentsTypes;
import mx.bidg.model.StockDocuments;
import mx.bidg.model.Stocks;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
@Repository
public class StockDocumentsDaoImpl extends AbstractDao<Integer, StockDocuments> implements StockDocumentsDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<StockDocuments> findByIdStock(Integer idStock) {
        return (List<StockDocuments>) createEntityCriteria()
                .add(Restrictions.eq("idStock", idStock))
                .add(Restrictions.eq("currentDocument", 1))
                .addOrder(Order.asc("idDocumentType"))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StockDocuments> findRecordBy(Stocks stock) {
        return (List<StockDocuments>) createEntityCriteria()
                .add(Restrictions.eq("idStock", stock.getIdStock()))
                .add(Restrictions.eq("currentDocument", 0))
                .addOrder(Order.asc("idDocumentType"))
                .addOrder(Order.desc("uploadingDate"))
                .list();
    }

    @Override
    public StockDocuments findBy(Stocks stocks, CStockDocumentsTypes documentType) {
        return (StockDocuments) createEntityCriteria()
                .add(Restrictions.eq("idStock", stocks.getIdStock()))
                .add(Restrictions.eq("idDocumentType", documentType.getIdDocumentType()))
                .uniqueResult();
    }

    @Override
    public StockDocuments save(StockDocuments entity) {
        persist(entity);
        return entity;
    }

    @Override
    public StockDocuments findById(int id) {
        return (StockDocuments) createEntityCriteria()
                .add(Restrictions.eq("idStockDocument", id))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StockDocuments> findAll() {
        return (List<StockDocuments>) createEntityCriteria()
                .list();
    }

    @Override
    public StockDocuments update(StockDocuments entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public boolean delete(StockDocuments entity) {
        remove(entity);
        return true;
    }
}
