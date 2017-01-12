package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.model.PolicyTruckdriver;
import mx.bidg.dao.PolicyTruckdriverDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResizableByteArrayOutputStream;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
@Repository
public class PolicyTruckdriverDaoImpl extends AbstractDao<Integer, PolicyTruckdriver> implements PolicyTruckdriverDao {
    @Override
    public PolicyTruckdriver save(PolicyTruckdriver entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PolicyTruckdriver findById(int id) {return getByKey(id);}

    @Override
    public List<PolicyTruckdriver> findAll() {
        return (List<PolicyTruckdriver>)createEntityCriteria().list();
    }

    @Override
    public PolicyTruckdriver update(PolicyTruckdriver entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PolicyTruckdriver entity) {
        delete(entity);
        return true;
    }

    @Override
    public List<PolicyTruckdriver> findDStartValidity(LocalDate starDate) {
        return  createEntityCriteria().add(Restrictions.eq("dStartValidity", starDate)).list();
    }
}
