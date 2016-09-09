package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CCostCenterDao;
import mx.bidg.model.CCostCenter;
import mx.bidg.model.CCostCenter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 09/09/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class CCostCenterDaoImpl extends AbstractDao<Integer, CCostCenter> implements CCostCenterDao {

    @Override
    public CCostCenter save(CCostCenter entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CCostCenter findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CCostCenter> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CCostCenter update(CCostCenter entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CCostCenter entity) {
        remove(entity);
        return true;
    }
}
