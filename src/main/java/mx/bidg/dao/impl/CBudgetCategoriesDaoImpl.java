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
import org.apache.xmlbeans.XmlObject;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@SuppressWarnings("unchecked")
@Repository
public class CBudgetCategoriesDaoImpl extends AbstractDao<Integer, CBudgetCategories> implements CBudgetCategoriesDao {

    @Override
    public CBudgetCategories save(CBudgetCategories entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBudgetCategories findById(int idBudgetCategory) {
        return getByKey(idBudgetCategory);
    }


    @Override
    public List<CBudgetCategories> findAll() {
        Criteria criteria = createEntityCriteria();
        return criteria.list();
    }

    @Override
    public CBudgetCategories update(CBudgetCategories entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CBudgetCategories entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CBudgetCategories> findAllRequest() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("isRequest", 1));
        return criteria.list();
    }

    @Override
    public CBudgetCategories findByFirstLevel(String firstLevel) {
        return (CBudgetCategories) createEntityCriteria()
                .add(Restrictions.eq("firstLevel",firstLevel)).uniqueResult();
    }

}
