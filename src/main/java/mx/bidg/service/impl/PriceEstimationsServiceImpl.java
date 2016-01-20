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
import java.util.List;
import mx.bidg.dao.PriceEstimationsDao;
import mx.bidg.model.Accounts;
import mx.bidg.model.CCurrencies;
import mx.bidg.model.CEstimationStatus;
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
    
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public PriceEstimations saveData(String data, Users user) throws Exception {
        
        JsonNode json = mapper.readTree(data);
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
        return estimation;
        
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
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequest(idEstimation);
        List<PriceEstimations> list = priceEstimationsDao.findByIdRequest(estimation.getIdRequest().getIdRequest());
        
        //Rechazar todas las cotizaciones de la solicitud
        for(PriceEstimations e : list) {
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
        
    }
    
}
