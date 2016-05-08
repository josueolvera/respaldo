package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CSettlementDao;
import mx.bidg.model.CSettlement;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Repository
public class CSettlementDaoImpl extends AbstractDao<Integer,CSettlement> implements CSettlementDao {
    @Override
    public CSettlement save(CSettlement entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CSettlement findById(int id) {
        return (CSettlement) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CSettlement> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CSettlement>) criteria.list();
    }

    @Override
    public CSettlement update(CSettlement entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CSettlement entity) {
        remove(entity);
        return true;
    }
}
