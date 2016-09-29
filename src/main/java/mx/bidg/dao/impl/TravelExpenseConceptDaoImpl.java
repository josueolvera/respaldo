package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestConceptDao;
import mx.bidg.model.RequestConcept;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Repository
public class RequestConceptDaoImpl extends AbstractDao<Integer, RequestConcept> implements RequestConceptDao {

    @Override
    public RequestConcept save(RequestConcept entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RequestConcept findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestConcept> findAll() {
        return (List<RequestConcept>) createEntityCriteria().list();
    }

    @Override
    public RequestConcept update(RequestConcept entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RequestConcept entity) {
        remove(entity);
        return true;
    }
}
