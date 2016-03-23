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
import java.time.format.DateTimeFormatter;
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
    public List<AccountsPayable> findByFolio(String folio) {
        return accountsPayableDao.findByFolio(folio);
    }

    @Override
    public List<AccountsPayable> update(String folio, String data) throws Exception {
        List<AccountsPayable> accounts = findByFolio(folio);
        for(AccountsPayable payable : accounts) {
            accountsPayableDao.delete(payable);
        }

        AccountsPayable accountsPayable;
        accounts = new ArrayList<>();
        JsonNode jsonList = mapper.readTree(data);
        for(JsonNode json : jsonList) {

            BigDecimal amount = json.get("amount").decimalValue();
            BigDecimal rate = json.get("rate").decimalValue();
            Integer payNum = json.get("payNum").asInt();
            Integer totalPayments = json.get("totalPayments").asInt();
            LocalDateTime dueDate = (json.get("dueDate") == null || json.findValue("dueDate").asText().equals("")) ? null :
                    LocalDateTime.parse(json.get("dueDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
            CCurrencies currency = new CCurrencies(json.get("idCurrency").asInt());

            accountsPayable = new AccountsPayable();
            accountsPayable.setFolio(folio);
            accountsPayable.setAmount(amount);
            accountsPayable.setRate(rate);
            accountsPayable.setPaidAmount(new BigDecimal(BigInteger.ZERO));
            accountsPayable.setPayNum(payNum);
            accountsPayable.setTotalPayments(totalPayments);
            //Tipo de operacion Egreso = 1
            accountsPayable.setOperationType(new COperationTypes(1));
            accountsPayable.setCreationDate(LocalDateTime.now());
            accountsPayable.setDueDate(dueDate);
            //Estatus de AccountPayable Inactiva = 1
            accountsPayable.setAccountPayableStatus(new CAccountsPayableStatus(1));
            accountsPayable.setIdAccessLevel(1);
            accountsPayable.setCurrency(currency);
            accountsPayable = accountsPayableDao.save(accountsPayable);
            accounts.add(accountsPayable);

        }
        return accounts;
    }

}
