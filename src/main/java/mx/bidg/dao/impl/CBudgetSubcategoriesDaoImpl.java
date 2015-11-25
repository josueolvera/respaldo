/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetSubcategoriesDao;
import mx.bidg.model.CBudgetSubcategories;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CBudgetSubcategoriesDaoImpl extends AbstractDao<Integer, CBudgetSubcategories> implements CBudgetSubcategoriesDao {

    @Override
    public CBudgetSubcategories save(CBudgetSubcategories entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CBudgetSubcategories findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CBudgetSubcategories> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CBudgetSubcategories>) criteria.list();
    }

    @Override
    public CBudgetSubcategories update(CBudgetSubcategories entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CBudgetSubcategories entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
