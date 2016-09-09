package mx.bidg.dao;

import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;

import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
public interface AgreementsGroupConditionDao extends InterfaceDao<AgreementsGroupCondition> {
    List<AgreementsGroupCondition> conditionList (Integer idAg);
    AgreementsGroupCondition getTabulator(Integer orden, AgreementsGroupCondition aGC);
    List<AgreementsGroupCondition> listByAgreementGroup(Integer idAg);
    AgreementsGroupCondition updateStatus(Integer idGroupCondition, boolean statusBoolean);
    AgreementsGroupCondition save(AgreementsGroupCondition agreementsGroupCondition);
    AgreementsGroupCondition getFinalOrder(Integer idAg);
}
