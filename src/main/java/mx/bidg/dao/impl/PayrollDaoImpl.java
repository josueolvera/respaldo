package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PayrollDao;
import mx.bidg.model.Payroll;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Desarrollador on 19/11/2016.
 */
@Repository
public class PayrollDaoImpl extends AbstractDao <Integer,Payroll> implements PayrollDao {

    @Override
    public Payroll save(Payroll entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Payroll findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Payroll> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Payroll update(Payroll entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Payroll entity) {
        remove(entity);
        return true;
    }

    @Override
    public Object sumEfectivoEdmon() {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("efectivoEdmon"));
        return criteria.setProjection(projectionList).uniqueResult();
    }

    @Override
    public Object sumGmtNec() {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("totalFacturar"));
        return criteria.setProjection(projectionList).uniqueResult();
    }

    @Override
    public List<Payroll> findByDistributor(Integer idDistributor) {
        return createEntityCriteria().add(Restrictions.eq("idDistribuidor", idDistributor)).list();
    }

    @Override
    public Object sumDistributorNec(Integer idDistributor) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("totalFacturar"));
        return criteria.setProjection(projectionList).add(Restrictions.eq("idDistribuidor", idDistributor)).uniqueResult();
    }
}
