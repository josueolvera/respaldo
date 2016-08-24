package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.UsersDwEnterprisesCBudgetsDao;
import mx.bidg.model.UsersDwEnterprisesCBudgets;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class UsersDwEnterprisesCBudgetsDaoImpl extends AbstractDao<Integer, UsersDwEnterprisesCBudgets> implements UsersDwEnterprisesCBudgetsDao {

    @Override
    public UsersDwEnterprisesCBudgets save(UsersDwEnterprisesCBudgets entity) {
        persist(entity);
        return entity;
    }

    @Override
    public UsersDwEnterprisesCBudgets findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<UsersDwEnterprisesCBudgets> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public UsersDwEnterprisesCBudgets update(UsersDwEnterprisesCBudgets entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(UsersDwEnterprisesCBudgets entity) {
        remove(entity);
        return true;
    }
}
