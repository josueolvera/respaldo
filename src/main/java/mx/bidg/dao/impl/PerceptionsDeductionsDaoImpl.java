package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PerceptionsDeductionsDao;
import mx.bidg.model.PerceptionsDeductions;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Repository
public class PerceptionsDeductionsDaoImpl extends AbstractDao<Integer, PerceptionsDeductions> implements PerceptionsDeductionsDao {

    @Override
    public PerceptionsDeductions save(PerceptionsDeductions entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PerceptionsDeductions findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PerceptionsDeductions> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public PerceptionsDeductions update(PerceptionsDeductions entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PerceptionsDeductions entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<PerceptionsDeductions> findAllWithStatus() {
        return createEntityCriteria().add(Restrictions.eq("status", true)).list();
    }
}
