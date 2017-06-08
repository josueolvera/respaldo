/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.PriceEstimations;

import java.util.List;

/**
 *
 * @author sistemask
 */
public interface PriceEstimationsDao extends InterfaceDao<PriceEstimations> {
    
    List<PriceEstimations> findByIdRequest(int idRequest);
    
    PriceEstimations findByIdFetchRequestStatus(int idEstimation);
    
    PriceEstimations findAuthorized(Integer idRequest);

    List<PriceEstimations> findEstimationsNotSelectedByRequest(Integer idRequest, Integer idEstimation);

}
