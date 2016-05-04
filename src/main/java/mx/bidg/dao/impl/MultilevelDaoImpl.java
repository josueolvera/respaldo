package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.dao.MultilevelDao;
import mx.bidg.model.Multilevel;
import mx.bidg.model.Providers;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 28/04/16.
 */
@Repository
public class MultilevelDaoImpl extends AbstractDao<Integer, Multilevel> implements MultilevelDao {
    @Override
    public Multilevel save(Multilevel entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Multilevel findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Multilevel> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<Multilevel>) criteria.list();
    }

    @Override
    public Multilevel update(Multilevel entity) {
        return null;
    }

    @Override
    public boolean delete(Multilevel entity) {
        return false;
    }
}
