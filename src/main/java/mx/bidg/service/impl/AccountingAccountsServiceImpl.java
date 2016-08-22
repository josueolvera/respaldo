package mx.bidg.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.AccountingAccountsDao;
import mx.bidg.dao.CBudgetCategoriesDao;
import mx.bidg.dao.CBudgetSubcategoriesDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.AccountingAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
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
    private ObjectMapper mapper;

    @Override
    public AccountingAccounts findByThreeLevels(Integer firstLevel, Integer secondLevel, Integer thirdLevel) {
        return accountingAccountsDao.findByThreeLevels(firstLevel, secondLevel, thirdLevel);
    }

    @Override
    public List<AccountingAccounts> findByFirstLevel(Integer firstLevel) {
        return accountingAccountsDao.findByFirstLevel(firstLevel);
    }

    @Override
    public List<AccountingAccounts> findBySecondLevel(Integer secondLevel) {
        return accountingAccountsDao.findBySecondLevel(secondLevel);
    }

    @Override
    public List<AccountingAccounts> findByThirdLevel(Integer thirdLevel) {
        return accountingAccountsDao.findByThirdLevel(thirdLevel);
    }

    @Override
    public List<AccountingAccounts> findByFirstAndSecondLevel(Integer firstLevel, Integer secondLevel) {
        return accountingAccountsDao.findByFirstAndSecondLevel(firstLevel, secondLevel);
    }

    @Override
    public List<AccountingAccounts> findAll() {
        return accountingAccountsDao.findAll();
    }

    @Override
    public AccountingAccounts save(String data) throws IOException {
        
        JsonNode node = mapper.readTree(data);
        LocalDateTime now = LocalDateTime.now();

        Integer firstLevel = node.get("firstLevel").asInt();
        Integer secondLevel = node.get("secondLevel").asInt();
        Integer thirdLevel = node.get("thirdLevel").asInt();

        String description = node.get("description").asText();

        AccountingAccounts accountingAccount;

        List<AccountingAccounts> accountingAccountsWithFirstLevel;

        List<AccountingAccounts> accountingAccountsWithFirstAndSecondLevel;

        if (firstLevel == 0) {
            throw new ValidationException("CUENTA CONTABLE NO VALIDA", "Cuenta contable no valida");
        } else {

            accountingAccountsWithFirstLevel = accountingAccountsDao.findByFirstLevel(firstLevel);

            accountingAccountsWithFirstAndSecondLevel = accountingAccountsDao.findByFirstAndSecondLevel(firstLevel, secondLevel);

            accountingAccount = new AccountingAccounts();

            if ((secondLevel != 0 || thirdLevel != 0) && accountingAccountsWithFirstLevel.isEmpty()) {

                throw new ValidationException("CUENTA INEXISTENTE", "No existe una cuenta contable con el número " + firstLevel);

            } else if (thirdLevel != 0 && accountingAccountsWithFirstAndSecondLevel.isEmpty()) {

                throw new ValidationException("CUENTA INEXISTENTE", "No existe una cuenta contable con el número " + firstLevel + "-" +secondLevel);

            } else if (secondLevel == 0 && thirdLevel == 0) {

                CBudgetCategories budgetCategory = new CBudgetCategories();
                budgetCategory.setBudgetCategory(description);
                budgetCategory.setCreationDate(now);
                budgetCategory.setIdAccessLevel(1);

                budgetCategory = cBudgetCategoriesDao.save(budgetCategory);

                accountingAccount.setBudgetCategory(budgetCategory);

            } else if (thirdLevel == 0) {

                CBudgetSubcategories budgetSubcategory = new CBudgetSubcategories();
                budgetSubcategory.setBudgetSubcategory(description);
                budgetSubcategory.setCreationDate(now);
                budgetSubcategory.setIdAccessLevel(1);

                budgetSubcategory = cBudgetSubcategoriesDao.save(budgetSubcategory);

                accountingAccount.setBudgetSubcategory(budgetSubcategory);

            }

            accountingAccount.setAvailable(true);
            accountingAccount.setDescription(description);
            accountingAccount.setFirstLevel(firstLevel);
            accountingAccount.setSecondLevel(secondLevel);
            accountingAccount.setThirdLevel(thirdLevel);
            accountingAccount.setIsOfRequest(0);
            accountingAccount.setcAccountingAccountCategory(mapper.treeToValue(node.get("accountingAccountCategory"), CAccountingAccountCategory.class));
            accountingAccount.setcAccountingAccountNature(mapper.treeToValue(node.get("accountingAccountNature"), CAccountingAccountNature.class));
            accountingAccount.setcAccountingAccountType(mapper.treeToValue(node.get("accountingAccountType"), CAccountingAccountType.class));

            accountingAccount = accountingAccountsDao.save(accountingAccount);
        }
        accountingAccountsDao.save(accountingAccount);

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
}
