/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import mx.bidg.model.PriceEstimations;

/**
 *
 * @author sistemask
 */
public interface PriceEstimationsService {
    
    public PriceEstimations saveData(String data, String filePath) throws Exception;
    
}
