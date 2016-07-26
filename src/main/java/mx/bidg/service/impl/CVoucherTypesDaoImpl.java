package mx.bidg.service.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CVoucherTypesDao;
import mx.bidg.model.CVoucherTypes;
import org.hibernate.FetchMode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 21/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CVoucherTypesDaoImpl extends AbstractDao<Integer, CVoucherTypes> implements CVoucherTypesDao {

    @Override
    public CVoucherTypes save(CVoucherTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CVoucherTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CVoucherTypes> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CVoucherTypes update(CVoucherTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CVoucherTypes entity) {
        remove(entity);
        return true;
    }
}
