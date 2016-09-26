package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CommissionAmountGroupDao;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
@Repository
public class CommissionAmountGroupDaoImpl extends AbstractDao<Integer, CommissionAmountGroup> implements CommissionAmountGroupDao {

    @Override
    public CommissionAmountGroup save(CommissionAmountGroup entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CommissionAmountGroup findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CommissionAmountGroup> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CommissionAmountGroup update(CommissionAmountGroup entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CommissionAmountGroup entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CommissionAmountGroup> getComissionsByConditon(AgreementsGroupCondition agreementsGroupCondition) {
        Criteria criteria = createEntityCriteria();

        return criteria.add(Restrictions.eq("idAg",agreementsGroupCondition.getIdAg()))
                .add(Restrictions.between("amount",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> getBonusByConditon(AgreementsGroupCondition agreementsGroupCondition) {
        Criteria criteria = createEntityCriteria();

        return criteria.add(Restrictions.eq("idAg",agreementsGroupCondition.getIdAg()))
                .add(Restrictions.between("applicationsNumber",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> getScopeByConditon(AgreementsGroupCondition agreementsGroupCondition) {
        Criteria criteria = createEntityCriteria();

        return criteria.add(Restrictions.eq("idAg",agreementsGroupCondition.getIdAg()))
                .add(Restrictions.between("scope",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List findOnlyByClaveSap() {
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        return createEntityCriteria().setProjection(projList).add(Restrictions.isNotNull("claveSap")).list();
    }

    @Override
    public List<CommissionAmountGroup> findAllByClaveSap() {
        return createEntityCriteria().add(Restrictions.isNotNull("claveSap")).list();
    }

    @Override
    public List findOnlyByIdBranch() {
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("idBranch")));
        return createEntityCriteria().setProjection(projList)
                .add(Restrictions.isNotNull("idBranch"))
                .add(Restrictions.ne("idAg",18))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findAllByBranchs() {
        return createEntityCriteria().add(Restrictions.ne("idAg",18)).add(Restrictions.isNotNull("idBranch")).list();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupAuxiliarAndBranch() {
        return createEntityCriteria().add(Restrictions.eq("idAg",18)).add(Restrictions.isNotNull("idBranch")).list();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupBranchAndBranch() {
        return createEntityCriteria().add(Restrictions.ne("idAg",18)).add(Restrictions.ne("idAg",21))
                .add(Restrictions.isNotNull("idBranch"))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupZonalAndZona() {
        return createEntityCriteria().add(Restrictions.isNotNull("idZona")).list();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupRegionslAndRegion() {
        return createEntityCriteria().add(Restrictions.isNotNull("idRegion")).list();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupComercialAndDistributor() {
        return createEntityCriteria().add(Restrictions.isNotNull("idDistributor"))
                .add(Restrictions.isNull("idZona"))
                .add(Restrictions.isNull("idRegion"))
                .list();
    }
}
