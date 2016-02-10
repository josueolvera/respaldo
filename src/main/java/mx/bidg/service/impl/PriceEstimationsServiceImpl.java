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
import mx.bidg.model.Accounts;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.CCurrencies;
import mx.bidg.model.CEstimationStatus;
import mx.bidg.model.PeriodicsPayments;
import mx.bidg.model.PriceEstimations;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;
import mx.bidg.service.PriceEstimationsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<PriceEstimations> saveData(String data, Users user) throws Exception {

        JsonNode jsonList = mapper.readTree(data);
        List<PriceEstimations> estimations = new ArrayList<>();

        for (JsonNode json : jsonList) {
            Integer idRequest = json.get("idRequest").asInt();
            Requests request = requestsDao.findByIdFetchBudgetMonthBranch(idRequest);
            BigDecimal budgetAmount = request.getBudgetMonthBranch().getAmount();
            BigDecimal expendedAmount = request.getBudgetMonthBranch().getExpendedAmount();
            BigDecimal residualAmount = budgetAmount.subtract(expendedAmount);
            int idAccount = json.get("idAccount").asInt();
            int idCurrency = json.get("idCurrency").asInt();
            BigDecimal rate = ((json.get("rate").decimalValue().compareTo(BigDecimal.ZERO)) == 1)? json.get("rate").decimalValue() : BigDecimal.ONE;
            BigDecimal amount = ((json.get("amount").decimalValue().compareTo(BigDecimal.ZERO)) == 1)? json.get("amount").decimalValue().divide(rate, 6, RoundingMode.DOWN) : BigDecimal.ZERO;
            String sku = json.get("sku").asText();

            PriceEstimations estimation = new PriceEstimations();
            estimation.setRequest(request);
            estimation.setAccount(new Accounts(idAccount));
            estimation.setCurrency(new CCurrencies(idCurrency));
            estimation.setAmount(amount);
            estimation.setFilePath("");
            estimation.setFileName("");
            estimation.setUserEstimation(user);
            estimation.setIdAccessLevel(1);
            //Por defecto, las cotizaciones se guardan como pendientes (1)
            estimation.setEstimationStatus(new CEstimationStatus(1));
            estimation.setSku(sku);
            //Si el Monto de Presupuesto es menor al de la cotizacion, OutOfBudget = true
            estimation.setOutOfBudget((residualAmount.compareTo(amount) == -1)? 1 : 0);
            estimation = priceEstimationsDao.save(estimation);
            estimations.add(estimation);
        }

        return estimations;

    }

    @Override
    public PriceEstimations findById(int id) {
        return priceEstimationsDao.findById(id);
    }

    @Override
    public PriceEstimations update(PriceEstimations pe) throws Exception{
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(pe.getIdEstimation());
        //Verifica que el estatus de la solicitud sea Pendiente (1)
        if(estimation.getEstimationStatus().getIdEstimationStatus().equals(1)) {
            Requests request = requestsDao.findByIdFetchBudgetMonthBranch(estimation.getIdRequest());
            BigDecimal budgetAmount = request.getBudgetMonthBranch().getAmount();
            BigDecimal expendedAmount = request.getBudgetMonthBranch().getExpendedAmount();
            BigDecimal residualAmount = budgetAmount.subtract(expendedAmount);
            BigDecimal rate = ((pe.getRate().compareTo(BigDecimal.ZERO)) == 1)? pe.getRate() : BigDecimal.ONE;
            BigDecimal amount = ((pe.getAmount().compareTo(BigDecimal.ZERO)) == 1)? pe.getAmount().divide(rate, 6, RoundingMode.DOWN) : BigDecimal.ZERO;
            
            estimation.setAccount(new Accounts(pe.getIdAccount()));
            estimation.setAmount(amount);
            estimation.setCurrency(new CCurrencies(pe.getIdCurrency()));
            estimation.setRate(rate);
            //Si el Monto de Presupuesto es menor al de la cotizacion, OutOfBudget = true
            estimation.setOutOfBudget((residualAmount.compareTo(amount) == -1)? 1 : 0);
            estimation.setSku(pe.getSku());
            estimation.setUserEstimation(new Users(pe.getIdUserEstimation()));
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
    public void estimationAuthorization(int idEstimation, Users user) {
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        Requests request = estimation.getRequest();

        //Verificar que la solicitud sigue pendiente (status 1) para poder modificar
        if (request.getRequestStatus().getIdRequestStatus().equals(1)) {
            
            String folio = request.getFolio();
            //Verificar si hay PeriodicPayments asociados a este request con status Inactivo(1) para eliminarlos
            List<PeriodicsPayments> periodicPayments = periodicPaymentsDao.findByFolio(folio);
            for (PeriodicsPayments payment : periodicPayments) {
                if (payment.getPeriodicPaymentStatus().getIdPeriodicPaymentStatus().equals(1)) {
                    if(!periodicPaymentsDao.delete(payment))
                        throw new ValidationException("No se pudo eliminar el PeriodicPayment: " + payment);
                }
            }
            
            //Verificar si hay AccountsPayable asociados a este request con status Inactivo(1) para eliminarlos
            List<AccountsPayable> accountsPayable = accountsPayableDao.findByFolio(folio);
            for(AccountsPayable account : accountsPayable) {
                if(account.getAccountPayableStatus().getIdAccountPayableStatus().equals(1)) {
                    if(!accountsPayableDao.delete(account))
                        throw new ValidationException("No se pudo eliminar el AccountPayable: " + account);
                }
            }

            List<PriceEstimations> list = priceEstimationsDao.findByIdRequest(request.getIdRequest());

            for (PriceEstimations e : list) {
                e.setUserAuthorization(user);
                e.setAuthorizationDate(LocalDateTime.now());

                if (e.getIdEstimation().equals(idEstimation)) {
                    //Cotizacion Aceptada = 2
                    e.setEstimationStatus(new CEstimationStatus(2));
                } else {
                    //Cotizacion Rechazada = 3
                    e.setEstimationStatus(new CEstimationStatus(3));
                }
            }

        } else {
            throw new ValidationException("No es posible elegir una cotizacion de una Solicitud Aceptada o Rechazada", 
                "No es posible elegir una cotizacion de una Solicitud Aceptada o Rechazada");
        }

    }

    @Override
    public PriceEstimations saveFileData(int idEstimation, String fileName, String filePath) {
        PriceEstimations estimation = new PriceEstimations(idEstimation);
        estimation.setFileName(fileName);
        estimation.setFilePath(filePath);
        return priceEstimationsDao.update(estimation);
    }

}
