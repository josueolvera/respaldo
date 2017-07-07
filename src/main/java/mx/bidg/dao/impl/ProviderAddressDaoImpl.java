package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ProviderAddressDao;
import mx.bidg.model.ProviderAddress;
import mx.bidg.model.Providers;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Repository
public class ProviderAddressDaoImpl extends AbstractDao<Integer,ProviderAddress> implements ProviderAddressDao {
    @Override
    public ProviderAddress save(ProviderAddress entity) {
        persist(entity);
        return entity;
    }

    @Override
    public ProviderAddress findById(int id) {
        return (ProviderAddress) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<ProviderAddress> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<ProviderAddress>) criteria.list();
    }

    @Override
    public ProviderAddress update(ProviderAddress entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(ProviderAddress entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<ProviderAddress> findByProvider(Providers p) {
        Criteria criteria = createEntityCriteria();
        return (List<ProviderAddress>) criteria.add(Restrictions.eq("provider",p)).list();
    }

    @Override
    public Long countAddress(Providers provider) {
        return (Long) getSession().createQuery("select count(p) from ProviderAddress p where p.idProvider = :idProvider")
                .setInteger("idProvider", provider.getIdProvider())
                .uniqueResult();
    }

    @Override
    public ProviderAddress findByIdProvider(Integer idProvider){
        Criteria criteria = createEntityCriteria();
        return (ProviderAddress) criteria.add(Restrictions.eq("idProvider", idProvider)).uniqueResult();
    }
}
