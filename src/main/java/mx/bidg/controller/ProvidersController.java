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
    private PhoneNumbersService phoneNumbersService;

    @Autowired
    private ProviderAddressService providerAddressService;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getProvidersList() throws Exception {
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(providersService.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{idProvider}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateProvider(@PathVariable int idProvider, @RequestBody String data) throws IOException {
        JsonNode jnode = mapper.readTree(data);
        Providers provider = providersService.findById(idProvider);
        provider.setProviderName(jnode.get("providerName").asText());
        provider.setBusinessName(jnode.get("businessName").asText());
        provider.setRfc(jnode.get("rfc").asText());
        provider.setAccountingaccount(jnode.get("accountingaccount").asText());
        provider.setIdAccessLevel(1);
        providersService.update(provider);

        for (JsonNode node : jnode.get("addressProvider")){
            ProviderAddress providerAddress = providerAddressService.findById(node.get("idProviderAddress").asInt());
            providerAddress.setStreet(node.get("street").asText());
            providerAddress.setCp(node.get("cp").asInt());
            providerAddress.setNumExt(node.get("numExt").asInt());
            providerAddress.setNumInt(node.get("numInt").asInt());
            providerAddress.setSettlement(new CSettlement(node.get("idSettlement").asInt()));
            providerAddress.setMunicipality(new CMunicipalities(node.get("idMunicipality").asInt()));
            providerAddress.setState(new CStates(node.get("idState").asInt()));
            providerAddress.setIdProvider(provider);
            providerAddress.setIdAccessLevel(1);

            providerAddressService.update(providerAddress);

        }

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(provider), HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> saveProvider(@RequestBody String data) throws IOException {
        System.out.println(data);
        JsonNode jnode = mapper.readTree(data);
        Providers provider = new Providers();
        provider.setProviderName(jnode.get("providerName").asText());
        provider.setBusinessName(jnode.get("businessName").asText());
        provider.setRfc(jnode.get("rfc").asText());
        provider.setAccountingaccount(jnode.get("accountingaccount").asText());
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

        for (JsonNode node : jnode.get("addressProvider")){
            ProviderAddress providerAddress = new ProviderAddress();
            providerAddress.setStreet(node.get("street").asText());
            providerAddress.setCp(node.get("cp").asInt());
            providerAddress.setNumExt(node.get("ext").asInt());
            providerAddress.setNumInt(node.get("int").asInt());
            providerAddress.setSettlement(new CSettlement(node.get("idSettlement").asInt()));
            providerAddress.setMunicipality(new CMunicipalities(node.get("idMunicipality").asInt()));
            providerAddress.setState(new CStates(node.get("idState").asInt()));
            providerAddress.setIdProvider(provider);
            providerAddress.setIdAccessLevel(1);

            providerAddressService.save(providerAddress);

        }

        for (JsonNode node : jnode.get("phoneNumbersList")){
            PhoneNumbers phone = new PhoneNumbers();
            phone.setPhoneNumber(node.get("phoneNumber").asInt());
            phone.setIdAccessLevel(1);
            phone.setIdProvider(provider);

            phoneNumbersService.save(phone);
        }

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(provider), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idProvider}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteProvider(@PathVariable int idProvider) {
        Providers provider = providersService.findById(idProvider);
        List<ProvidersAccounts> providersAccounts = providersAccountsService.findByProvider(provider);
        for (ProvidersAccounts pa : providersAccounts) {
            providersAccountsService.delete(pa);
        }
        providersService.delete(provider);
        return new ResponseEntity<>("Proveedor eliminado", HttpStatus.OK);
    }
}
