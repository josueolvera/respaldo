package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CConceptBudgetDao;
import mx.bidg.model.CConceptBudget;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 07/03/2017.
 */
@Repository
public class CConceptBudgetDaoImpl extends AbstractDao<Integer,CConceptBudget> implements CConceptBudgetDao{
    @Override
    public CConceptBudget save(CConceptBudget entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CConceptBudget findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CConceptBudget> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CConceptBudget update(CConceptBudget entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CConceptBudget entity) {
        remove(entity);
        return true;
    }
}
