package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.AccountsService;
import mx.bidg.service.ProvidersAccountsService;
import mx.bidg.service.ProvidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private ProvidersService providersService;

    @Autowired
    private ProvidersAccountsService providersAccountsService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> accountsByProvider(@PathVariable int idProvider) throws Exception {
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(accountsService
                .findByProvider(new Providers(idProvider)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> addProviderAccount(@PathVariable int idProvider, @RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        Providers provider = providersService.findById(idProvider);
        Accounts account = new Accounts();
        account.setAccountNumber(node.get("accountNumber").asText());
        account.setAccountClabe(node.get("accountClabe").asText());
        account.setBank(new CBanks(node.get("idBank").asInt()));
        account.setCurrencies(new CCurrencies(node.get("idCurrency").asInt()));
        account.setAccountType(CAccountsTypes.DEFINITIVA);
        account.setIdAccessLevel(1);
        accountsService.addAccountForProvider(provider, account);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(account), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idAccount}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteAccount(@PathVariable int idAccount) throws IOException {
        ProvidersAccounts providersAccounts = providersAccountsService.findByAccountsProvider(new Accounts(idAccount));
        providersAccountsService.delete(providersAccounts);
        accountsService.delete(new Accounts(idAccount));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{idUser}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> accountsByUser(@PathVariable int idUser) throws Exception {
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString("");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/low/{idAccount}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> lowAccount(@PathVariable int idAccount) throws Exception {

        accountsService.low(idAccount);

        return new ResponseEntity<>("Cuenta eliminada", HttpStatus.OK);
    }
}
