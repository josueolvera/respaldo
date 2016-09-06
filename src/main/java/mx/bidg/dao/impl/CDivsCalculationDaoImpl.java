package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CDivsCalculationDao;
import mx.bidg.model.CDivsCalculation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class CDivsCalculationDaoImpl extends AbstractDao<Integer, CDivsCalculation> implements CDivsCalculationDao {

    @Override
    public CDivsCalculation save(CDivsCalculation entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CDivsCalculation findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CDivsCalculation> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CDivsCalculation update(CDivsCalculation entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CDivsCalculation entity) {
        remove(entity);
        return true;
    }
}
