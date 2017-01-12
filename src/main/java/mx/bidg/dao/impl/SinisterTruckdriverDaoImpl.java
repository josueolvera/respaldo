package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.SinisterTruckdriverDao;
import mx.bidg.model.SinisterTruckdriver;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
@Repository
public class SinisterTruckdriverDaoImpl extends AbstractDao<Integer,SinisterTruckdriver> implements SinisterTruckdriverDao {
    @Override
    public SinisterTruckdriver save(SinisterTruckdriver entity) {
        persist(entity);
        return entity;
    }

    @Override
    public SinisterTruckdriver findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<SinisterTruckdriver> findAll() {
        return (List<SinisterTruckdriver>)createEntityCriteria().list() ;
    }

    @Override
    public SinisterTruckdriver update(SinisterTruckdriver entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(SinisterTruckdriver entity) {
        delete(entity);
        return true;
    }

    @Override
    public List<SinisterTruckdriver> findByDateStart(Integer idTipeAssistance, String startDate) {
        return createEntityCriteria().add(Restrictions.eq("idTipeAssistance",idTipeAssistance)).add(Restrictions.eq("startDate",startDate)).list();
    }
}
