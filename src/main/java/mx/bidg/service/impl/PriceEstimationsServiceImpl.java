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
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.AccountsPayableDao;
import mx.bidg.dao.PeriodicPaymentsDao;
import mx.bidg.dao.PriceEstimationsDao;
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

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<PriceEstimations> saveData(String data, Users user) throws Exception {

        JsonNode jsonList = mapper.readTree(data);
        List<PriceEstimations> estimations = new ArrayList<>();

        for (JsonNode json : jsonList.get("estimations")) {
            int idRequest = json.get("idRequest").asInt();
            int idAccount = json.get("idAccount").asInt();
            int idCurrency = json.get("idCurrency").asInt();
            BigDecimal amount = json.get("amount").decimalValue();
            String sku = json.get("sku").asText();

            PriceEstimations estimation = new PriceEstimations();
            estimation.setIdRequest(new Requests(idRequest));
            estimation.setIdAccount(new Accounts(idAccount));
            estimation.setCurrency(new CCurrencies(idCurrency));
            estimation.setAmount(amount);
            estimation.setFilePath("");
            estimation.setFileName("");
            estimation.setUserEstimation(user);
            estimation.setIdAccessLevel(1);
            //Por defecto, las cotizaciones se guardan como pendientes (1)
            estimation.setIdEstimationStatus(new CEstimationStatus(1));
            estimation.setSku(sku);
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
    public PriceEstimations update(PriceEstimations pe) {
        return priceEstimationsDao.update(pe);
    }

    @Override
    public List<PriceEstimations> findByIdRequest(int idRequest) {
        return priceEstimationsDao.findByIdRequest(idRequest);
    }

    @Override
    public void estimationAuthorization(int idEstimation, Users user) {
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        Requests request = estimation.getIdRequest();

        //Verificar que la solicitud sigue pendiente (status 1) para poder modificar
        if (request.getIdRequestStatus().getIdRequestStatus().equals(1)) {
            
            String folio = request.getFolio();
            //Verificar si hay PeriodicPayments asociados a este request con status Inactivo(1) para eliminarlos
            List<PeriodicsPayments> periodicPayments = periodicPaymentsDao.findByFolio(folio);
            for (PeriodicsPayments payment : periodicPayments) {
                if (payment.getIdPeriodicPaymentStatus().getIdPeriodicPaymentStatus().equals(1)) {
                    if(!periodicPaymentsDao.delete(payment))
                        throw new ValidationException("No se pudo eliminar el PeriodicPayment: " + payment);
                }
            }
            
            //Verificar si hay AccountsPayable asociados a este request con status Inactivo(1) para eliminarlos
            List<AccountsPayable> accountsPayable = accountsPayableDao.findByFolio(folio);
            for(AccountsPayable account : accountsPayable) {
                if(account.getIdAccountPayableStatus().getIdAccountPayableStatus().equals(1)) {
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
                    e.setIdEstimationStatus(new CEstimationStatus(2));
                } else {
                    //Cotizacion Rechazada = 3
                    e.setIdEstimationStatus(new CEstimationStatus(3));
                }
            }

        } else {
            throw new ValidationException("No es posible elegir una cotizacion de una Solicitud Aceptada o Rechazada");
        }

    }

}
