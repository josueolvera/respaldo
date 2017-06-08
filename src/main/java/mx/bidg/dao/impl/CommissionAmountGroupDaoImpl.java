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
        Criterion auxiliar = Restrictions.eq("idRole", 80);
        Criterion emptys = Restrictions.isNull("idRole");

        LogicalExpression orExpression1 = Restrictions.or(advisers, cleaning);
        LogicalExpression orExpression2 = Restrictions.or(orExpression1, auxiliar);
        LogicalExpression orExpression3 = Restrictions.or(orExpression2, emptys);

        return criteria.add(Restrictions.isNotNull("claveSap"))
                .add(orExpression3)
                .add(Restrictions.ne("idRole",81))
                .addOrder(Order.desc("idRole"))
                .list();
    }

    @Override
    public List findOnlyByClaveSapAndAdvisersAndCleaning() {
        ProjectionList projList = Projections.projectionList();

        Criterion advisers = Restrictions.eq("idRole", 64);
        Criterion cleaning = Restrictions.eq("idRole", 82);
        Criterion auxiliar = Restrictions.eq("idRole", 80);
        Criterion emptys = Restrictions.isNull("idRole");

        LogicalExpression orExpression1 = Restrictions.or(advisers, cleaning);
        LogicalExpression orExpression2 = Restrictions.or(orExpression1, auxiliar);
        LogicalExpression orExpression3 = Restrictions.or(orExpression2, emptys);

        projList.add(Projections.distinct(Projections.groupProperty("claveSap")));
        return createEntityCriteria()
                .setProjection(projList)
                .add(orExpression3)
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

    @Override
    public List<CommissionAmountGroup> getBranchWithScopeGoalBetween(AgreementsGroupCondition agreementsGroupCondition) {
        return createEntityCriteria().add(Restrictions.ne("idAg",18)).add(Restrictions.ne("idAg",21))
                .add(Restrictions.isNotNull("idBranch"))
                .add(Restrictions.between("scope",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public CommissionAmountGroup findSaleByBranchWithAmountPemex(Integer idBranch) {
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idAg", 21))
                .add(Restrictions.eq("idBranch", idBranch))
                .uniqueResult();
    }

    @Override
    public List<CommissionAmountGroup> findBranchsWithTabulatorByZona(Integer idZona) {
        return createEntityCriteria().add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .add(Restrictions.eq("idZona", idZona))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findBranchsWithTabulatorByRegion(Integer idRegion) {
        return createEntityCriteria().add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .add(Restrictions.eq("idRegion", idRegion))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> getBranchWithTabulatorByGBranch() {
        return createEntityCriteria().add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findBranchsWithTabulatorByDistributor(Integer idDistributor) {
        return createEntityCriteria().add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .add(Restrictions.eq("idDistributor", idDistributor))
                .list();
    }

    @Override
    public List<Integer> getOnlyIdsZonasFromGroupZona() {
        Criteria criteria = createEntityCriteria();

        return criteria.setProjection(
                Projections.distinct(Projections.property("idZona")))
                .add(Restrictions.eq("idAg", 23))
                .list();
    }

    @Override
    public List getSumAmountsAndNumOfBranchsByZona(Integer idZona) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idZona")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public List getSumAmountsAndNumOfBranchsByDistributorAndZona(Integer idDistributor, Integer idZona) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idDistributor")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));
        projectionList.add(Projections.groupProperty("idZona"));

        return criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .setProjection(projectionList)
                .list();
    }

    @Override
    public List getSumAmountsAndNumOfBranchsByZonaPemex(Integer idZona) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idZona")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 21))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public List getSumAmountsAndNumOfBranchsByDistributorAndZonaPemex(Integer idDistributor, Integer idZona) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idDistributor")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));
        projectionList.add(Projections.groupProperty("idZona"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 21))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public CommissionAmountGroup findZonaByBranchAndDistributorInGroupZona(Integer idDistributor, Integer idZona) {
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 23))
                .uniqueResult();
    }

    @Override
    public CommissionAmountGroup findZonaByBranchAndDistributorInGroupZonaP(Integer idDistributor, Integer idZona) {
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 31))
                .uniqueResult();
    }

    @Override
    public List<CommissionAmountGroup> findByGroupBranchAndTabulatorBranch() {
        return createEntityCriteria().add(Restrictions.ne("idAg",18)).add(Restrictions.ne("idAg",21))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .add(Restrictions.isNotNull("idBranch"))
                .list();
    }

    @Override
    public BigDecimal getSumAmountsByDistributorAndZona(Integer idDistributor, Integer idZona) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idDistributor")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));
        projectionList.add(Projections.groupProperty("idZona"));

        return (BigDecimal) criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .setProjection(Projections.sum("amount"))
                .uniqueResult();
    }

    @Override
    public Long getNumOfBranchsByDistributorAndZona(Integer idDistributor, Integer idZona) {
        Criteria criteria = createEntityCriteria();

        return (Long) criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .setProjection(Projections.count("idBranch"))
                .uniqueResult();
    }

    @Override
    public List<CommissionAmountGroup> findAllBranchsByZonaAndDistributor(Integer idDistributor, Integer idZona) {
        Criteria criteria = createEntityCriteria();

        return  criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public BigDecimal getSumAmountsByDistributorAndZonaP(Integer idDistributor, Integer idZona) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idDistributor")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));
        projectionList.add(Projections.groupProperty("idZona"));

        return (BigDecimal) criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 21))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .setProjection(Projections.sum("amount"))
                .uniqueResult();
    }

    @Override
    public List<CommissionAmountGroup> findAllBranchsPByZonaAndDistributor(Integer idDistributor, Integer idZona) {
        return  createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 21))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public List<Integer> getOnlyIdsRegionsFromGroupRegion() {
        Criteria criteria = createEntityCriteria();

        return criteria.setProjection(
                Projections.distinct(Projections.property("idRegion")))
                .add(Restrictions.eq("idAg", 24))
                .list();
    }

    @Override
    public List getSumAmountsAndNumOfBranchsByRegion(Integer idRegion) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idRegion")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public List getSumAmountsAndNumOfBranchsByRegionPemex(Integer idRegion) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idRegion")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 21))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public BigDecimal getSumAmountsByDistributorAndRegion(Integer idDistributor, Integer idRegion) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idDistributor")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));
        projectionList.add(Projections.groupProperty("idRegion"));

        return (BigDecimal) criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .setProjection(Projections.sum("amount"))
                .uniqueResult();
    }

    @Override
    public BigDecimal getSumAmountsByDistributorAndRegionP(Integer idDistributor, Integer idRegion) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idDistributor")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));
        projectionList.add(Projections.groupProperty("idRegion"));

        return (BigDecimal) criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 21))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .setProjection(Projections.sum("amount"))
                .uniqueResult();
    }

    @Override
    public List<CommissionAmountGroup> findAllBranchsByRegionAndDistributor(Integer idDistributor, Integer idRegion) {
        Criteria criteria = createEntityCriteria();

        return  criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findAllBranchsPByRegionAndDistributor(Integer idDistributor, Integer idRegion) {
        Criteria criteria = createEntityCriteria();

        return  criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 21))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public CommissionAmountGroup findRegionByRegionAndDistributorInGroupRegion(Integer idDistributor, Integer idRegion) {
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 24))
                .uniqueResult();
    }

    @Override
    public CommissionAmountGroup findRegionByRegionAndDistributorInGroupRegionP(Integer idDistributor, Integer idRegion) {
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 32))
                .uniqueResult();
    }

    @Override
    public List getSumAmountsAndNumOfBranchsByRegionAndCondition(Integer idRegion, AgreementsGroupCondition groupCondition) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idRegion")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.between("scope",groupCondition.getAmountMin(),
                        groupCondition.getAmountMax()))
                .list();
    }

    @Override
    public BigDecimal getSumAmountsByDistributorAndRegionAndCondition(Integer idDistributor, Integer idRegion, AgreementsGroupCondition groupCondition) {
        Criteria criteria = createEntityCriteria();

        return (BigDecimal) criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.between("scope",groupCondition.getAmountMin(),
                        groupCondition.getAmountMax()))
                .setProjection(Projections.sum("amount"))
                .uniqueResult();
    }

    @Override
    public List<CommissionAmountGroup> findAllBranchsByRegionAndDistributorAndCondition(Integer idDistributor, Integer idRegion, AgreementsGroupCondition groupCondition) {
        Criteria criteria = createEntityCriteria();

        return  criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.between("scope",groupCondition.getAmountMin(),
                        groupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List getSumAmountsAndNumOfBranchsByZonaAndCondition(Integer idZona, AgreementsGroupCondition groupCondition) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.distinct(Projections.groupProperty("idZona")));
        projectionList.add(Projections.sum("amount"));
        projectionList.add(Projections.count("idBranch"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.between("scope",groupCondition.getAmountMin(),
                        groupCondition.getAmountMax()))
                .list();
    }

    @Override
    public BigDecimal getSumAmountsByDistributorAndZonaAndCondition(Integer idDistributor, Integer idZona, AgreementsGroupCondition groupCondition) {
        Criteria criteria = createEntityCriteria();

        return (BigDecimal) criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.between("scope",groupCondition.getAmountMin(),
                        groupCondition.getAmountMax()))
                .setProjection(Projections.sum("amount"))
                .uniqueResult();
    }

    @Override
    public List<CommissionAmountGroup> findAllBranchsByZonaAndDistributorAndCondition(Integer idDistributor, Integer idZona, AgreementsGroupCondition groupCondition) {
        Criteria criteria = createEntityCriteria();

        return  criteria
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 20))
                .add(Restrictions.between("scope",groupCondition.getAmountMin(),
                        groupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> getBranchsWithScopeGoalAndTabulator() {
        return createEntityCriteria().add(Restrictions.ne("idAg",18)).add(Restrictions.ne("idAg",21))
                .add(Restrictions.isNotNull("idBranch")).add(Restrictions.ge("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> getBranchWithTabulatorByGBranchAndGBP() {
        LogicalExpression expression = Restrictions.or(Restrictions.eq("idAg", 20), Restrictions.eq("idAg", 21));
        return createEntityCriteria().add(expression)
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> findBranchsAndBPWithTabulatorByDistributor(Integer idDistributor) {
        LogicalExpression expression = Restrictions.or(Restrictions.eq("idAg", 20), Restrictions.eq("idAg", 21));
        return createEntityCriteria()
                .add(expression)
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .add(Restrictions.eq("idDistributor", idDistributor))
                .list();
    }

    @Override
    public CommissionAmountGroup findZonaByBranchAndDistributorInGroupZonaGT(Integer idDistributor, Integer idZona) {
        LogicalExpression expression = Restrictions.and(Restrictions.gt("tabulator", new BigDecimal(0.00)), Restrictions.isNotNull("tabulator"));
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idZona", idZona))
                .add(Restrictions.eq("idAg", 23))
                .add(expression)
                .uniqueResult();
    }

    @Override
    public CommissionAmountGroup findRegionByRegionAndDistributorInGroupRegionGT(Integer idDistributor, Integer idRegion) {
        LogicalExpression expression = Restrictions.and(Restrictions.gt("tabulator", new BigDecimal(0.00)), Restrictions.isNotNull("tabulator"));
        return (CommissionAmountGroup) createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idAg", 24))
                .add(Restrictions.gt("tabulator", new BigDecimal(0.00)))
                .add(expression)
                .uniqueResult();
    }
}
