package mx.bidg.service.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RefundConceptsDao;
import mx.bidg.model.RefundConcepts;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 22/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class RefundConceptsDaoImpl extends AbstractDao<Integer,RefundConcepts> implements RefundConceptsDao {

    @Override
    public RefundConcepts save(RefundConcepts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RefundConcepts findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RefundConcepts> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RefundConcepts update(RefundConcepts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RefundConcepts entity) {
        remove(entity);
        return true;
    }
}
