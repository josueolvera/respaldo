package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.AccountingAccountsDao;
import mx.bidg.dao.CBudgetCategoriesDao;
import mx.bidg.dao.CBudgetSubSubcategoriesDao;
import mx.bidg.dao.CBudgetSubcategoriesDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.AccountingAccountsService;
import mx.bidg.service.CBudgetCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rafael Viveros
 *         Created on 6/06/16.
 */
@Service
@Transactional
public class AccountingAccountsServiceImpl implements AccountingAccountsService {

    @Autowired
    private AccountingAccountsDao accountingAccountsDao;

    @Autowired
    private CBudgetCategoriesDao cBudgetCategoriesDao;

    @Autowired
    private CBudgetSubcategoriesDao cBudgetSubcategoriesDao;

    @Autowired
    private CBudgetSubSubcategoriesDao cBudgetSubSubcategoriesDao;

    @Autowired
    private CBudgetCategoriesService cBudgetCategoriesService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public AccountingAccounts findByThreeLevels(Integer idBudgetCategory, Integer idBudgetSubcategory, Integer idSubSubcategoies) {
        return accountingAccountsDao.findByThreeLevels(idBudgetCategory, idBudgetSubcategory, idSubSubcategoies);

    }

    @Override
    public List<AccountingAccounts> findByFirstLevel(Integer idBudgetCategory) {
        return accountingAccountsDao.findByFirstLevel(idBudgetCategory);
    }

    @Override
    public List<AccountingAccounts> findBySecondLevel(Integer idBudgetSubcategory) {
        return accountingAccountsDao.findBySecondLevel(idBudgetSubcategory);
    }

    @Override
    public List<AccountingAccounts> findByThirdLevel(Integer idSubSubcategoies) {
        return accountingAccountsDao.findByThirdLevel(idSubSubcategoies);
    }

    @Override
    public List<AccountingAccounts> findByFirstAndSecondLevel(Integer idBudgetCategory, Integer idBudgetSubcategory) {
        return accountingAccountsDao.findByFirstAndSecondLevel(idBudgetCategory, idBudgetSubcategory);
    }

    @Override
    public List<AccountingAccounts> findAll() {
        return accountingAccountsDao.findAll();
    }

