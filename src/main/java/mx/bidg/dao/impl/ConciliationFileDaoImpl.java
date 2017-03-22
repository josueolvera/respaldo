package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ConciliationFileDao;
import mx.bidg.model.ConciliationFile;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Desarrollador on 27/02/2017.
 */
@Repository
public class ConciliationFileDaoImpl extends AbstractDao<Integer, ConciliationFile> implements ConciliationFileDao {

    @Override
    public ConciliationFile save(ConciliationFile entity) {
        persist(entity);
        return entity;
    }

    @Override
    public ConciliationFile findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<ConciliationFile> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public ConciliationFile update(ConciliationFile entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(ConciliationFile entity) {
        remove(entity);
        return true;
    }

    @Override
    public ConciliationFile findByFileName(String fileName) {
        return (ConciliationFile) createEntityCriteria().add(Restrictions.eq("fileName",fileName)).uniqueResult();
    }
}
