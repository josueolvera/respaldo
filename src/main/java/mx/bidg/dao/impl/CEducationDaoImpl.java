package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CEducationDao;
import mx.bidg.model.CEducation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 22/06/16.
 */
@Repository
public class CEducationDaoImpl extends AbstractDao<Integer,CEducation> implements CEducationDao {
    @Override
    public CEducation save(CEducation entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CEducation findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CEducation> findAll() {
        return (List<CEducation>) createEntityCriteria().list();
    }

    @Override
    public CEducation update(CEducation entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CEducation entity) {
        remove(entity);
        return true;
    }
}
