/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.dao.AccountsPayableDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.AccountsPayable;
import mx.bidg.service.AccountsPayableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountsPayableServiceImpl implements AccountsPayableService {
    
    @Autowired
    AccountsPayableDao accountsPayableDao;
    
    @Autowired
    RequestsDao requestsDao;
    
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<AccountsPayable> saveData(String data, int idRequest) throws Exception {
        
        JsonNode json = mapper.readTree(data);
        String folio = requestsDao.findById(idRequest).getFolio();
        
        return null;
    }
    
}
