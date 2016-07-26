package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTravelExpensesConceptsDao;
import mx.bidg.model.CTravelExpensesConcepts;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CTravelExpensesConceptsDaoImpl extends AbstractDao<Integer, CTravelExpensesConcepts> implements CTravelExpensesConceptsDao {

    @Override
    public CTravelExpensesConcepts save(CTravelExpensesConcepts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CTravelExpensesConcepts findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTravelExpensesConcepts> findAll() {
        return (List<CTravelExpensesConcepts>) createEntityCriteria().list();
    }

    @Override
    public CTravelExpensesConcepts update(CTravelExpensesConcepts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CTravelExpensesConcepts entity) {
        remove(entity);
        return true;
    }
}
