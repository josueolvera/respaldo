package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CommissionAmountGroupDao;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
        return createEntityCriteria()
                .setProjection(projList)
                .add(Restrictions.isNotNull("claveSap"))
                .add(Restrictions.ne("idAg",30))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findAllByClaveSap() {
        return createEntityCriteria().add(Restrictions.isNotNull("claveSap")).add(Restrictions.ne("idAg",30)).list();
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
        return createEntityCriteria().add(Restrictions.isNotNull("idZona"))
                .add(Restrictions.isNull("idBranch"))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupRegionslAndRegion() {
        return createEntityCriteria().add(Restrictions.isNotNull("idRegion"))
                .add(Restrictions.isNull("idBranch"))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupComercialAndDistributor() {
        return createEntityCriteria().add(Restrictions.isNotNull("idDistributor"))
                .add(Restrictions.isNull("idZona"))
                .add(Restrictions.isNull("idRegion"))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> obtainBranchByZonaAndCondition(Integer idZona, AgreementsGroupCondition agreementsGroupCondition) {
        return createEntityCriteria().add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.isNotNull("idDistributor"))
                .add(Restrictions.isNotNull("idRegion"))
                .add(Restrictions.isNotNull("idBranch"))
                .add(Restrictions.between("scope",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> obtainBranchByRegionAndCondition(Integer idRegion, AgreementsGroupCondition agreementsGroupCondition) {
        return createEntityCriteria().add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.isNotNull("idDistributor"))
                .add(Restrictions.isNotNull("idZona"))
                .add(Restrictions.isNotNull("idBranch"))
                .add(Restrictions.between("scope",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> obtainBranchByDistributorAndCondition(Integer idDistributor, AgreementsGroupCondition agreementsGroupCondition) {
        return createEntityCriteria().add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.isNotNull("idRegion"))
                .add(Restrictions.isNotNull("idZona"))
                .add(Restrictions.isNotNull("idBranch"))
                .add(Restrictions.between("scope",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> getReprocessingByCondition(AgreementsGroupCondition agreementsGroupCondition) {
         return createEntityCriteria().add(Restrictions.isNotNull("idBranch"))
                .add(Restrictions.isNull("idRegion"))
                .add(Restrictions.isNull("idZona"))
                .add(Restrictions.isNull("idDistributor"))
                .add(Restrictions.eq("idAg",agreementsGroupCondition.getIdAg()))
                .add(Restrictions.between("indexReprocessing",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public CommissionAmountGroup getOnlyDataOfGroupNineTeen(Integer idEmployee) {
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idAg", 19))
                .add(Restrictions.eq("idEmployee", idEmployee))
                .uniqueResult();
    }

    @Override
    public List<CommissionAmountGroup> findAllByAdvisersAndCleaning() {
        Criteria criteria = createEntityCriteria();

        Criterion advisers = Restrictions.eq("idRole", 64);
        Criterion cleaning = Restrictions.eq("idRole", 82);
        Criterion emptys = Restrictions.isNull("idRole");

        LogicalExpression orExpression1 = Restrictions.or(advisers, cleaning);
        LogicalExpression orExpression2 = Restrictions.or(orExpression1, emptys);

        return criteria.add(Restrictions.isNotNull("claveSap"))
                .add(orExpression2)
                .add(Restrictions.ne("idRole",81))
                .addOrder(Order.desc("idRole"))
                .list();
    }

    @Override
    public List findOnlyByClaveSapAndAdvisersAndCleaning() {
        ProjectionList projList = Projections.projectionList();

        Criterion advisers = Restrictions.eq("idRole", 64);
        Criterion cleaning = Restrictions.eq("idRole", 82);
        Criterion emptys = Restrictions.isNull("idRole");

        LogicalExpression orExpression1 = Restrictions.or(advisers, cleaning);
        LogicalExpression orExpression2 = Restrictions.or(orExpression1, emptys);

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        return createEntityCriteria()
                .setProjection(projList)
                .add(orExpression2)
                .add(Restrictions.ne("idRole",81))
                .addOrder(Order.desc("idRole"))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> getBranchWithScopeGoal() {
        return createEntityCriteria().add(Restrictions.ne("idAg",18)).add(Restrictions.ne("idAg",21))
                .add(Restrictions.isNotNull("idBranch")).add(Restrictions.ge("scope", new BigDecimal(80)))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupSupervisorAndSupervisors() {
        return createEntityCriteria()
                .add(Restrictions.eq("idAg", 30))
                .add(Restrictions.eq("idRole", 81))
                .list();
    }

    @Override
    public List findOnlyClaveSapAndSupervisor() {
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        return createEntityCriteria()
                .setProjection(projList)
                .add(Restrictions.isNotNull("claveSap"))
                .add(Restrictions.eq("idRole",81))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findAllBySupervisor() {
        Criteria criteria = createEntityCriteria();

        return criteria.add(Restrictions.isNotNull("claveSap"))
                .add(Restrictions.eq("idRole",81))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findAllRegisterBySupervisorExceptGroupSupervisor(String claveSap) {
        return createEntityCriteria()
                .add(Restrictions.eq("claveSap", claveSap))
                .add(Restrictions.ne("idAg",30))
                .list();
    }

    @Override
    public CommissionAmountGroup getGroupNineTeenAndConditons(Integer idEmployee, AgreementsGroupCondition agreementsGroupCondition) {
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idAg", 19))
                .add(Restrictions.eq("idEmployee", idEmployee))
                .add(Restrictions.between("applicationsNumber",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .uniqueResult();
    }

    @Override
    public List findSupervisorOnlyClaveSapeByBuildingReport() {
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        return createEntityCriteria()
                .setProjection(projList)
                .add(Restrictions.isNotNull("claveSap"))
                .add(Restrictions.eq("idRole",81))
                .add(Restrictions.eq("idAg",30))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findSupervisorByBuildingReport() {
        Criteria criteria = createEntityCriteria();

        return criteria.add(Restrictions.isNotNull("claveSap"))
                .add(Restrictions.eq("idRole",81))
                .add(Restrictions.eq("idAg",30))
                .list();
    }
}