    @Override
    public AccountingAccounts save(String data, Users user) throws IOException {
        JsonNode node = mapper.readTree(data);
        LocalDateTime now = LocalDateTime.now();
        String firstLevel = node.get("firstLevel").asText();
        String secondLevel = node.get("secondLevel").asText();
        String thirdLevel = node.get("thirdLevel").asText();
        String descriptionFirst = node.get("descriptionFirst").asText();
        String descriptionSecond = node.get("descriptionSecond").asText();
        String descriptionThird = node.get("descriptionThird").asText();
        String acronyms = node.get("acronyms").asText();
        AccountingAccounts accountingAccount = new AccountingAccounts();
        CBudgetCategories cBudgetCategories = new CBudgetCategories();
        CBudgetSubcategories cBudgetSubcategories = new CBudgetSubcategories();
        CBudgetSubSubcategories cBudgetSubSubcategories = new CBudgetSubSubcategories();
        AccountingAccounts accounting = new AccountingAccounts();
        CBudgetCategories categories;
        List<CBudgetSubSubcategories> subSubcategories;
        CBudgetSubSubcategories subSubcategories1;

        if (secondLevel.equals("") && thirdLevel.equals("")) {
            cBudgetCategories.setBudgetCategory(descriptionFirst);
            cBudgetCategories.setFirstLevel(firstLevel);
            cBudgetCategories.setUsername(user.getUsername());
            cBudgetCategories.setCreationDate(now);
            cBudgetCategories.setIdAccessLevel(1);
            cBudgetCategories = cBudgetCategoriesDao.save(cBudgetCategories);
            accountingAccount.setBudgetCategory(cBudgetCategories);
            accountingAccount.setBudgetSubcategory(new CBudgetSubcategories(0));
            accountingAccount.setcBudgetSubSubcategories(new CBudgetSubSubcategories(0));
            accountingAccount.setCreationDate(now);
            accountingAccount.setUsername(user.getUsername());
            accountingAccount.setAcronyms(cBudgetCategories.getFirstLevel() + "-000-0000");
            accountingAccount.setcAccountingAccountCategory(mapper.treeToValue(node.get("accountingAccountCategory"), CAccountingAccountCategory.class));
            accountingAccount.setcAccountingAccountNature(mapper.treeToValue(node.get("accountingAccountNature"), CAccountingAccountNature.class));
            accountingAccount.setcAccountingAccountType(mapper.treeToValue(node.get("accountingAccountType"), CAccountingAccountType.class));
            accountingAccount = accountingAccountsDao.save(accountingAccount);
        }

        categories = cBudgetCategoriesDao.findByFirstLevel(firstLevel);
        if (thirdLevel.equals("")) {
            cBudgetSubcategories.setBudgetSubcategory(descriptionSecond);
            cBudgetSubcategories.setUsername(user.getUsername());
            cBudgetSubcategories.setSecondLevel(secondLevel);
            cBudgetSubcategories.setIdAccessLevel(1);
            cBudgetSubcategories.setCreationDate(now);
            cBudgetSubcategories = cBudgetSubcategoriesDao.save(cBudgetSubcategories);
            accountingAccount.setBudgetCategory(categories);
            accountingAccount.setBudgetSubcategory(cBudgetSubcategories);
            accountingAccount.setcBudgetSubSubcategories(new CBudgetSubSubcategories(0));
            accountingAccount.setCreationDate(now);
            accountingAccount.setUsername(user.getUsername());
            System.out.println("Cuenta contable:" + categories.getFirstLevel() + "-" + cBudgetSubcategories.getSecondLevel() + "-0000");
            accountingAccount.setAcronyms(categories.getFirstLevel() + "-" + cBudgetSubcategories.getSecondLevel() + "-0000");
            accountingAccount.setcAccountingAccountCategory(mapper.treeToValue(node.get("accountingAccountCategory"), CAccountingAccountCategory.class));
            accountingAccount.setcAccountingAccountNature(mapper.treeToValue(node.get("accountingAccountNature"), CAccountingAccountNature.class));
            accountingAccount.setcAccountingAccountType(mapper.treeToValue(node.get("accountingAccountType"), CAccountingAccountType.class));
            accountingAccount = accountingAccountsDao.save(accountingAccount);
        } else {
            accounting = accountingAccountsDao.findByAcronym(firstLevel + "-" + secondLevel + "-" + thirdLevel);
            if (accounting == null) {
                cBudgetSubSubcategories.setName(descriptionThird);
                cBudgetSubSubcategories.setCreationDate(now);
                cBudgetSubSubcategories.setUsername(user.getUsername());
                cBudgetSubSubcategories.setThirdLevel(thirdLevel);
                cBudgetSubSubcategories = cBudgetSubSubcategoriesDao.save(cBudgetSubSubcategories);
                categories = cBudgetCategoriesDao.findByFirstLevel(firstLevel);
                accountingAccount.setBudgetCategory(categories);
                AccountingAccounts second = accountingAccountsDao.findByAcronym(firstLevel + "-" + secondLevel + "-0000");
                accountingAccount.setBudgetSubcategory(second.getBudgetSubcategory());
                accountingAccount.setcBudgetSubSubcategories(cBudgetSubSubcategories);
                accountingAccount.setCreationDate(now);
                accountingAccount.setUsername(user.getUsername());
                accountingAccount.setAcronyms(categories.getFirstLevel() + "-" + secondLevel + "-" + cBudgetSubSubcategories.getThirdLevel());
                accountingAccount.setcAccountingAccountCategory(mapper.treeToValue(node.get("accountingAccountCategory"), CAccountingAccountCategory.class));
                accountingAccount.setcAccountingAccountNature(mapper.treeToValue(node.get("accountingAccountNature"), CAccountingAccountNature.class));
                accountingAccount.setcAccountingAccountType(mapper.treeToValue(node.get("accountingAccountType"), CAccountingAccountType.class));
                accountingAccount = accountingAccountsDao.save(accountingAccount);
            }
        }
        return accountingAccount;
    }


    @Override
    public AccountingAccounts update(AccountingAccounts accountingAccounts) {
        accountingAccountsDao.update(accountingAccounts);
        return accountingAccounts;
    }

    @Override
    public AccountingAccounts findById(Integer idAccountingAccount) {
        return accountingAccountsDao.findById(idAccountingAccount);
    }

    @Override
    public List<AccountingAccounts> findAllCategories() {
        return accountingAccountsDao.findAllCategories();
    }

    @Override
    public AccountingAccounts findByAcronym(String acronym) {
        return accountingAccountsDao.findByAcronym(acronym);
    }

    @Override
    public List<AccountingAccounts> findByLikeAcronyms(String acronyms) {
        return accountingAccountsDao.findByLikeAcronyms(acronyms);
    }
}
