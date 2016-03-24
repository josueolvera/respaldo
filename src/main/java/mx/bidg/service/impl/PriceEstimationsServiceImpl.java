/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.PriceEstimationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PriceEstimationsServiceImpl implements PriceEstimationsService {

    @Autowired
    PriceEstimationsDao priceEstimationsDao;

    @Autowired
    PeriodicPaymentsDao periodicPaymentsDao;

    @Autowired
    AccountsPayableDao accountsPayableDao;
    
    @Autowired
    RequestsDao requestsDao;

    @Autowired
    CCurrenciesDao currenciesDao;

    @Autowired
    AccountsDao accountsDao;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public PriceEstimations saveData(String data, Users user) throws Exception {

        JsonNode json = mapper.readTree(data);

        Integer idRequest = json.get("idRequest").asInt();
        Requests request = requestsDao.findByIdFetchBudgetMonthBranch(idRequest);
        BigDecimal budgetAmount = request.getBudgetMonthBranch().getAmount();
        BigDecimal expendedAmount = request.getBudgetMonthBranch().getExpendedAmount();
        BigDecimal residualAmount = budgetAmount.subtract(expendedAmount);
        Accounts account = accountsDao.findById(json.get("idAccount").asInt());
        int idCurrency = json.get("idCurrency").asInt();
        BigDecimal rate = ((json.get("rate").decimalValue().compareTo(BigDecimal.ZERO)) == 1)? json.get("rate").decimalValue() : BigDecimal.ONE;
        BigDecimal amount = ((json.get("amount").decimalValue().compareTo(BigDecimal.ZERO)) == 1)? json.get("amount").decimalValue().divide(rate, 6, RoundingMode.DOWN) : BigDecimal.ZERO;
        String sku = json.get("sku").asText();

        PriceEstimations estimation = new PriceEstimations();
        estimation.setRequest(request);
        estimation.setAccount(account);
        estimation.setCurrency(new CCurrencies(idCurrency));
        estimation.setAmount(amount);
        estimation.setFilePath("");
        estimation.setFileName("");
        estimation.setRate(rate);
        estimation.setCreationDate(LocalDateTime.now());
        estimation.setUserEstimation(user);
        estimation.setIdAccessLevel(1);
        estimation.setEstimationStatus(new CEstimationStatus(CEstimationStatus.PENDIENTE));
        estimation.setSku(sku);
        //Si el Monto de Presupuesto es menor al de la cotizacion, OutOfBudget = true
        estimation.setOutOfBudget((residualAmount.compareTo(amount) == -1)? 1 : 0);
        estimation = priceEstimationsDao.save(estimation);

        request.setRequestStatus(new CRequestStatus(CRequestStatus.COTIZADA));
        requestsDao.update(request);

        return estimation;

    }

    @Override
    public PriceEstimations findById(int id) {
        return priceEstimationsDao.findById(id);
    }

    @Override
    public PriceEstimations update(Integer idEstimation, String data, Users user) throws Exception{
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        JsonNode json = mapper.readTree(data);
        if(estimation.getEstimationStatus().getIdEstimationStatus().equals(CEstimationStatus.PENDIENTE)) {
            Requests request = requestsDao.findByIdFetchBudgetMonthBranch(estimation.getIdRequest());
            BigDecimal budgetAmount = request.getBudgetMonthBranch().getAmount();
            BigDecimal expendedAmount = request.getBudgetMonthBranch().getExpendedAmount();
            BigDecimal residualAmount = budgetAmount.subtract(expendedAmount);
            BigDecimal rate = ((json.get("rate").decimalValue().compareTo(BigDecimal.ZERO)) == 1)? json.get("rate").decimalValue() : BigDecimal.ONE;
            BigDecimal amount = ((json.get("amount").decimalValue().compareTo(BigDecimal.ZERO)) == 1)?
                    json.get("amount").decimalValue().divide(rate, 6, RoundingMode.DOWN) : BigDecimal.ZERO;
            
            estimation.setAccount(new Accounts(json.get("idAccount").asInt()));
            estimation.setAmount(amount);
            estimation.setCurrency(new CCurrencies(json.get("idCurrency").asInt()));
            estimation.setRate(rate);
            estimation.setUserEstimation(user);
            //Si el Monto de Presupuesto es menor al de la cotizacion, OutOfBudget = true
            estimation.setOutOfBudget((residualAmount.compareTo(amount) == -1)? 1 : 0);
            estimation.setSku((json.get("sku").asText() != null) ? json.get("sku").asText() : "");
            return estimation;
        } else {
            throw new ValidationException("No se puede modificar una cotizacion ya autorizada", 
            "No se puede modificar una solicitud ya autorizada");
        }
    }

    @Override
    public List<PriceEstimations> findByIdRequest(int idRequest) {
        return priceEstimationsDao.findByIdRequest(idRequest);
    }

    @Override
    public PriceEstimations estimationAuthorization(int idEstimation, Users user) {
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        Requests request = estimation.getRequest();

        if (request.getIdRequestStatus() == CRequestStatus.APROBADA
                || request.getIdRequestStatus() == CRequestStatus.RECHAZADA) {
            throw new ValidationException("No es posible elegir una cotizacion de una Solicitud Aceptada o Rechazada",
                    "No es posible elegir una cotizacion de una Solicitud Aceptada o Rechazada", HttpStatus.FORBIDDEN);
        } else {
            String folio = request.getFolio();
            PeriodicsPayments periodicPayment = periodicPaymentsDao.findByFolio(folio);
                if ((periodicPayment != null) &&
                        (periodicPayment.getIdPeriodicPaymentStatus() == CPeriodicPaymentsStatus.INACTIVO)) {
                    if(!periodicPaymentsDao.delete(periodicPayment))
                        throw new ValidationException("No se pudo eliminar el PeriodicPayment: " + periodicPayment);
                }
            
            List<AccountsPayable> accountsPayable = accountsPayableDao.findByFolio(folio);
            for(AccountsPayable account : accountsPayable) {
                if(account.getAccountPayableStatus().getIdAccountPayableStatus().equals(CAccountsPayableStatus.INACTIVA)) {
                    if(!accountsPayableDao.delete(account))
                        throw new ValidationException("No se pudo eliminar el AccountPayable: " + account);
                }
            }

            List<PriceEstimations> list = priceEstimationsDao.findByIdRequest(request.getIdRequest());

            for (PriceEstimations e : list) {
                e.setUserAuthorization(user);
                e.setAuthorizationDate(LocalDateTime.now());

                if (e.getIdEstimation().equals(idEstimation)) {
                    e.setEstimationStatus(new CEstimationStatus(CEstimationStatus.APROBADA));
                } else {
                    e.setEstimationStatus(new CEstimationStatus(CEstimationStatus.RECHAZADA));
                }
            }
        }

        return estimation;
    }

    @Override
    public boolean delete(Integer idEstimation) {
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        if(estimation.getIdEstimationStatus() == CEstimationStatus.PENDIENTE){
            return priceEstimationsDao.delete(estimation);
        } else {
            throw new ValidationException("La cotizacion no tiene estatus de Pendiente", "Solo pueden" +
                    " eliminarse Cotizaciones Pendientes de aprobacion", HttpStatus.CONFLICT);
        }
    }

    @Override
    public PriceEstimations saveFileData(int idEstimation, String fileName, String filePath) {
        PriceEstimations estimation = priceEstimationsDao.findById(idEstimation);
        estimation.setFileName(fileName);
        estimation.setFilePath(filePath);
        return priceEstimationsDao.update(estimation);
    }
}
