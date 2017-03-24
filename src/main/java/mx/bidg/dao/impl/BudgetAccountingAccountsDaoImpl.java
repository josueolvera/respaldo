package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BudgetAccountingAccountsDao;
import mx.bidg.model.BudgetAccountingAccounts;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 13/03/2017.
 */
@Repository
public class BudgetAccountingAccountsDaoImpl extends AbstractDao<Integer,BudgetAccountingAccounts> implements BudgetAccountingAccountsDao{

    @Override
    public BudgetAccountingAccounts save(BudgetAccountingAccounts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BudgetAccountingAccounts findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<BudgetAccountingAccounts> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public BudgetAccountingAccounts update(BudgetAccountingAccounts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(BudgetAccountingAccounts entity) {
        remove(entity);
        return true;
    }

    @Override
    public BudgetAccountingAccounts findByConceptBudget(Integer idConceptBudget) {
        Criteria criteria = createEntityCriteria();
        return (BudgetAccountingAccounts) criteria.add(Restrictions.eq("idConceptBudget",idConceptBudget)).uniqueResult();
    }
}
