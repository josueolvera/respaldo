package mx.bidg.service.impl;

import java.util.ArrayList;

import mx.bidg.dao.CBudgetSubcategoriesDao;
import mx.bidg.dao.CProductsDao;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.CProducts;
import mx.bidg.service.CProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.bidg.dao.ProductTypesProductDao;
import mx.bidg.model.AccountingAccounts;
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

    @Autowired
    CBudgetSubcategoriesDao budgetSubcategoriesDao;

    @Override
    public CProducts findById(int id) {
        return productsDao.findById(id);
    }

    @Override
    public List<CProducts> findAll() {
        return productsDao.findAll();
    }

    @Override
    public List<CProducts> findByProductTypes(AccountingAccounts accountingAccounts) {
        
        List<CProducts> list = new ArrayList<>();
        List<ProductTypesProduct> productTypesProducts = productTypesProductDao.findByProductType(accountingAccounts);
        List<CProducts> cProducts = productsDao.findAll();
        
        for(ProductTypesProduct productTypesProduct : productTypesProducts) {
            if(cProducts.contains(productTypesProduct.getProduct()) && 
                    !list.contains(productTypesProduct.getProduct())) {
                list.add(productTypesProduct.getProduct());
            }
        }
        
        return list;
    }

    @Override
    public List<CProducts> findByBudgetSubcategory(int idBudgetSubcategory) {
        return productsDao.findByBudgetSubcategory(idBudgetSubcategory);
    }

    @Override
    public CProducts save(int idBudgetSubcategory, CProducts product) {

        CBudgetSubcategories budgetSubcategory = budgetSubcategoriesDao.findById(idBudgetSubcategory);

        if (budgetSubcategory != null) {
            product.setProduct(product.getProduct().toUpperCase().trim());
            product.setIdAccessLevel(1);
            budgetSubcategory.getProducts().add(product);
            budgetSubcategoriesDao.save(budgetSubcategory);
        }

        return product;
    }
}
