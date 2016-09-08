package mx.bidg.dao;

import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;

import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
public interface CommissionAmountGroupDao extends InterfaceDao<CommissionAmountGroup> {
    List<CommissionAmountGroup> getComissionsByConditon (AgreementsGroupCondition agreementsGroupCondition);
    List<CommissionAmountGroup> getBonusByConditon (AgreementsGroupCondition agreementsGroupCondition);
    List<CommissionAmountGroup> findOnlyByClaveSap ();
}
