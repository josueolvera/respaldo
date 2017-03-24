package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AccountingAccountsDao;
import mx.bidg.model.AccountingAccounts;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
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
    public AccountingAccounts findByThreeLevels(Integer idBudgetCategory, Integer idBudgetSubcategory, Integer idSubSubcategoies) {
        return (AccountingAccounts) createEntityCriteria()
                .add(Restrictions.eq("idBudgetCategory", idBudgetCategory))
                .add(Restrictions.eq("idBudgetSubcategory", idBudgetSubcategory))
                .add(Restrictions.eq("idSubSubcategoies", idSubSubcategoies))
                .uniqueResult();
    }

    @Override
    public List<AccountingAccounts> findByFirstLevel(Integer idBudgetCategory) {
        return createEntityCriteria()
                .add(Restrictions.eq("idBudgetCategory", idBudgetCategory))
                .list();
    }

    @Override
    public List<AccountingAccounts> findBySecondLevel(Integer idBudgetSubcategory) {
        return createEntityCriteria()
                .add(Restrictions.eq("idBudgetSubcategory", idBudgetSubcategory))
                .list();
    }

    @Override
    public List<AccountingAccounts> findByThirdLevel(Integer idSubSubcategoies) {
        return createEntityCriteria()
                .add(Restrictions.eq("idSubSubcategoies", idSubSubcategoies))
                .list();
    }

    @Override
    public List<AccountingAccounts> findByFirstAndSecondLevel(Integer idBudgetCategory, Integer idBudgetSubcategory) {
        return createEntityCriteria()
                .add(Restrictions.eq("idBudgetCategory", idBudgetCategory))
                .add(Restrictions.eq("idBudgetSubcategory", idBudgetSubcategory))
                .add(Restrictions.ne("idBudgetSubcategory", 0))
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
        return createEntityCriteria().list();
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

    @Override
    public AccountingAccounts findByAcronym(String acronyms) {
        Criteria criteria = createEntityCriteria();
        return (AccountingAccounts) criteria.add(Restrictions.eq("acronyms",acronyms)).uniqueResult();
    }

    @Override
    public AccountingAccounts findByCategoryAndSubcategory(Integer idBudgetCategory, Integer idBudgetSubcategory) {
        return (AccountingAccounts) createEntityCriteria()
                .add(Restrictions.eq("idBudgetCategory", idBudgetCategory))
                .add(Restrictions.eq("idBudgetSubcategory", idBudgetSubcategory))
                .uniqueResult();
    }

    @Override
    public List<AccountingAccounts> findByLikeAcronyms(String acronyms) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.ilike("acronyms",acronyms, MatchMode.ANYWHERE)).list();
    }
}
