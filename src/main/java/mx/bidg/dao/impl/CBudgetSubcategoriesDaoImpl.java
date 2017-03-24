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
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@SuppressWarnings("unchecked")
@Repository
public class CBudgetSubcategoriesDaoImpl extends AbstractDao<Integer, CBudgetSubcategories> implements CBudgetSubcategoriesDao {

    @Override
    public CBudgetSubcategories save(CBudgetSubcategories entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBudgetSubcategories findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CBudgetSubcategories> findAll() {
        Criteria criteria = createEntityCriteria();
        return  criteria.list();
    }

    @Override
    public CBudgetSubcategories update(CBudgetSubcategories entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CBudgetSubcategories entity) {
        remove(entity);
        return true;
    }

    @Override
    public List <CBudgetSubcategories> findBySecondLevel(String secondLevel) {
        return createEntityCriteria()
                .add(Restrictions.eq("secondLevel",secondLevel)).list();
    }
}
