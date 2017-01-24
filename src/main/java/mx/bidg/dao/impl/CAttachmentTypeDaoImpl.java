package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAttachmentTypeDao;
import mx.bidg.model.CAttachmentType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 17/01/2017.
 */
@Repository
public class CAttachmentTypeDaoImpl extends AbstractDao<Integer, CAttachmentType> implements CAttachmentTypeDao {
    @Override
    public CAttachmentType save(CAttachmentType entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CAttachmentType findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAttachmentType> findAll() {
        return (List<CAttachmentType>) createEntityCriteria().list();
    }

    @Override
    public CAttachmentType update(CAttachmentType entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CAttachmentType entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
