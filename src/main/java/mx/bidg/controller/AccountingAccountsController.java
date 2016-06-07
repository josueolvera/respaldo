package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CDistributors;
import mx.bidg.service.AccountingAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<AccountingAccounts> accountingAccounts = accountingAccountsService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idDistributor}/{firstLevel}/{secondLevel}/{thirdLevel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByThreeLevels(
            @PathVariable int idDistributor, @PathVariable int firstLevel, @PathVariable int secondLevel, @PathVariable int thirdLevel
    ) throws IOException {
        AccountingAccounts accountingAccounts = accountingAccountsService.findByThreeLevels(
                new CDistributors(idDistributor), firstLevel, secondLevel, thirdLevel
        );
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(accountingAccounts),
                HttpStatus.OK
        );
    }

}
