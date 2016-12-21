package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CalculationReportDao;
import mx.bidg.model.CalculationReport;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
@Repository
public class CalculationReportDaoImpl extends AbstractDao<Integer, CalculationReport> implements CalculationReportDao {

    @Override
    public CalculationReport save(CalculationReport entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CalculationReport findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CalculationReport> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CalculationReport update(CalculationReport entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CalculationReport entity) {
        remove(entity);
        return true;
    }

    @Override
    public CalculationReport findByName(String fileName) {
        Criteria criteria = createEntityCriteria();

        return (CalculationReport) criteria.add(Restrictions.eq("fileName", fileName)).uniqueResult();
    }

    @Override
    public List<CalculationReport> findReportsGeneratedAndNotSended() {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("status",0))
                .add(Restrictions.eq("send",0))
                .add(Restrictions.eq("showWindow",1))
                .list()
                ;
    }

    @Override
    public List<CalculationReport> findReportsGeneratedAndSendedNotAuthorized() {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("status",0))
                .add(Restrictions.eq("send",1))
                .add(Restrictions.eq("showWindow",1))
                .list()
                ;
    }

    @Override
    public List<CalculationReport> findAllReportsAuthorizedCorporate() {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("status",1))
                .add(Restrictions.eq("send",1))
                .add(Restrictions.eq("showWindow",1))
                .list()
                ;
    }

    @Override
    public List<CalculationReport> findAllReportsAuthorizedOutsourcing() {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("status",1))
                .add(Restrictions.eq("send",1))
                .add(Restrictions.eq("showWindow",2))
                .list()
                ;
    }
}
