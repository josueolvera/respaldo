package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CProductTypesDao;
import mx.bidg.model.CProductTypes;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

/**
 * @author Rafael Viveros
 *         Created on 19/11/15.
 */
@Repository
public class CProductTypesDaoImpl extends AbstractDao<Integer, CProductTypes> implements CProductTypesDao {
    @Override
    public CProductTypes save(CProductTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CProductTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CProductTypes> findAll() {
        return (List<CProductTypes>) createEntityCriteria().list();
    }

    @Override
    public CProductTypes update(CProductTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CProductTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CProductTypes findByIdFetchBudgetSubcategory(int id) {
        return (CProductTypes) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .setFetchMode("budgetSubcategory", FetchMode.JOIN)
                .uniqueResult();
    }
}
