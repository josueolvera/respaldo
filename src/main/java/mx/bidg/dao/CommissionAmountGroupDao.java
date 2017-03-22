package mx.bidg.dao;

import mx.bidg.dao.impl.CommissionAmountGroupDaoImpl;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;

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
}
