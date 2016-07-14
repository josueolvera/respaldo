package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RoleConceptDao;
import mx.bidg.model.RequestConcept;
import mx.bidg.model.RoleConcept;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Repository
public class RoleConceptDaoImpl extends AbstractDao<Integer, RoleConcept> implements RoleConceptDao {
    @Override
    public RoleConcept save(RoleConcept entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RoleConcept findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RoleConcept> findAll() {
        return (List<RoleConcept>) createEntityCriteria().list();
    }

    @Override
    public RoleConcept update(RoleConcept entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RoleConcept entity) {
        remove(entity);
        return true;
    }
}
