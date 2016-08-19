package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.AccountingAccountsService;
import mx.bidg.service.CBudgetCategoriesService;
import mx.bidg.service.CBudgetSubcategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
@Controller
@RequestMapping("/accounting-accounts")
public class AccountingAccountsController {

    @Autowired
    private AccountingAccountsService accountingAccountsService;

    @Autowired
    private CBudgetCategoriesService cBudgetCategoriesService;

    @Autowired
    private CBudgetSubcategoriesService cBudgetSubcategoriesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<AccountingAccounts> accountingAccounts = accountingAccountsService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{firstLevel}/{secondLevel}/{thirdLevel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByThreeLevels(
            @PathVariable int firstLevel, @PathVariable int secondLevel, @PathVariable int thirdLevel
    ) throws IOException {
        AccountingAccounts accountingAccounts = accountingAccountsService.findByThreeLevels(
                firstLevel, secondLevel, thirdLevel
        );
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data) throws IOException{

        JsonNode node = mapper.readTree(data);
        LocalDateTime now = LocalDateTime.now();

        Integer firstLevel = node.get("firstLevel").asInt();
        Integer secondLevel = node.get("secondLevel").asInt();
        Integer thirdLevel = node.get("thirdLevel").asInt();

        String description = node.get("description").asText();

        AccountingAccounts accountingAccount;

        List<AccountingAccounts> accountingAccounts;

        if (firstLevel == 0) {
            throw new ValidationException("CUENTA CONTABLE NO VALIDA", "Cuenta contable no valida");
        } else {

            accountingAccount = new AccountingAccounts();
            if (secondLevel == 0 && thirdLevel == 0) {

                accountingAccounts = accountingAccountsService.findByFirstLevel(firstLevel);

                if (!accountingAccounts.isEmpty()) {
                    CBudgetCategories budgetCategory = new CBudgetCategories();
                    budgetCategory.setBudgetCategory(description);
                    budgetCategory.setCreationDate(now);
                    budgetCategory.setIdAccessLevel(1);

                    budgetCategory = cBudgetCategoriesService.save(budgetCategory);

                    accountingAccount.setBudgetCategory(budgetCategory);
                } else {
                    throw new ValidationException("CUENTA INEXISTENTE", "No existe una cuenta contable con el número " + firstLevel);
                }

            } else if (thirdLevel == 0) {

                accountingAccounts = accountingAccountsService.findBySecondLevel(secondLevel);

                if (!accountingAccounts.isEmpty()) {
                    CBudgetSubcategories budgetSubcategory = new CBudgetSubcategories();
                    budgetSubcategory.setBudgetSubcategory(description);
                    budgetSubcategory.setCreationDate(now);
                    budgetSubcategory.setIdAccessLevel(1);

                    budgetSubcategory = cBudgetSubcategoriesService.save(budgetSubcategory);

                    accountingAccount.setBudgetSubcategory(budgetSubcategory);
                } else {
                    throw new ValidationException("CUENTA INEXISTENTE", "No existe una cuenta contable con el número " + secondLevel);
                }

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

            accountingAccount = accountingAccountsService.save(accountingAccount);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccount), HttpStatus.OK);
    }

    @RequestMapping( value = "/{idAccountingAccount}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@PathVariable Integer idAccountingAccount, @RequestBody String data) throws IOException{

        JsonNode node = mapper.readTree(data);

        AccountingAccounts accountingAccounts = accountingAccountsService.findById(idAccountingAccount);
        accountingAccounts.setDescription(node.get("description").asText());
        accountingAccounts.setcAccountingAccountCategory(new CAccountingAccountCategory(node.get("idAccountingCategory").asInt()));
        accountingAccounts.setcAccountingAccountType(new CAccountingAccountType(node.get("idAccountingType").asInt()));
        accountingAccounts.setcAccountingAccountNature(new CAccountingAccountNature(node.get("idAccountingNature").asInt()));

        accountingAccountsService.update(accountingAccounts);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK);
    }
    
    @RequestMapping( value="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllCategories() throws IOException {
        List<AccountingAccounts> accountingAccounts = accountingAccountsService.findAllCategories();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK
        );
    }


}
