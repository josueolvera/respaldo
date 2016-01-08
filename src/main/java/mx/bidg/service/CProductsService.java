package mx.bidg.service;

import mx.bidg.model.CProducts;

import java.util.List;
import mx.bidg.model.CProductTypes;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
public interface CProductsService {
    
    public CProducts findById(int id);
    
    public List<CProducts> findAll();
    
    public List<CProducts> findByProductTypes(CProductTypes cProductType);
    
}
