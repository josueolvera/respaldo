package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    private CAccountingAccountCategoryService cAccountingAccountCategoryService;

    @Autowired
    private CAccountingAccountTypeService cAccountingAccountTypeService;

    @Autowired
    private CAccountingAccountNatureService cAccountingAccountNatureService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<AccountingAccounts> accountingAccounts = accountingAccountsService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK
        );
    }
    /*
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
    */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data , HttpSession session) throws IOException{
        Users user = (Users)session.getAttribute("user");
        AccountingAccounts accountingAccount = accountingAccountsService.save(data,user);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccount), HttpStatus.OK);
    }

    @RequestMapping( value = "/{idAccountingAccount}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@PathVariable Integer idAccountingAccount, @RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);
        AccountingAccounts accountingAccounts = accountingAccountsService.findById(idAccountingAccount);
        accountingAccounts.setAcronyms(node.get("description").asText());
        CAccountingAccountCategory caac = cAccountingAccountCategoryService.findById(node.get("accountingAccountCategory").get("idAccountingCategory").asInt());
        accountingAccounts.setcAccountingAccountCategory(caac);
        CAccountingAccountType caat = cAccountingAccountTypeService.findById(node.get("accountingAccountType").get("idAccountingType").asInt());
        accountingAccounts.setcAccountingAccountType(caat);
        CAccountingAccountNature caan = cAccountingAccountNatureService.findById(node.get("accountingAccountNature").get("idAccountingNature").asInt());
        accountingAccounts.setcAccountingAccountNature(caan);
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

    @RequestMapping(value = "/validate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByAcronym(@RequestParam(name = "acronym", required = true) String acronyms) throws IOException{
        AccountingAccounts accountingAccounts = accountingAccountsService.findByAcronym(acronyms);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK);
    }

    @RequestMapping(value = "/accounting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByAccounting(@RequestParam (name = "acronyms", required = false) String acronyms)throws  IOException{
        List<AccountingAccounts> accountingAccounts = accountingAccountsService.findByLikeAcronyms(acronyms);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK);
    }

}
