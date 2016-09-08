package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersBudgetSubcategories;
import mx.bidg.service.ProvidersBudgetSubcategoriesService;
import mx.bidg.service.ProvidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.ProvidersAccounts;
import mx.bidg.service.AccountingAccountsService;
import mx.bidg.service.ProvidersAccountsService;

/**
 * Created by jolvera on 30/05/16.
 */
@Controller
@RequestMapping("providers-budget-subcategories")
public class ProvidersBudgetSubcategoriesController {

    @Autowired
    ProvidersService providersService;

    @Autowired
    ProvidersBudgetSubcategoriesService providersBudgetSubcategoriesService;

    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private AccountingAccountsService accountingAccountsService;
    
    @Autowired
    private ProvidersBudgetSubcategoriesService budgetSubcategoriesService;

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<String> addProviderProduct(@PathVariable int idProvider, @RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        Providers provider = providersService.findById(idProvider);
        ProvidersBudgetSubcategories providersBudgetSubcategories = new ProvidersBudgetSubcategories();
        providersBudgetSubcategories.setBudgetSubcategory(mapper.treeToValue(node.get("budgetSubcategory"), CBudgetSubcategories.class));
        providersBudgetSubcategories.setProvider(provider);
        providersBudgetSubcategories.setIdAccessLevel(1);
        providersBudgetSubcategories = providersBudgetSubcategoriesService.save(providersBudgetSubcategories);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(providersBudgetSubcategories), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idProvidersProductsTypes}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteProviderProduct(@PathVariable int idProvidersProductsTypes) throws IOException {
        ProvidersBudgetSubcategories providersBudgetSubcategories = providersBudgetSubcategoriesService.findById(idProvidersProductsTypes);
        providersBudgetSubcategoriesService.delete(providersBudgetSubcategories);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody public ResponseEntity<String> productsByProvider(@PathVariable int idProvider)throws Exception{
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(providersBudgetSubcategoriesService
                .findByProvider(new Providers(idProvider)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/providers/{idAccountingAccount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByProductType(@PathVariable int idAccountingAccount) throws IOException {
        
        AccountingAccounts accountingAccounts = accountingAccountsService.findById(idAccountingAccount);
        List<ProvidersBudgetSubcategories> providers = budgetSubcategoriesService.findByBudgetSubcategorie(accountingAccounts.getBudgetSubcategory());
        for(ProvidersBudgetSubcategories pbs : providers){
            pbs.setProviders(providersService.findById(pbs.getIdProvider()));
        }
        
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(providers),
                HttpStatus.OK
        );
    }
}
