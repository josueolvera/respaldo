package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.AccountingAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

        AccountingAccounts accountingAccounts = new AccountingAccounts();
        accountingAccounts.setAvailable(false);
        accountingAccounts.setDescription(node.get("description").asText());
        accountingAccounts.setFirstLevel(node.get("firstLevel").asInt());
        accountingAccounts.setSecondLevel(node.get("secondLevel").asInt());
        accountingAccounts.setThirdLevel(node.get("thirdLevel").asInt());
        accountingAccounts.setcAccountingAccountCategory(new CAccountingAccountCategory(node.get("idAccountingCategory").asInt()));
        accountingAccounts.setcAccountingAccountNature(new CAccountingAccountNature(node.get("idAccountingNature").asInt()));
        accountingAccounts.setcAccountingAccountType(new CAccountingAccountType(node.get("idAccountingType").asInt()));

        accountingAccountsService.save(accountingAccounts);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK);
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
