/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.dao.ProvidersAccountsDao;
import mx.bidg.model.Accounts;
import mx.bidg.model.CAccountsTypes;
import mx.bidg.model.CBanks;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;
import mx.bidg.service.ProvidersAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProvidersAccountsServiceImpl implements ProvidersAccountsService {
    
    @Autowired
    ProvidersAccountsDao dao;
    
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<ProvidersAccounts> findByProvider(Providers provider) {
        return dao.findByProvider(provider);
    }

    @Override
    public ProvidersAccounts save(String data) throws Exception {
        
        JsonNode jsonRequest = mapper.readTree(data);
        String providerName = jsonRequest.get("provider").asText();
        String rfc = jsonRequest.get("rfc").asText();
        Integer idBank = jsonRequest.get("bank").asInt();
        String accountNumber = jsonRequest.get("account").asText();
        String clabe = jsonRequest.get("clabe").asText();
        
        Providers provider = new Providers();
        provider.setIdAccessLevel(1);
        provider.setProviderName(providerName);
        provider.setRfc(rfc);
        
        Accounts account = new Accounts();
        account.setAccountClabe(clabe);
        account.setAccountNumber(accountNumber);
        account.setIdAccessLevel(1);
        //Tipo de cuenta definitiva
        account.setAccountType(new CAccountsTypes(1));
        account.setBank(new CBanks(idBank));
        
        ProvidersAccounts providersAccount = new ProvidersAccounts();
        providersAccount.setIdAccessLevel(1);
        providersAccount.setAccount(account);
        providersAccount.setProvider(provider);
        
        return dao.save(providersAccount);
    }
    
}
