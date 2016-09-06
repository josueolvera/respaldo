package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RulesCalculationDao;
import mx.bidg.model.RulesCalculation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class RulesCalculationDaoImpl extends AbstractDao<Integer, RulesCalculation> implements RulesCalculationDao {

    @Override
    public RulesCalculation save(RulesCalculation entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RulesCalculation findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RulesCalculation> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RulesCalculation update(RulesCalculation entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RulesCalculation entity) {
        remove(entity);
        return true;
    }
}
