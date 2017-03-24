package mx.bidg.service;

import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.Users;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
public interface AccountingAccountsService {
    AccountingAccounts findByThreeLevels(Integer idBudgetCategory, Integer idBudgetSubcategory, Integer idSubSubcategoies);
    List<AccountingAccounts> findByFirstLevel(Integer idBudgetCategory);
    List<AccountingAccounts> findBySecondLevel(Integer idBudgetSubcategory);
    List<AccountingAccounts> findByThirdLevel(Integer idSubSubcategoies);
    List<AccountingAccounts> findByFirstAndSecondLevel(Integer idBudgetCategory, Integer idBudgetSubcategory);
    List<AccountingAccounts> findAll();
    AccountingAccounts save(String data, Users user) throws IOException;
    AccountingAccounts update(AccountingAccounts accountingAccounts);
    AccountingAccounts findById(Integer idAccountingAccount);
    List<AccountingAccounts> findAllCategories();
    AccountingAccounts findByAcronym(String acronym);
    List<AccountingAccounts>findByLikeAcronyms(String acronyms);
}
