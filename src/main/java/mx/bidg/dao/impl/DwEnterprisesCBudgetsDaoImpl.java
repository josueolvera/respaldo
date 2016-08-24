package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DwEnterprisesCBudgetsDao;
import mx.bidg.model.DwEnterprisesCBudgets;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class DwEnterprisesCBudgetsDaoImpl extends AbstractDao<Integer, DwEnterprisesCBudgets> implements DwEnterprisesCBudgetsDao {

    @Override
    public DwEnterprisesCBudgets save(DwEnterprisesCBudgets entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DwEnterprisesCBudgets findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DwEnterprisesCBudgets> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DwEnterprisesCBudgets update(DwEnterprisesCBudgets entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DwEnterprisesCBudgets entity) {
        remove(entity);
        return true;
    }
}
