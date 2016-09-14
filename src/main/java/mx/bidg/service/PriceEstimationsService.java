/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.PriceEstimations;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface PriceEstimationsService {
    
    PriceEstimations saveData(String data, Users user) throws Exception;
    
    PriceEstimations saveFileData(int idEstimation, String fileName, String filePath);
    
    PriceEstimations findById(int id);
    
    PriceEstimations update(Integer idEstimation, String data, Users user) throws Exception;
    
    List<PriceEstimations> findByIdRequest(int idRequest);
    
    PriceEstimations estimationAuthorization(int idEstimation, Users user);

    boolean delete(Integer idEstimation);
    boolean reject(Integer idEstimation);
    
    PriceEstimations findAuthorized(Integer idRequest);
    
}
