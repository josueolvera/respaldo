/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.PriceEstimations;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface PriceEstimationsService {
    
    public List<PriceEstimations> saveData(String data, Users user) throws Exception;
    
    public PriceEstimations findById(int id);
    
    public PriceEstimations update(PriceEstimations pe);
    
    public List<PriceEstimations> findByIdRequest(int idRequest);
    
    public void estimationAuthorization(int idEstimation, Users user);
    
}
