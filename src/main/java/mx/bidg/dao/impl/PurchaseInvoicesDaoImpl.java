package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PurchaseInvoicesDao;
import mx.bidg.model.PurchaseInvoices;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
@Repository
public class PurchaseInvoicesDaoImpl extends AbstractDao<Integer, PurchaseInvoices> implements PurchaseInvoicesDao {

    @Override
    public PurchaseInvoices save(PurchaseInvoices entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PurchaseInvoices findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PurchaseInvoices> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public PurchaseInvoices update(PurchaseInvoices entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PurchaseInvoices entity) {
        remove(entity);
        return true;
    }
}
