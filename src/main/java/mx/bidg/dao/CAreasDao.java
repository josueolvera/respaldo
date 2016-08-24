/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.CAreas;

import java.util.List;

/**
 *
 * @author sistemask
 */
public interface CAreasDao extends InterfaceDao<CAreas> {

    CAreas findAreaWithRoles (Integer idArea);
    List<CAreas> findBySaemFlag(Integer idArea,Integer saemFlag);
    
}
