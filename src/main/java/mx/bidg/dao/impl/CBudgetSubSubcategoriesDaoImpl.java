package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetSubSubcategoriesDao;
import mx.bidg.model.CBudgetSubSubcategories;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 14/02/2017.
 */
@Repository
public class CBudgetSubSubcategoriesDaoImpl extends AbstractDao<Integer,CBudgetSubSubcategories> implements CBudgetSubSubcategoriesDao {
    @Override
    public CBudgetSubSubcategories save(CBudgetSubSubcategories entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBudgetSubSubcategories findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CBudgetSubSubcategories> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CBudgetSubSubcategories update(CBudgetSubSubcategories entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(CBudgetSubSubcategories entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CBudgetSubSubcategories> findByThirdLevel(String thirdLevel) {
        return createEntityCriteria()
                .add(Restrictions.eq("thirdLevel",thirdLevel)).list();
    }
}
