package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/providers")
public class ProvidersController {
    
    @Autowired
    private ProvidersService providersService;
    
    @Autowired
    private AccountsService accountService;
    
    @Autowired
    private ProvidersAccountsService providersAccountsService;

    @Autowired
    private ProvidersContactService providersContactService;

    @Autowired
    private ProviderAddressService providerAddressService;

    @Autowired
    private ProvidersBudgetSubcategoriesService providersBudgetSubcategoriesService;

    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private AccountingAccountsService accountingAccountsService;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getProvidersList() throws Exception {
        String response = mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(providersService.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/product-type/{idBudgetSubcategory}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByProductType(@PathVariable int idBudgetSubcategory) throws IOException {
        
        List<Providers> providers = providersService.findByBudgetSubtegorie(idBudgetSubcategory);
        return ResponseEntity.ok(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(providers)
        );
    }

    @RequestMapping(value = "/find/{idProvider}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById (@PathVariable int idProvider) throws IOException{
        Providers provider = providersService.findById(idProvider);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(provider), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idProvider}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateProvider(@PathVariable int idProvider, @RequestBody String data) throws IOException {
        JsonNode jnode = mapper.readTree(data);
        Providers provider = providersService.findById(idProvider);
        provider.setProviderName(jnode.get("providerName").asText());
        provider.setBusinessName(jnode.get("businessName").asText());
        provider.setRfc(jnode.get("rfc").asText());
        provider.setAccountingAccounts(new AccountingAccounts(jnode.get("idAccountingAccount").asInt()));
        provider.setCreditDays(jnode.get("creditDays").asInt());
        provider.setCuttingDate(jnode.get("cuttingDate").asInt());
        provider.setIdAccessLevel(1);
        providersService.update(provider);

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(provider), HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> saveProvider(@RequestBody String data) throws IOException {
        JsonNode jnode = mapper.readTree(data);
        Providers provider = new Providers();
        provider.setProviderName(jnode.get("providerName").asText());
        provider.setBusinessName(jnode.get("businessName").asText());
        provider.setRfc(jnode.get("rfc").asText());
        provider.setAccountingAccounts(new AccountingAccounts(jnode.get("idAccountingAccount").asInt()));
        provider.setCreditDays(jnode.get("creditDays").asInt());
        provider.setCuttingDate(jnode.get("cuttingDate").asInt());
        provider.setIdAccessLevel(1);
        providersService.save(provider);

        for (JsonNode node : jnode.get("providersAccountsList")) {
            ProvidersAccounts providerAccount = new ProvidersAccounts();
            providerAccount.setIdAccessLevel(1);
            Accounts account = new Accounts();
            account.setAccountNumber(node.get("accountNumber").asText());
            account.setAccountClabe(node.get("accountClabe").asText());
            account.setBank(new CBanks(node.get("idBank").asInt()));
            account.setCurrencies(new CCurrencies(node.get("idCurrency").asInt()));
            account.setAccountType(CAccountsTypes.DEFINITIVA);
            account.setIdAccessLevel(1);

            providerAccount.setAccount(account);
            providerAccount.setProvider(provider);
            accountService.save(account);
            providersAccountsService.save(providerAccount);
        }

        for (JsonNode node : jnode.get("providerAddressList")) {
            ProviderAddress providerAddress = new ProviderAddress();
            providerAddress.setStreet(node.get("street").asText());
            providerAddress.setNumExt(node.get("numExt").asText());
            providerAddress.setNumInt(node.get("numInt").asText());
            providerAddress.setAsentamiento(new CAsentamientos(node.get("idAsentamiento").asInt()));
            providerAddress.setProvider(provider);
            providerAddress.setIdAccessLevel(1);

            providerAddressService.save(providerAddress);

        }

        for (JsonNode node : jnode.get("providersContactList")) {
            ProvidersContact phone = new ProvidersContact();
            phone.setPhoneNumber(node.get("phoneNumber").asText());
            phone.setEmail(node.get("email").asText());
            phone.setName(node.get("name").asText());
            phone.setPost(node.get("post").asText());
            phone.setIdAccessLevel(1);
            phone.setProvider(provider);

            providersContactService.save(phone);
        }

        for (JsonNode node : jnode.get("providersBudgetSubcategories")){
            ProvidersBudgetSubcategories providersBudgetSubcategories = new ProvidersBudgetSubcategories();
            providersBudgetSubcategories.setBudgetSubcategory(mapper.treeToValue(node.get("budgetSubcategory"),CBudgetSubcategories.class));
            providersBudgetSubcategories.setIdAccessLevel(1);
            providersBudgetSubcategories.setProvider(provider);

            providersBudgetSubcategoriesService.save(providersBudgetSubcategories);
        }

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(provider), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idProvider}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteProvider(@PathVariable int idProvider) {
        Providers provider = providersService.findById(idProvider);
        List<ProvidersAccounts> providersAccounts = providersAccountsService.findByProvider(idProvider);
        for (ProvidersAccounts pa : providersAccounts) {
            providersAccountsService.delete(pa);
        }
        providersService.delete(provider);
        return new ResponseEntity<>("Proveedor eliminado", HttpStatus.OK);
    }

    @RequestMapping(value = "/low/{idProvider}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> lowProvider(@PathVariable int idProvider) throws Exception {

        providersService.low(idProvider);

        return new ResponseEntity<>("Proveedor eliminado", HttpStatus.OK);
    }
}
