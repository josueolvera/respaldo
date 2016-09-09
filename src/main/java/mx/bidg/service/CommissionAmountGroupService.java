package mx.bidg.service;

import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.CommissionAmountGroup;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
public interface CommissionAmountGroupService {
    CommissionAmountGroup save(CommissionAmountGroup commissionAmountGroup);
    CommissionAmountGroup update(CommissionAmountGroup commissionAmountGroup);
    CommissionAmountGroup findById(Integer idCommissionAmount);
    boolean delete (CommissionAmountGroup commissionAmountGroup);
    List<CommissionAmountGroup> findAll();
    List<CommissionAmountGroup> obtainAmountsbyGroup(List list, CAgreementsGroups agreementsGroups);
    List<CommissionAmountGroup> obtainAmountsbyBranch(List list, CAgreementsGroups agreementsGroups);
    List<CommissionAmountGroup> obtainAmountsbyZona(List list, CAgreementsGroups agreementsGroups);
    List<CommissionAmountGroup> obtainAmountsbyRegion(List list, CAgreementsGroups agreementsGroups);
    List<CommissionAmountGroup> obtainAmountsbyDistributor(List list, CAgreementsGroups agreementsGroups);
    List findByOnlyClaveSap ();
    void comissionByReport(OutputStream stream) throws IOException;
}
