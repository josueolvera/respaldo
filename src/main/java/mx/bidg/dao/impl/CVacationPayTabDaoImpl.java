package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CVacationPayTabDao;
import mx.bidg.model.CVacationPayTab;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PC_YAIR on 14/11/2016.
 */
@Repository
public class CVacationPayTabDaoImpl extends AbstractDao<Integer, CVacationPayTab> implements CVacationPayTabDao {

    @Override
    public CVacationPayTab save(CVacationPayTab entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CVacationPayTab findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CVacationPayTab> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CVacationPayTab update(CVacationPayTab entity) {
        modify(entity);
        return entity ;
          }

    @Override
    public boolean delete(CVacationPayTab entity) {
        remove(entity);
        return true;
    }
}
