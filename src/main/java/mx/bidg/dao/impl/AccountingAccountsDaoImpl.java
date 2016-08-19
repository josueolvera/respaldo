package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AccountingAccountsDao;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CDistributors;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.hibernate.criterion.Projections;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class AccountingAccountsDaoImpl extends AbstractDao<Integer, AccountingAccounts> implements AccountingAccountsDao {
    @Override
    public AccountingAccounts findByThreeLevels(Integer firstLevel, Integer secondLevel, Integer thirdLevel) {
        return (AccountingAccounts) createEntityCriteria()
                .add(Restrictions.eq("firstLevel", firstLevel))
                .add(Restrictions.eq("secondLevel", secondLevel))
                .add(Restrictions.eq("thirdLevel", thirdLevel))
                .uniqueResult();
    }

    @Override
    public List<AccountingAccounts> findByFirstLevel(Integer firstLevel) {
        return createEntityCriteria()
                .add(Restrictions.eq("firstLevel", firstLevel))
                .list();
    }

    @Override
    public List<AccountingAccounts> findBySecondLevel(Integer secondLevel) {
        return createEntityCriteria()
                .add(Restrictions.eq("secondLevel", secondLevel))
                .list();
    }

    @Override
    public List<AccountingAccounts> findByThirdLevel(Integer thirdLevel) {
        return createEntityCriteria()
                .add(Restrictions.eq("thirdLevel", thirdLevel))
                .list();
    }

    @Override
    public AccountingAccounts save(AccountingAccounts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public AccountingAccounts findById(int id) {
        return (AccountingAccounts) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<AccountingAccounts> findAll() {
        return createEntityCriteria()
                .addOrder(Order.asc("firstLevel"))
                .addOrder(Order.asc("secondLevel"))
                .addOrder(Order.asc("thirdLevel"))
                .list();
    }

    @Override
    public AccountingAccounts update(AccountingAccounts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(AccountingAccounts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<AccountingAccounts> findAllCategories() {
        return createEntityCriteria()
                .add(Restrictions.eq("isOfRequest", 1))
                .setProjection(Projections.distinct(Projections.property("budgetCategory")))
                .list();
    }

    @Override
    public List<AccountingAccounts> findByBudgetCategory(Integer idBudgetCategory) {
        return createEntityCriteria()
                .add(Restrictions.eq("idBudgetCategory",idBudgetCategory))
                .list();
    }
}
