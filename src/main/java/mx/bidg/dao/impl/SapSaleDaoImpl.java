package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.SapSaleDao;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.model.SapSale;
import mx.bidg.pojos.PojoSiscom;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by gerardo8 on 29/04/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class SapSaleDaoImpl extends AbstractDao<Integer, SapSale> implements SapSaleDao {
    @Override
    public SapSale save(SapSale entity) {
        persist(entity);
        return entity;
    }

    @Override
    public SapSale findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<SapSale> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<SapSale>) criteria.list();
    }

    @Override
    public SapSale update(SapSale entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(SapSale entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public SapSale findByIdSale(String idSale) {
        return (SapSale) getSession().createCriteria(SapSale.class)
                .add(Restrictions.eq("idSale", idSale))
                .add(Restrictions.eq("status", 0))
                .uniqueResult();
    }

    @Override
    public List findByAgreementGroup(List<GroupsAgreements> groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate) {
        Date from = Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant());

        Criteria criteria = createEntityCriteria();
        Disjunction disjuntionAgreement = Restrictions.disjunction();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        projList.add(Projections.count("idSale"));
        projList.add(Projections.sum("comissionableAmount"));
        
        for (GroupsAgreements groupsAgreements : groupsAgreementsList){
            disjuntionAgreement.add(Restrictions.eq("idAgreement", groupsAgreements.getIdAgreement()));
        }
        criteria.setProjection(projList);
        criteria.add(Restrictions.disjunction(disjuntionAgreement));
        criteria.add(Restrictions.between("purchaseDate",from,to));

        return criteria.list();
    }

    @Override
    public List findByBranchGroup(List<GroupsAgreements> groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate) {
        Date from = Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant());

        Criteria criteria = createEntityCriteria();
        Disjunction disjuntionAgreement = Restrictions.disjunction();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("idBranch")));
        projList.add(Projections.sum("comissionableAmount"));
        projList.add(Projections.count("idSale"));

        for (GroupsAgreements groupsAgreements : groupsAgreementsList){
            disjuntionAgreement.add(Restrictions.eq("idAgreement", groupsAgreements.getIdAgreement()));
        }
        criteria.setProjection(projList);
        criteria.add(Restrictions.disjunction(disjuntionAgreement));
        criteria.add(Restrictions.between("purchaseDate",from,to));

        return criteria.list();
    }

    @Override
    public List findByZonaGroup(List<GroupsAgreements> groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate) {
        Date from = Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant());

        Criteria criteria = createEntityCriteria();
        Disjunction disjuntionAgreement = Restrictions.disjunction();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("idZonas")));
        projList.add(Projections.sum("comissionableAmount"));
        projList.add(Projections.count("idSale"));

        for (GroupsAgreements groupsAgreements : groupsAgreementsList){
            disjuntionAgreement.add(Restrictions.eq("idAgreement", groupsAgreements.getIdAgreement()));
        }
        criteria.setProjection(projList);
        criteria.add(Restrictions.disjunction(disjuntionAgreement));
        criteria.add(Restrictions.between("purchaseDate",from,to));

        return criteria.list();
    }

    @Override
    public List findByRegionGroup(List<GroupsAgreements> groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate) {
        Date from = Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant());

        Criteria criteria = createEntityCriteria();
        Disjunction disjuntionAgreement = Restrictions.disjunction();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("idRegion")));
        projList.add(Projections.sum("comissionableAmount"));
        projList.add(Projections.count("idSale"));

        for (GroupsAgreements groupsAgreements : groupsAgreementsList){
            disjuntionAgreement.add(Restrictions.eq("idAgreement", groupsAgreements.getIdAgreement()));
        }
        criteria.setProjection(projList);
        criteria.add(Restrictions.disjunction(disjuntionAgreement));
        criteria.add(Restrictions.between("purchaseDate",from,to));

        return criteria.list();
    }

    @Override
    public List findByDistributorGroup(List<GroupsAgreements> groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate) {
        Date from = Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant());

        Criteria criteria = createEntityCriteria();
        Disjunction disjuntionAgreement = Restrictions.disjunction();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("idDistributor")));
        projList.add(Projections.sum("comissionableAmount"));
        projList.add(Projections.count("idSale"));

        for (GroupsAgreements groupsAgreements : groupsAgreementsList){
            disjuntionAgreement.add(Restrictions.eq("idAgreement", groupsAgreements.getIdAgreement()));
        }
        criteria.setProjection(projList);
        criteria.add(Restrictions.disjunction(disjuntionAgreement));
        criteria.add(Restrictions.between("purchaseDate",from,to));

        return criteria.list();
    }

    @Override
    public List<SapSale> findAllByIdSale(String idSale) {
        return getSession().createCriteria(SapSale.class)
                .add(Restrictions.eq("idSale", idSale)).list();
    }
}
