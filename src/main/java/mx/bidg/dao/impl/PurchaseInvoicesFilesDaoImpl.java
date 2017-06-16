package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PurchaseInvoicesFilesDao;
import mx.bidg.model.PurchaseInvoicesFiles;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by desarrollador on 14/06/17.
 */
@Repository
public class PurchaseInvoicesFilesDaoImpl extends AbstractDao<Integer, PurchaseInvoicesFiles> implements PurchaseInvoicesFilesDao {

    @Override
    public PurchaseInvoicesFiles save(PurchaseInvoicesFiles entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PurchaseInvoicesFiles findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PurchaseInvoicesFiles> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public PurchaseInvoicesFiles update(PurchaseInvoicesFiles entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PurchaseInvoicesFiles entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<PurchaseInvoicesFiles> findByIdPurchaseInvoices(Integer idPurchaseInvoices) {
        return createEntityCriteria()
                .add(Restrictions.eq("idPurchaseInvoices", idPurchaseInvoices))
                .list();
    }
}
