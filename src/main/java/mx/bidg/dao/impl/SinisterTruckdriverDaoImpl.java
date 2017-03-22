package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.SinisterTruckdriverDao;
import mx.bidg.model.SinisterTruckdriver;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<SinisterTruckdriver> findByCreationDate(LocalDateTime creationDate) {
        return createEntityCriteria()
                .add(Restrictions.eq("creationDate", creationDate))
                .list();
    }

    @Override
    public List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .setProjection(Projections.property("numFolio"))
                .add(Restrictions.between("dStartValidity",startDate, endDate))
                .list();
    }

    @Override
    public List<SinisterTruckdriver> findByDStartValidity(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.between("dStartValidity",startDate, endDate))
                .list();
    }

    @Override
    public List getFoliosComisionIvaByDStartValidity(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = createEntityCriteria();
        ProjectionList properties = Projections.projectionList();

        properties.add(Projections.property("numFolio"));
        properties.add(Projections.property("ip.commissionInterpro"));
        properties.add(Projections.property("ip.ivaInterpro"));

        criteria.createAlias("insurancePremium", "ip");
        criteria.setProjection(properties);
        criteria.add(Restrictions.between("dStartValidity",startDate, endDate));

        return criteria.list();
    }
}
