package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTravelTypesDao;
import mx.bidg.model.CTravelTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Repository
public class CTravelTypesDaoImpl extends AbstractDao<Integer, CTravelTypes> implements CTravelTypesDao {

    @Override
    public CTravelTypes save(CTravelTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CTravelTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTravelTypes> findAll() {
        return (List<CTravelTypes>) createEntityCriteria().list();
    }

    @Override
    public CTravelTypes update(CTravelTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CTravelTypes entity) {
        remove(entity);
        return true;
    }
}
