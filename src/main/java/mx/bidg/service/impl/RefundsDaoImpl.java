package mx.bidg.service.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RefundsDao;
import mx.bidg.model.Refunds;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 25/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class RefundsDaoImpl extends AbstractDao<Integer,Refunds> implements RefundsDao {
    @Override
    public Refunds save(Refunds entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Refunds findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Refunds> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Refunds update(Refunds entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Refunds entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<Refunds> getRefunds(Integer idUser) {
        Criteria criteria = createEntityCriteria();

        if (idUser != null) {
            criteria.createCriteria("request")
                    .add(Restrictions.eq("idUserRequest", idUser));
        }

        return criteria.list();
    }
}
