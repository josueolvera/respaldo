/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CAreas;

/**
 *
 * @author sistemask
 */
public interface CAreasService {

    List<CAreas> findAll();
    CAreas findById(Integer idArea);
    CAreas findAreaWhitRole(Integer idArea);
    List<CAreas> findBySaemFlag(Integer idArea, Integer saemFlag);
    CAreas save(CAreas area);
    CAreas update(CAreas area);
    boolean delete(CAreas area);
    
}
