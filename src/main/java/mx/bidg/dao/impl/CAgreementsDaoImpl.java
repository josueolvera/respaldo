package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAgreementsDao;
import mx.bidg.model.CAgreements;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 16/06/16.
 */
@Repository
public class CAgreementsDaoImpl extends AbstractDao <Integer,CAgreements> implements CAgreementsDao {

    @Override
    public CAgreements save(CAgreements entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAgreements findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAgreements> findAll() {
        return (List<CAgreements>) createEntityCriteria().list();
    }

    @Override
    public CAgreements update(CAgreements entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAgreements entity) {
        remove(entity);
        return true;
    }

    @Override
    public CAgreements findByName(String name) {
        Criteria criteria = createEntityCriteria();
        return (CAgreements) criteria.add(Restrictions.eq("agreementNameClean", name)).uniqueResult();
    }
}
