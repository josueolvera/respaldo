package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CommissionAmountGroupBackupDao;
import mx.bidg.model.CommissionAmountGroupBackup;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by josueolvera on 22/09/16.
 */
@Repository
public class CommissionAmountGroupBackupDaoImpl extends AbstractDao<Integer, CommissionAmountGroupBackup> implements CommissionAmountGroupBackupDao {

    @Override
    public CommissionAmountGroupBackup save(CommissionAmountGroupBackup entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CommissionAmountGroupBackup findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CommissionAmountGroupBackup> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CommissionAmountGroupBackup update(CommissionAmountGroupBackup entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CommissionAmountGroupBackup entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CommissionAmountGroupBackup> findTotalAmountGroupGobierno(LocalDateTime fromDate, LocalDateTime toDate) {

        Criteria criteria = createEntityCriteria();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        projList.add(Projections.sum("amount"));
        projList.add(Projections.sum("applicationsNumber"));
        projList.add(Projections.sum("commission"));
        projList.add(Projections.groupProperty("idAg"));

        criteria.setProjection(projList);
        criteria.add(Restrictions.between("toDate",fromDate,toDate));

        return criteria.add(Restrictions.eq("idAg", 13)).list();
    }

    @Override
    public List<CommissionAmountGroupBackup> findTotalAmountGroupSalud(LocalDateTime fromDate, LocalDateTime toDate) {

        Criteria criteria = createEntityCriteria();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        projList.add(Projections.sum("amount"));
        projList.add(Projections.sum("applicationsNumber"));
        projList.add(Projections.sum("commission"));
        projList.add(Projections.groupProperty("idAg"));

        criteria.setProjection(projList);
        criteria.add(Restrictions.between("toDate",fromDate,toDate));

        return criteria.add(Restrictions.eq("idAg", 16)).list();
    }

    @Override
    public List<CommissionAmountGroupBackup> findTotalAmountGroupSaludCI(LocalDateTime fromDate, LocalDateTime toDate) {

        Criteria criteria = createEntityCriteria();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        projList.add(Projections.sum("amount"));
        projList.add(Projections.sum("applicationsNumber"));
        projList.add(Projections.sum("commission"));
        projList.add(Projections.groupProperty("idAg"));

        criteria.setProjection(projList);
        criteria.add(Restrictions.between("toDate",fromDate,toDate));

        return criteria.add(Restrictions.eq("idAg", 17)).list();
    }
}
