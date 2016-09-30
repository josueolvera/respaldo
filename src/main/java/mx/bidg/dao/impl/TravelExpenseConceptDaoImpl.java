package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.TravelExpenseConceptDao;
import mx.bidg.model.TravelExpenseConcept;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class TravelExpenseConceptDaoImpl extends AbstractDao<Integer, TravelExpenseConcept> implements TravelExpenseConceptDao {

    @Override
    public TravelExpenseConcept save(TravelExpenseConcept entity) {
        persist(entity);
        return entity;
    }

    @Override
    public TravelExpenseConcept findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<TravelExpenseConcept> findAll() {
        return (List<TravelExpenseConcept>) createEntityCriteria().list();
    }

    @Override
    public TravelExpenseConcept update(TravelExpenseConcept entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(TravelExpenseConcept entity) {
        remove(entity);
        return true;
    }
}
