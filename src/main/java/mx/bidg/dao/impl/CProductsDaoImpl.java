package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CProductsDao;
import mx.bidg.model.CProducts;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
@Repository
@SuppressWarnings("unchecked")
public class CProductsDaoImpl extends AbstractDao<Integer, CProducts> implements CProductsDao {
    @Override
    public CProducts save(CProducts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CProducts findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CProducts> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CProducts update(CProducts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CProducts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<CProducts> findByBudgetSubcategory(int idBudgetSubcategory) {
        return createEntityCriteria()
                .createAlias("budgetSubcategories","bs")
                .add(Restrictions.eq("bs.idBudgetSubcategory", idBudgetSubcategory))
                .list();
    }
}
