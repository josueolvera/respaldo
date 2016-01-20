/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.PriceEstimations;

/**
 *
 * @author sistemask
 */
public interface PriceEstimationsDao extends InterfaceDao<PriceEstimations> {
    
    public List<PriceEstimations> findByIdRequest(int idRequest);
    
    public PriceEstimations findByIdFetchRequest(int idEstimation);
    
}
