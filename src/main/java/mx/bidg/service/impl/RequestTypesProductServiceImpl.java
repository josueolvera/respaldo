/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.dao.RequestTypesProductDao;
import mx.bidg.model.CProductTypes;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.model.RequestTypesProduct;
import mx.bidg.service.RequestTypesProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RequestTypesProductServiceImpl implements RequestTypesProductService {
    
    @Autowired
    RequestTypesProductDao dao;

    @Override
    public RequestTypesProduct findById(int id) {
        return dao.findById(id);
    }

    @Override
    public RequestTypesProduct findByCombination(int idRequestCategory, int idRequestType, int idProductType) {
        return dao.findByCombination(new CRequestsCategories(idRequestCategory), new CRequestTypes(idRequestType), 
                new CProductTypes(idProductType));
    }

    @Override
    public List<RequestTypesProduct> findByRequestType(CRequestTypes cRequestTypes) {
        return dao.findByRequestType(cRequestTypes);
    }

}
