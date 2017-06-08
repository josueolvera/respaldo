/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.RequestProducts;

import java.util.List;

/**
 *
 * @author sistemask
 */
public interface RequestProductsDao extends InterfaceDao<RequestProducts> {
    List<RequestProducts> findByIdRequest(Integer idRequest);
    
}
