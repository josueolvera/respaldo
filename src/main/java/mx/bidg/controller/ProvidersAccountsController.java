package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Accounts;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;
import mx.bidg.service.ProvidersAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author kenneth
 */
@Controller
@RequestMapping("/providers-accounts")
public class ProvidersAccountsController {

    @Autowired
    ProvidersAccountsService providersAccountsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"}, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> saveProviderAccount(@RequestBody String data) throws Exception {
        ProvidersAccounts providersAccount = providersAccountsService.save(data);
        return ResponseEntity.ok(mapper.writeValueAsString(providersAccount));

    }

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByProvider(@PathVariable Integer idProvider) throws Exception {
        List<ProvidersAccounts> list = providersAccountsService.findByProvider(idProvider);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.EmbeddedAccounts.class)
                .writeValueAsString(list));
    }

    @RequestMapping(value = "/account/{idAccount}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByAccount(@PathVariable int idAccount) throws Exception {
        ProvidersAccounts providerAccount = providersAccountsService.findByAccountsProvider(new Accounts(idAccount));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.EmbeddedAccounts.class)
                .writeValueAsString(providerAccount), HttpStatus.OK);
    }

}
