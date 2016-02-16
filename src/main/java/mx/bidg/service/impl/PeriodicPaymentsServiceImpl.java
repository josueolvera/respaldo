/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import mx.bidg.dao.PeriodicPaymentsDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.CCurrencies;
import mx.bidg.model.CPeriodicPaymentsStatus;
import mx.bidg.model.CPeriods;
import mx.bidg.model.PeriodicsPayments;
import mx.bidg.service.PeriodicPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PeriodicPaymentsServiceImpl implements PeriodicPaymentsService {
    
    @Autowired
    PeriodicPaymentsDao periodicPaymentsDao;
    
    @Autowired
    RequestsDao requestsDao;
    
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public PeriodicsPayments saveData(String data) throws Exception {
        
        JsonNode json = mapper.readTree(data);
        String folio = json.get("folio").asText();
        CPeriods period = new CPeriods(json.get("period").asInt());
        CCurrencies currency = new CCurrencies(json.get("currency").asInt());
        BigDecimal amount = json.get("amount").decimalValue();
        LocalDateTime initialDate = LocalDateTime.parse(json.get("initialDate").asText());
        LocalDateTime dueDate = (json.get("dueDate").asText() != null)? 
                LocalDateTime.parse(json.get("dueDate").asText()) : null;
        
        PeriodicsPayments periodicsPayment = new PeriodicsPayments();
        periodicsPayment.setPeriod(period);
        periodicsPayment.setFolio(folio);
        periodicsPayment.setAmount(amount);
        periodicsPayment.setInitialDate(initialDate);
        periodicsPayment.setDueDate(dueDate);
        //Ningun pago realizado de este servicio
        periodicsPayment.setPaymentNum(0);
        //Se crea el PeriodicsPayment con Status 1 = Inactivo
        periodicsPayment.setPeriodicPaymentStatus(new CPeriodicPaymentsStatus(1));
        periodicsPayment.setIdAccessLevel(1);
        periodicsPayment.setCurrency(currency);
        periodicsPayment = periodicPaymentsDao.save(periodicsPayment);
        
        return periodicsPayment;
    }
    
}
