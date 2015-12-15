/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.RequestTypesProduct;

/**
 *
 * @author sistemask
 */
public interface RequestTypesProductDao extends InterfaceDao<RequestTypesProduct> {
    
    public RequestTypesProduct findByCombination(int idRequestCategory, int idRequestType, int idProductType);
    
}
