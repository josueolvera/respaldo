package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DistributorPerceptionDeductionDao;
import mx.bidg.model.DistributorPerceptionDeduction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Repository
public class DistributorPerceptionDeductionDaoImpl extends AbstractDao<Integer, DistributorPerceptionDeduction> implements DistributorPerceptionDeductionDao {

    @Override
    public DistributorPerceptionDeduction save(DistributorPerceptionDeduction entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DistributorPerceptionDeduction findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DistributorPerceptionDeduction> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DistributorPerceptionDeduction update(DistributorPerceptionDeduction entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DistributorPerceptionDeduction entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<DistributorPerceptionDeduction> findByDistributorAndHpd(Integer idDistributor) {
        return createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("hasPd", true))
                .list();
    }

    @Override
    public List<DistributorPerceptionDeduction> findByDistributorAll(Integer idDistributor) {
        return createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .list();
    }
}
