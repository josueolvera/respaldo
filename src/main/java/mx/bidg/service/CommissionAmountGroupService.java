package mx.bidg.service;

import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.CommissionAmountGroup;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
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
    List<CommissionAmountGroup> obtainAmountsbyGroup(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate);
    List<CommissionAmountGroup> obtainAmountsbyAuxiliarGroup(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate);
    List<CommissionAmountGroup> obtainAmountsbyBranch(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate);
    List<CommissionAmountGroup> obtainAmountsbyZona(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate);
    List<CommissionAmountGroup> obtainAmountsbyRegion(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate);
    List<CommissionAmountGroup> obtainAmountsbyDistributor(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate);
    List findByOnlyClaveSap ();
    void comissionByReport(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException;
    List<CommissionAmountGroup> obtainBranchManager();
    List<CommissionAmountGroup> obtainAuxiliar();
    void reportMonthlyCommissions(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException;
    List<CommissionAmountGroup> getBranchWithScopeGoal();
    List<CommissionAmountGroup> obtainAmountsbySupervisor(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate);
    List<CommissionAmountGroup> getBranchsWithScopeGoalAndTabulator();
}
