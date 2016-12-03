package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CalculationReportDao;
import mx.bidg.model.CalculationReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
@Repository
public class CalculationReportDaoImpl extends AbstractDao<Integer, CalculationReport> implements CalculationReportDao {

    @Override
    public CalculationReport save(CalculationReport entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CalculationReport findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CalculationReport> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CalculationReport update(CalculationReport entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CalculationReport entity) {
        remove(entity);
        return true;
    }
}
