package mx.bidg.dao;

import mx.bidg.dao.impl.CommissionAmountGroupDaoImpl;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
public interface CommissionAmountGroupDao extends InterfaceDao<CommissionAmountGroup> {
    List<CommissionAmountGroup> getComissionsByConditon (AgreementsGroupCondition agreementsGroupCondition);
    List<CommissionAmountGroup> getBonusByConditon (AgreementsGroupCondition agreementsGroupCondition);
    List<CommissionAmountGroup> getScopeByConditon (AgreementsGroupCondition agreementsGroupCondition);
    List findOnlyByClaveSap ();
    List<CommissionAmountGroup> findAllByClaveSap();
    List findOnlyByIdBranch ();
    List<CommissionAmountGroup> findAllByBranchs();
    List<CommissionAmountGroup> findByGroupAuxiliarAndBranch();
    List<CommissionAmountGroup> findByGroupBranchAndBranch();
    List<CommissionAmountGroup> findByGroupZonalAndZona();
    List<CommissionAmountGroup> findByGroupRegionslAndRegion();
    List<CommissionAmountGroup> findByGroupComercialAndDistributor();
    List<CommissionAmountGroup> obtainBranchByZonaAndCondition(Integer idZona, AgreementsGroupCondition agreementsGroupCondition);
    List<CommissionAmountGroup> obtainBranchByRegionAndCondition(Integer idRegion, AgreementsGroupCondition agreementsGroupCondition);
    List<CommissionAmountGroup> obtainBranchByDistributorAndCondition(Integer idDistributor, AgreementsGroupCondition agreementsGroupCondition);
    List<CommissionAmountGroup> getReprocessingByCondition(AgreementsGroupCondition agreementsGroupCondition);
    CommissionAmountGroup getOnlyDataOfGroupNineTeen(Integer idEmployee);
    List<CommissionAmountGroup> findAllByAdvisersAndCleaning();
    List findOnlyByClaveSapAndAdvisersAndCleaning ();
    List <CommissionAmountGroup> getBranchWithScopeGoal();
    List<CommissionAmountGroup> findByGroupSupervisorAndSupervisors();
    List findOnlyClaveSapAndSupervisor ();
    List<CommissionAmountGroup> findAllBySupervisor();
    List<CommissionAmountGroup> findAllRegisterBySupervisorExceptGroupSupervisor(String claveSap);
    CommissionAmountGroup getGroupNineTeenAndConditons(Integer idEmployee, AgreementsGroupCondition agreementsGroupCondition);
    List findSupervisorOnlyClaveSapeByBuildingReport();
    List<CommissionAmountGroup> findSupervisorByBuildingReport();
    List<CommissionAmountGroup> getBranchWithScopeGoalBetween(AgreementsGroupCondition agreementsGroupCondition);
    CommissionAmountGroup findSaleByBranchWithAmountPemex(Integer idBranch);
    List<CommissionAmountGroup> findBranchsWithTabulatorByZona(Integer idZona);
    List<CommissionAmountGroup> findBranchsWithTabulatorByRegion(Integer idRegion);
    List<CommissionAmountGroup> getBranchWithTabulatorByGBranch();
    List<CommissionAmountGroup> findBranchsWithTabulatorByDistributor(Integer idDistributor);
    List<Integer> getOnlyIdsZonasFromGroupZona();
    List getSumAmountsAndNumOfBranchsByZona(Integer idZona);
    List getSumAmountsAndNumOfBranchsByDistributorAndZona(Integer idDistributor, Integer idZona);
    List getSumAmountsAndNumOfBranchsByZonaPemex(Integer idZona);
    List getSumAmountsAndNumOfBranchsByDistributorAndZonaPemex(Integer idDistributor, Integer idZona);
    CommissionAmountGroup findZonaByBranchAndDistributorInGroupZona(Integer idDistributor, Integer idZona);
    CommissionAmountGroup findZonaByBranchAndDistributorInGroupZonaP(Integer idDistributor, Integer idZona);
    List<CommissionAmountGroup> findByGroupBranchAndTabulatorBranch();
    BigDecimal getSumAmountsByDistributorAndZona(Integer idDistributor, Integer idZona);
    Long getNumOfBranchsByDistributorAndZona(Integer idDistributor, Integer idZona);
    List<CommissionAmountGroup> findAllBranchsByZonaAndDistributor(Integer idDistributor, Integer idZona);
    BigDecimal getSumAmountsByDistributorAndZonaP(Integer idDistributor, Integer idZona);
    List<CommissionAmountGroup> findAllBranchsPByZonaAndDistributor(Integer idDistributor, Integer idZona);
    List<Integer> getOnlyIdsRegionsFromGroupRegion();
    List getSumAmountsAndNumOfBranchsByRegion(Integer idRegion);
    List getSumAmountsAndNumOfBranchsByRegionPemex(Integer idRegion);
    BigDecimal getSumAmountsByDistributorAndRegion(Integer idDistributor, Integer idRegion);
    BigDecimal getSumAmountsByDistributorAndRegionP(Integer idDistributor, Integer idRegion);
    List<CommissionAmountGroup> findAllBranchsByRegionAndDistributor(Integer idDistributor, Integer idRegion);
    List<CommissionAmountGroup> findAllBranchsPByRegionAndDistributor(Integer idDistributor, Integer idRegion);
    CommissionAmountGroup findRegionByRegionAndDistributorInGroupRegion(Integer idDistributor, Integer idRegion);
    CommissionAmountGroup findRegionByRegionAndDistributorInGroupRegionP(Integer idDistributor, Integer idRegion);
    List getSumAmountsAndNumOfBranchsByRegionAndCondition(Integer idRegion, AgreementsGroupCondition groupCondition);
    BigDecimal getSumAmountsByDistributorAndRegionAndCondition(Integer idDistributor, Integer idRegion, AgreementsGroupCondition groupCondition);
    List<CommissionAmountGroup> findAllBranchsByRegionAndDistributorAndCondition(Integer idDistributor, Integer idRegion, AgreementsGroupCondition groupCondition);
    List getSumAmountsAndNumOfBranchsByZonaAndCondition(Integer idZona, AgreementsGroupCondition groupCondition);
    BigDecimal getSumAmountsByDistributorAndZonaAndCondition(Integer idDistributor, Integer idZona, AgreementsGroupCondition groupCondition);
    List<CommissionAmountGroup> findAllBranchsByZonaAndDistributorAndCondition(Integer idDistributor, Integer idZona, AgreementsGroupCondition groupCondition);
    List<CommissionAmountGroup> getBranchsWithScopeGoalAndTabulator();
    List<CommissionAmountGroup> getBranchWithTabulatorByGBranchAndGBP();
    List<CommissionAmountGroup> findBranchsAndBPWithTabulatorByDistributor(Integer idDistributor);
    CommissionAmountGroup findZonaByBranchAndDistributorInGroupZonaGT(Integer idDistributor, Integer idZona);
    CommissionAmountGroup findRegionByRegionAndDistributorInGroupRegionGT(Integer idDistributor, Integer idRegion);
}
