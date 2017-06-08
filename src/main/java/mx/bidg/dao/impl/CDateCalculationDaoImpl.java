package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CDateCalculationDao;
import mx.bidg.model.CDateCalculation;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 6/10/16.
 */
@Repository
public class CDateCalculationDaoImpl extends AbstractDao<Integer, CDateCalculation> implements CDateCalculationDao {

    @Override
    public CDateCalculation save(CDateCalculation entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CDateCalculation findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CDateCalculation> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CDateCalculation update(CDateCalculation entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CDateCalculation entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CDateCalculation> findByStatus(Integer status) {
        return createEntityCriteria()
                .add(Restrictions.eq("status", status))
                .list();
    }
}
