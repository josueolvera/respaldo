/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ProductTypesProductDao;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.ProductTypesProduct;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProductTypesProductDaoImpl extends AbstractDao<Integer, ProductTypesProduct> implements ProductTypesProductDao {

    @Override
    public ProductTypesProduct save(ProductTypesProduct entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductTypesProduct findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProductTypesProduct> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductTypesProduct update(ProductTypesProduct entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(ProductTypesProduct entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<ProductTypesProduct> findByProductType(AccountingAccounts accountingAccounts ) {
        return (List<ProductTypesProduct>) createEntityCriteria()
                .add(Restrictions.eq("accountingAccounts", accountingAccounts ))
                .setFetchMode("product", FetchMode.JOIN)
                .list();
    }
    
}
