/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetCategoriesDao;
import mx.bidg.model.CBudgetCategories;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CBudgetCategoriesDaoImpl extends AbstractDao<Integer, CBudgetCategories> implements CBudgetCategoriesDao {

    @Override
    public CBudgetCategories save(CBudgetCategories entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CBudgetCategories findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CBudgetCategories> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CBudgetCategories>) criteria.list();
    }

    @Override
    public CBudgetCategories update(CBudgetCategories entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CBudgetCategories entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
