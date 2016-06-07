package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AccountingAccountsDao;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CDistributors;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
@Repository
public class AccountingAccountsDaoImpl extends AbstractDao<Integer, AccountingAccounts> implements AccountingAccountsDao {
    @Override
    public AccountingAccounts findByThreeLevels(CDistributors distributor, Integer firstLevel, Integer secondLevel, Integer thirdLevel) {
        return (AccountingAccounts) createEntityCriteria()
                .add(Restrictions.eq("idDistributor", distributor.getIdDistributor()))
                .add(Restrictions.eq("firstLevel", firstLevel))
                .add(Restrictions.eq("secondLevel", secondLevel))
                .add(Restrictions.eq("thirdLevel", thirdLevel))
                .uniqueResult();
    }

    @Override
    public AccountingAccounts save(AccountingAccounts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
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
                .list();
    }

    @Override
    public AccountingAccounts update(AccountingAccounts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(AccountingAccounts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
