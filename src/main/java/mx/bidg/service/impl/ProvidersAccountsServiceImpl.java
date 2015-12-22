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
        String provider = jsonRequest.get("provider").asText();
        String rfc = jsonRequest.get("rfc").asText();
        Integer idBank = jsonRequest.get("bank").asInt();
        String account = jsonRequest.get("account").asText();
        String clabe = jsonRequest.get("clabe").asText();
        
        
        
        return null;
    }
    
}
