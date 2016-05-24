/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.AccountsPayableDao;
import mx.bidg.dao.CPeriodsDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.*;
import mx.bidg.service.AccountsPayableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountsPayableServiceImpl implements AccountsPayableService {
    
    @Autowired
    private AccountsPayableDao accountsPayableDao;
    
    @Autowired
    private RequestsDao requestsDao;

    @Autowired
    private CPeriodsDao periodsDao;
    
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public List<AccountsPayable> findByFolio(String folio) {
        return accountsPayableDao.findByFolio(folio);
    }

    @Override
    public List<AccountsPayable> updatePeriodic(String folio, String data) throws IOException {
        List<AccountsPayable> accountsPayables = findByFolio(folio);
        for (AccountsPayable accountPayable : accountsPayables) {
            if (accountPayable.getAccountPayableStatus().equals(CAccountsPayableStatus.INACTIVA)) {
                accountsPayableDao.delete(accountPayable);
            }
        }

        accountsPayables = new ArrayList<>();
        JsonNode node = mapper.readTree(data);
        LocalDateTime initialDate = LocalDateTime.parse(node.get("initialDate").asText());
        CPeriods period = periodsDao.findById(node.get("idPeriod").asInt());
        BigDecimal amount = node.get("amount").decimalValue();
        CCurrencies currency = new CCurrencies(node.get("idCurrency").asInt());
        BigDecimal rate = node.get("rate").decimalValue();
        Integer totalPayments = node.get("totalPayments").asInt();
        Period datePeriod = Period.of(period.getYears(), period.getMonths(), period.getDays());

        for (Integer paymentNumber = 0; paymentNumber < totalPayments; paymentNumber++) {
            AccountsPayable accountPayable = new AccountsPayable();
            Period currentPeriod = datePeriod.multipliedBy(paymentNumber);
            accountPayable.setFolio(folio);
            accountPayable.setAmount(amount);
            accountPayable.setPaidAmount(BigDecimal.ZERO);
            accountPayable.setPayNum(paymentNumber + 1);
            accountPayable.setTotalPayments(totalPayments);
            accountPayable.setCreationDate(LocalDateTime.now());
            accountPayable.setDueDate(initialDate.plus(currentPeriod));
            accountPayable.setAccountPayableStatus(CAccountsPayableStatus.INACTIVA);
            accountPayable.setOperationType(COperationTypes.EGRESO);
            accountPayable.setCurrency(currency);
            accountPayable.setRate(rate);
            accountPayable.setIdAccessLevel(1);

            accountsPayableDao.save(accountPayable);
            accountsPayables.add(accountPayable);
        }

        return accountsPayables;
    }

    @Override
    public boolean delete(AccountsPayable accountPayable) {
        accountsPayableDao.delete(accountPayable);
        return true;
    }

    @Override
    public AccountsPayable save(AccountsPayable accountPayable) {
        accountsPayableDao.save(accountPayable);
        return accountPayable;
    }

    @Override
    public List<AccountsPayable> update(String folio, String data) throws Exception {
        List<AccountsPayable> accounts = findByFolio(folio);
        for (AccountsPayable payable : accounts) {
            if (payable.getAccountPayableStatus().equals(CAccountsPayableStatus.INACTIVA)) {
                accountsPayableDao.delete(payable);
            }
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
            accountsPayable.setAccountPayableStatus(CAccountsPayableStatus.INACTIVA);
            accountsPayable.setIdAccessLevel(1);
            accountsPayable.setCurrency(currency);
            accountsPayable = accountsPayableDao.save(accountsPayable);
            accounts.add(accountsPayable);

        }
        return accounts;
    }

    @Override
    public List<AccountsPayable> findAll() {
        return accountsPayableDao.findAll();
    }


}
