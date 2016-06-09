/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.PriceEstimations;
import mx.bidg.model.Requests;

/**
 *
 * @author sistemask
 */
public interface PriceEstimationsDao extends InterfaceDao<PriceEstimations> {
    
    List<PriceEstimations> findByIdRequest(int idRequest);
    
    PriceEstimations findByIdFetchRequestStatus(int idEstimation);
    
    PriceEstimations findAuthorized(Requests request);
    
}
