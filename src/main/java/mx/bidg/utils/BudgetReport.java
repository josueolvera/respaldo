package mx.bidg.utils;

import mx.bidg.model.Budgets;
import mx.bidg.model.DistributorCostCenter;
import mx.bidg.model.RealBudgetSpending;
import mx.bidg.pojos.ReportsRealBudgetSpending;
import mx.bidg.service.BudgetsService;
import mx.bidg.service.DistributorCostCenterService;
import mx.bidg.service.RealBudgetSpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Salvador on 03/04/2017.
 */
@Component
public class BudgetReport {

    @Autowired
    private RealBudgetSpendingService realBudgetSpendingService;

    @Autowired
    private DistributorCostCenterService distributorCostCenterService;

    @Autowired
    private BudgetsService budgetsService;

    public ReportsRealBudgetSpending getReportBudget(Integer idCostCenter, Integer year){
        List<ReportsRealBudgetSpending> reportsRealBudgetSpendingList = new ArrayList<>();
        List<RealBudgetSpending> currentYearList = new ArrayList<>();
        List<RealBudgetSpending>lastYearList = new ArrayList<>();
        Integer anioAnterior = year - 1;
        ReportsRealBudgetSpending reportsRealBudgetSpending = new ReportsRealBudgetSpending();
        List<DistributorCostCenter> distributorCostCenterList =  distributorCostCenterService.findByCostCenter(idCostCenter);
        for(DistributorCostCenter d: distributorCostCenterList){
            //ReportsRealBudgetSpending reportsRealBudgetSpending = new ReportsRealBudgetSpending();
            List<Budgets> budgets = budgetsService.findByIdDistributor(d.getIdDistributorCostCenter());
            for (Budgets budget: budgets){
                //ReportsRealBudgetSpending reportsRealBudgetSpending = new ReportsRealBudgetSpending();
                RealBudgetSpending actual = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(),year);
                if(actual!=null) {
                    currentYearList.add(actual);
                    reportsRealBudgetSpending.setCurrentYear(currentYearList);
                }
                RealBudgetSpending anterior = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(),anioAnterior);
                if(anterior!=null){
                    lastYearList.add(anterior);
                    reportsRealBudgetSpending.setLastYear(lastYearList);
                }
                /*if(reportsRealBudgetSpending==null) {
                    reportsRealBudgetSpendingList.add(null);
                    //reportsRealBudgetSpendingList.add(reportsRealBudgetSpending);
                }else {
                    //reportsRealBudgetSpendingList.add(null);
                    reportsRealBudgetSpendingList.add(reportsRealBudgetSpending);
                }*/
            }
            /*if(reportsRealBudgetSpending==null) {
                reportsRealBudgetSpendingList.add(null);
                //reportsRealBudgetSpendingList.add(reportsRealBudgetSpending);
            }else {
                //reportsRealBudgetSpendingList.add(null);
                reportsRealBudgetSpendingList.add(reportsRealBudgetSpending);
            }*/
        }
        return  reportsRealBudgetSpending;
    }
}
