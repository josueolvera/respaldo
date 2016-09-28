package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ChecksDao;
import mx.bidg.model.CGroups;
import mx.bidg.model.Checks;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 27/09/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class ChecksDaoImpl extends AbstractDao<Integer, Checks> implements ChecksDao {
    @Override
    public Checks save(Checks entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Checks findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Checks> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Checks update(Checks entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Checks entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<Checks> getChecks(Integer idUser) {
        Criteria criteria = createEntityCriteria();

        if (idUser != null) {
            criteria.createCriteria("request")
                    .add(Restrictions.eq("idUserRequest", idUser));
        }

        return criteria.list();
    }
}
