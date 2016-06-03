package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CSapFileDao;
import mx.bidg.model.CSapFile;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 12/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CSapFileDaoImpl extends AbstractDao<Integer,CSapFile> implements CSapFileDao {
    @Override
    public CSapFile save(CSapFile entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CSapFile findById(int id) {
        return (CSapFile) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CSapFile> findAll() {
        return (List<CSapFile>) createEntityCriteria().list();
    }

    @Override
    public CSapFile update(CSapFile entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CSapFile entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
