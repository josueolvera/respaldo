package mx.bidg.service;

import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;

import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
public interface AgreementsGroupConditionService {
    AgreementsGroupCondition save(AgreementsGroupCondition agreementsGroupCondition);
    AgreementsGroupCondition update (AgreementsGroupCondition agreementsGroupCondition);
    AgreementsGroupCondition findById(Integer idGroupCondition);
    boolean delete (AgreementsGroupCondition agreementsGroupCondition);
    List<AgreementsGroupCondition> findAll();
    List<CommissionAmountGroup> setTabulator (List <AgreementsGroupCondition> agreementsGroupConditionList);
    List<AgreementsGroupCondition> conditions(Integer idAg, Integer idDateCalculation);
    List<AgreementsGroupCondition> findByGroupCondition(Integer idAg);
    AgreementsGroupCondition updateStatus(Integer idGroupCondition, boolean statusBoolean);
    AgreementsGroupCondition getFinalOrder(Integer idAg);
    List<CommissionAmountGroup> obtainCommissionByGoalBranchToZona(List <AgreementsGroupCondition> agreementsGroupConditionList);
    List<CommissionAmountGroup> obtainCommissionByGoalBranchToRegion(List <AgreementsGroupCondition> agreementsGroupConditionList);
    List<CommissionAmountGroup> obtainCommissionByGoalBranchToDistributor(List <AgreementsGroupCondition> agreementsGroupConditionList);
}
