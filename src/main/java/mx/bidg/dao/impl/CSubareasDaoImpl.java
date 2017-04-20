package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CSubareasDao;
import mx.bidg.model.CSubareas;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josue on 19/04/2017.
 */
@Repository
public class CSubareasDaoImpl extends AbstractDao<Integer, CSubareas> implements CSubareasDao {

    @Override
    public CSubareas save(CSubareas entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CSubareas findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CSubareas> findAll() {
        return createEntityCriteria().add(Restrictions.ne("idSubarea", 0)).list();
    }

    @Override
    public CSubareas update(CSubareas entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CSubareas entity) {
        remove(entity);
        return true;
    }
}
