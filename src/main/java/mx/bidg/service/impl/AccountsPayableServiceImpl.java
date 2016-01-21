/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.AccountsPayableDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.CAccountsPayableStatus;
import mx.bidg.model.CCurrencies;
import mx.bidg.model.COperationTypes;
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
        
        JsonNode jsonList = mapper.readTree(data);
        String folio = requestsDao.findById(idRequest).getFolio();
        List<AccountsPayable> accounts = new ArrayList<>();
        AccountsPayable accountsPayable;
        
        for(JsonNode json : jsonList.get("payments")) {
            
            BigDecimal amount = json.get("amount").decimalValue();
            Integer payNum = json.get("payNum").asInt();
            Integer totalPayments = json.get("totalPayments").asInt();
            LocalDateTime dueDate = (json.get("dueDate").asText() != null)? 
                LocalDateTime.parse(json.get("dueDate").asText()) : null;
            CCurrencies currency = new CCurrencies(json.get("currency").asInt());
            
            accountsPayable = new AccountsPayable();
            accountsPayable.setFolio(folio);
            accountsPayable.setAmount(amount);
            accountsPayable.setPaidAmount(new BigDecimal(BigInteger.ZERO));
            accountsPayable.setPayNum(payNum);
            accountsPayable.setTotalPayments(totalPayments);
            //Tipo de operacion Egreso = 1
            accountsPayable.setIdOperationType(new COperationTypes(1));
            accountsPayable.setDueDate(dueDate);
            //Estatus de AccountPayable Inactiva = 1
            accountsPayable.setIdAccountPayableStatus(new CAccountsPayableStatus(1));
            accountsPayable.setIdAccessLevel(1);
            accountsPayable.setIdCurrency(currency);
            accountsPayable = accountsPayableDao.save(accountsPayable);
            accounts.add(accountsPayable);
            
        }
        
        return accounts;
    }
    
}
