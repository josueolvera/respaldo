package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CStockDocumentsTypesDao;
import mx.bidg.model.CStockDocumentsTypes;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 29/12/15.
 */
@SuppressWarnings("unchecked")
@Repository
public class CStockDocumentsTypesDaoImpl extends AbstractDao<Integer, CStockDocumentsTypes> implements CStockDocumentsTypesDao {
    @Override
    public CStockDocumentsTypes save(CStockDocumentsTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CStockDocumentsTypes findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<CStockDocumentsTypes> findAll() {
        return (List<CStockDocumentsTypes>) createEntityCriteria()
                .addOrder(Order.asc("idDocumentType"))
                .list();
    }

    @Override
    public CStockDocumentsTypes update(CStockDocumentsTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CStockDocumentsTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<CStockDocumentsTypes> findAllRequired() {
        return (List<CStockDocumentsTypes>) createEntityCriteria()
                .add(Restrictions.eq("required",1))
                .list();
    }

    @Override
    public List<CStockDocumentsTypes> findAllNoRequired() {
        return (List<CStockDocumentsTypes>) createEntityCriteria()
                .add(Restrictions.eq("required",2))
                .list();
    }
}
