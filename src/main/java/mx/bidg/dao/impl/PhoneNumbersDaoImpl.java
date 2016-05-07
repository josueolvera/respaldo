package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PhoneNumbersDao;
import mx.bidg.model.PhoneNumbers;
import mx.bidg.model.Providers;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 6/05/16.
 */

@Repository
public class PhoneNumbersDaoImpl extends AbstractDao<Integer,PhoneNumbers> implements PhoneNumbersDao {
    @Override
    public PhoneNumbers save(PhoneNumbers entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PhoneNumbers findById(int id) {
        return (PhoneNumbers) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<PhoneNumbers> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<PhoneNumbers>) criteria.list();
    }

    @Override
    public PhoneNumbers update(PhoneNumbers entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PhoneNumbers entity) {
        remove(entity);
        return true;
    }
}
