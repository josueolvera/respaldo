package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CPerceptionsDeductionsDao;
import mx.bidg.model.CPerceptionsDeductions;
import mx.bidg.model.DistributorPerceptionDeduction;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Repository
public class CPerceptionsDeductionsDaoImpl extends AbstractDao<Integer, CPerceptionsDeductions> implements CPerceptionsDeductionsDao {

    @Override
    public CPerceptionsDeductions save(CPerceptionsDeductions entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CPerceptionsDeductions findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CPerceptionsDeductions> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CPerceptionsDeductions update(CPerceptionsDeductions entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CPerceptionsDeductions entity) {
        remove(entity);
        return true;
    }
}
