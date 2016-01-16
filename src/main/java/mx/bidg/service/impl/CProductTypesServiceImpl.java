package mx.bidg.service.impl;

import java.util.ArrayList;
import mx.bidg.dao.CProductTypesDao;
import mx.bidg.model.CProductTypes;
import mx.bidg.service.CProductTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import mx.bidg.dao.RequestTypesProductDao;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.model.RequestTypesProduct;

/**
 * @author Rafael Viveros
 *         Created on 19/11/15.
 */
@Service
@Transactional
public class CProductTypesServiceImpl implements CProductTypesService {

    @Autowired
    CProductTypesDao productTypesDao;
    
    @Autowired
    RequestTypesProductDao requestTypesProductDao;

    @Override
    public CProductTypes findById(Integer id) {
        return productTypesDao.findById(id);
    }

    @Override
    public List<CProductTypes> findAll() {
        return productTypesDao.findAll();
    }

    @Override
    public List<CProductTypes> findByRequestCategoryRequestType(CRequestsCategories cRequestsCategory, 
            CRequestTypes cRequestTypes) {
        
        List<CProductTypes> list = new ArrayList<>();
        List<RequestTypesProduct> requestTypesProducts = requestTypesProductDao
                .findByRequestCategoryRequestType(cRequestsCategory, cRequestTypes);
        if(requestTypesProducts.isEmpty()) {
            return null;
        }
        
        List<CProductTypes> productTypes = productTypesDao.findAll();System.out.println(productTypes);
        
        for(RequestTypesProduct requestTypesProduct : requestTypesProducts) {
            if(productTypes.contains(requestTypesProduct.getIdProductType()) && 
                    !list.contains(requestTypesProduct.getIdProductType())) {
                list.add(requestTypesProduct.getIdProductType());
            }
        }
        
        return list;
    }
}
