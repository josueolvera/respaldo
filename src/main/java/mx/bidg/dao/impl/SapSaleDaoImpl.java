package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.SapSaleDao;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.model.SapSale;
import mx.bidg.pojos.PojoSiscom;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        projList.add(Projections.groupProperty("idEmployee"));
        projList.add(Projections.groupProperty("idRole"));

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
        projList.add(Projections.groupProperty("idRegion"));
        projList.add(Projections.groupProperty("idZonas"));
        projList.add(Projections.groupProperty("idDistributor"));

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
        projList.add(Projections.groupProperty("idDistributor"));

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
        projList.add(Projections.groupProperty("idDistributor"));

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
    public List findBySupervisorRoleAndGroup(Integer idEmployee, List<GroupsAgreements> groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate) {
        Date from = Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant());

        Criteria criteria = createEntityCriteria();
        Disjunction disjuntionAgreement = Restrictions.disjunction();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        projList.add(Projections.sum("comissionableAmount"));
        projList.add(Projections.count("idSale"));
        projList.add(Projections.groupProperty("idEmployee"));
        projList.add(Projections.groupProperty("idRole"));

        for (GroupsAgreements groupsAgreements : groupsAgreementsList){
            disjuntionAgreement.add(Restrictions.eq("idAgreement", groupsAgreements.getIdAgreement()));
        }
        criteria.setProjection(projList);
        criteria.add(Restrictions.disjunction(disjuntionAgreement));
        criteria.add(Restrictions.between("purchaseDate",from,to));
        criteria.add(Restrictions.eq("idEmployee", idEmployee));
        criteria.add(Restrictions.eq("idRole", 81));

        return criteria.list();
    }

    @Override
    public Object sumTotalAmuntBeteween(Integer idDistributor, LocalDateTime fromDate, LocalDateTime toDate) {
        Date from = Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant());

        Criteria criteria = createEntityCriteria();

        return criteria.setProjection(Projections.sum("comissionableAmount"))
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.between("purchaseDate", from,to))
                .uniqueResult();
    }

    @Override
    public List<String> getAllSaleStatus() {
        Criteria criteria = createEntityCriteria();

        return criteria
                .setProjection(
                        Projections.distinct(
                                Projections.property("statusSale")))
                .list();
    }

    @Override
    public List<SapSale> findAllSalesByStatusAndDates(List<String> status, String startDate, String endDate) throws Exception{
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionStatus = Restrictions.disjunction();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate fromDate = LocalDate.parse(startDate, formatter);
        LocalDate toDate = LocalDate.parse(endDate, formatter);


        Date initialDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finalDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (!status.isEmpty()){
            for (String statusName : status){
                disjunctionStatus.add(Restrictions.like("statusSale", statusName));
            }
        }

        criteria.add(disjunctionStatus);

        return criteria
                .add(
                        Restrictions.between("purchaseDate", initialDate, finalDate))
                .list();
    }

    @Override
    public List<SapSale> findAllByIdSale(String idSale) {
        return getSession().createCriteria(SapSale.class)
                .add(Restrictions.eq("idSale", idSale)).list();
    }
}
