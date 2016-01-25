package mx.bidg.service.impl;

import java.util.ArrayList;
import mx.bidg.dao.CRequestTypesDao;
import mx.bidg.model.CRequestTypes;
import mx.bidg.service.CRequestTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import mx.bidg.dao.RequestTypesProductDao;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.model.RequestTypesProduct;

/**
 * @author Rafael Viveros
 * Created on 18/11/15.
 */
@Service
@Transactional
public class CRequestTypesServiceImpl implements CRequestTypesService {

    @Autowired
    CRequestTypesDao requestTypesDao;
    
    @Autowired
    RequestTypesProductDao requestTypesProductDao;

    @Override
    public List<CRequestTypes> findAll() {
        return requestTypesDao.findAll();
    }

    @Override
    public CRequestTypes findById(Integer id) {
        return requestTypesDao.findById(id);
    }

    @Override
    public List<CRequestTypes> findByRequestCategory(CRequestsCategories cRequestsCategory) {
        
        List<CRequestTypes> list = new ArrayList<>();
        List<RequestTypesProduct> requestTypesProducts = requestTypesProductDao.findByRequestCategory(cRequestsCategory);
        if(requestTypesProducts.isEmpty()) {
            return null;
        }
        List<CRequestTypes> requestTypes = findAll();
        
        for(RequestTypesProduct requestTypesProduct : requestTypesProducts) {
            if(requestTypes.contains(requestTypesProduct.getRequestType()) && 
                    !list.contains(requestTypesProduct.getRequestType())) {
                list.add(requestTypesProduct.getRequestType());
            }
        }
        
        return list;
        
    }
}
