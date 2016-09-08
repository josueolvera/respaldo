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
}
