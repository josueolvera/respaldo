package mx.bidg.service.impl;

import java.util.ArrayList;
import mx.bidg.dao.CProductsDao;
import mx.bidg.model.CProducts;
import mx.bidg.service.CProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import mx.bidg.dao.ProductTypesProductDao;
import mx.bidg.model.CProductTypes;
import mx.bidg.model.ProductTypesProduct;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
@Service
@Transactional
public class CProductsServiceImpl implements CProductsService {

    @Autowired
    CProductsDao productsDao;
    
    @Autowired
    ProductTypesProductDao productTypesProductDao;

    @Override
    public CProducts findById(int id) {
        return productsDao.findById(id);
    }

    @Override
    public List<CProducts> findAll() {
        return productsDao.findAll();
    }

    @Override
    public List<CProducts> findByProductTypes(CProductTypes cProductType) {
        
        List<CProducts> list = new ArrayList<>();
        List<ProductTypesProduct> productTypesProducts = productTypesProductDao.findByProductType(cProductType);
        List<CProducts> cProducts = productsDao.findAll();
        
        for(ProductTypesProduct productTypesProduct : productTypesProducts) {
            if(cProducts.contains(productTypesProduct.getProduct()) && 
                    !list.contains(productTypesProduct.getProduct())) {
                list.add(productTypesProduct.getProduct());
            }
        }
        
        return list;
    }
}
