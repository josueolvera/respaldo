package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ProvidersContactDao;
import mx.bidg.model.ProvidersContact;
import mx.bidg.model.Providers;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 6/05/16.
 */
@Repository
public class ProvidersContactDaoImpl extends AbstractDao<Integer,ProvidersContact> implements ProvidersContactDao {
    @Override
    public ProvidersContact save(ProvidersContact entity) {
        persist(entity);
        return entity;
    }

    @Override
    public ProvidersContact findById(int id) {
        return (ProvidersContact) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<ProvidersContact> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<ProvidersContact>) criteria.list();
    }

    @Override
    public ProvidersContact update(ProvidersContact entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(ProvidersContact entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<ProvidersContact> findByProvider(Providers p) {
        Criteria criteria = createEntityCriteria();
        return (List<ProvidersContact>) criteria.add(Restrictions.eq("provider",p)).list();
    }
}
