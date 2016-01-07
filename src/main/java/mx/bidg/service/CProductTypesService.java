package mx.bidg.service;

import mx.bidg.model.CProductTypes;

import java.util.List;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.CRequestsCategories;

/**
 * @author Rafael Viveros
 * Created on 19/11/15.
 */
public interface CProductTypesService {
    CProductTypes findById(Integer id);
    List<CProductTypes> findAll();
    public List<CProductTypes> findByRequestCategoryRequestType(CRequestsCategories cRequestsCategory, 
            CRequestTypes cRequestTypes);
}
