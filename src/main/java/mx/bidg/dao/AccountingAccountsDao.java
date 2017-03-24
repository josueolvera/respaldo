package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.AccountingAccounts;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
public interface AccountingAccountsDao extends InterfaceDao<AccountingAccounts> {
    AccountingAccounts findByThreeLevels(Integer idBudgetCategory, Integer idBudgetSubcategory, Integer idSubSubcategoies);
    List<AccountingAccounts> findByFirstLevel(Integer idBudgetCategory);
    List<AccountingAccounts> findBySecondLevel(Integer idBudgetSubcategory);
    List<AccountingAccounts> findByThirdLevel(Integer idSubSubcategoies);
    List<AccountingAccounts> findByFirstAndSecondLevel(Integer idBudgetCategory, Integer idBudgetSubcategory);
    List<AccountingAccounts> findAllCategories();
    List<AccountingAccounts> findAll();
    List<AccountingAccounts> findByBudgetCategory(Integer idBudgetCategory);
    AccountingAccounts findByAcronym(String acronyms);
    AccountingAccounts findByCategoryAndSubcategory(Integer idBudgetCategory, Integer idBudgetSubcategory);
    List<AccountingAccounts>findByLikeAcronyms(String acronyms);
}
