package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ProvidersBudgetSubcategoriesDao;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersBudgetSubcategories;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import mx.bidg.model.CBudgetSubcategories;

/**
 * Created by jolvera on 30/05/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class ProvidersBudgetSubcategoriesDaoImpl extends AbstractDao <Integer,ProvidersBudgetSubcategories> implements ProvidersBudgetSubcategoriesDao {
    @Override
    public ProvidersBudgetSubcategories save(ProvidersBudgetSubcategories entity) {
        persist(entity);
        return entity;
    }

    @Override
    public ProvidersBudgetSubcategories findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<ProvidersBudgetSubcategories> findAll() {
        Criteria criteria = createEntityCriteria();
        return criteria.list();
    }

    @Override
    public ProvidersBudgetSubcategories update(ProvidersBudgetSubcategories entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(ProvidersBudgetSubcategories entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<ProvidersBudgetSubcategories> findByProvider(Providers p) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("provider",p)).list();
    }

    @Override
    public List<ProvidersBudgetSubcategories> findByBudgetSubcategorie(CBudgetSubcategories budgetSubcategories) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("budgetSubcategory", budgetSubcategories));
        return criteria.list();
    }
}
