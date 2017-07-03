package mx.bidg.utils;

import mx.bidg.model.*;
import mx.bidg.pojos.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo8 on 15/09/16.
 */
@Component
public class BudgetHelper {

    @Autowired
    private BudgetsService budgetsService;

    @Autowired
    private RealBudgetSpendingService realBudgetSpendingService;

    @Autowired
    private DistributorCostCenterService distributorCostCenterService;

    @Autowired
    private  RealBudgetSpendingHistoryService realBudgetSpendingHistoryService;

    public List<RealBudgetSpending> getOrderedBudget(Integer idCostCenter,Integer  idBudgetType,Integer  idBudgetNature,Integer  year) {
        List<RealBudgetSpending>realBudgetSpendings = new ArrayList<>();
        List<DistributorCostCenter> distributorCostCenters = distributorCostCenterService.findByCostCenter(idCostCenter);
        if (!distributorCostCenters.isEmpty()){
            for (DistributorCostCenter d: distributorCostCenters){
                if (d != null){
                    List<Budgets> budgets = budgetsService.findByIdDistributorCostCenter(d.getIdDistributorCostCenter(),idBudgetType,idBudgetNature);
                    if (!budgets.isEmpty()) {
                        for (Budgets budget : budgets) {
                            if (budget != null){
                                RealBudgetSpending r = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(), year);
                                if (r != null){
                                    realBudgetSpendings.add(r);
                                }
                            }
                        }
                    }
                }
            }
        }
        return realBudgetSpendings;
    }

    public List<BudgetCategory> getModifyOrderBudget(Integer idCostCenter,Integer  year) {
        /*List<Budgets> budgets = budgetsService.findByIdCostCenter(idCostCenter);
        List<BudgetCategory> budgetCategories = new ArrayList<>();
        for (Budgets budget: budgets){
            budgetsService.update(budget);
            BudgetCategory budgetCategory = new BudgetCategory();
            budgetCategory.setName(budget.getConceptBudget().getNameConcept());
            budgetCategory.setIdBudgetCategory(budget.getIdConceptBudget());
            List<RealBudgetSpending>realBudgetSpendingList = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
            //for(RealBudgetSpending r: realBudgetSpendingList){
              //  r.setAuthorized(false);
                //realBudgetSpendingService.update(r);
            //}
            //budgetCategory.setRealBudgetSpendings(realBudgetSpendingList);
            budgetCategories.add(budgetCategory);
        }*/
        return null;
    }

    public List<RealBudgetSpending> getBudgetReport(Integer idCostCenter,Integer  year) {
        List<RealBudgetSpending> realBudgetSpendings = new ArrayList<>();
        List<DistributorCostCenter> distributorCostCenters = distributorCostCenterService.findByCostCenter(idCostCenter);
        for (DistributorCostCenter d: distributorCostCenters){
            List<Budgets> budgets = budgetsService.findByIdDistributor(d.getIdDistributorCostCenter());
            for (Budgets budget: budgets){
                RealBudgetSpending r = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(),year);
                BigDecimal totalAmountLastYear = realBudgetSpendingService.getTotalBudgetAmount(budget.getIdBudget(),year-1);
                if (totalAmountLastYear != null){
                    r.setTotalLastYearAmount(totalAmountLastYear);
                }else {
                    r.setTotalLastYearAmount(new BigDecimal(0.00));
                }
                realBudgetSpendings.add(r);
            }
        }
        return realBudgetSpendings;
    }

    public List<BudgetCategory> prueba(Integer idCostCenter,Integer  year) {

        List<DistributorCostCenter> distributorCostCenterList = distributorCostCenterService.findByCostCenter(idCostCenter);
        List<BudgetCategory> categories = new  ArrayList<>();
        for (DistributorCostCenter distributorCostCenter : distributorCostCenterList){
            if (distributorCostCenter != null){
                if (distributorCostCenter.getAccountingAccounts() != null){
                    if (distributorCostCenter.getAccountingAccounts().getIdBudgetSubcategory() == 0){
                        BudgetCategory budgetCategory = new BudgetCategory();
                        budgetCategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getBudgetCategory());
                        budgetCategory.setIdBudgetCategory(distributorCostCenter.getAccountingAccounts().getIdBudgetCategory());
                        budgetCategory.setBudgetSubcategories(new ArrayList<>());
                        List<Budgets> budgetsList = budgetsService.findByIdDistributorCostCenter(distributorCostCenter.getIdDistributorCostCenter(), null, null);
                        List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                        for (Budgets budget : budgetsList){
                            List<RealBudgetSpending> realBudgetSpendings = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
                            for (RealBudgetSpending realBudgetSpending : realBudgetSpendings){
                                realBudgetSpendingList.add(realBudgetSpending);
                            }
                        }

                        if (!categories.contains(budgetCategory)) {
                            budgetCategory.setRealBudgetSpendings(realBudgetSpendingList);
                            categories.add(budgetCategory);
                        } else {
                            BudgetCategory oldBudgetCategory = categories.get(categories.indexOf(budgetCategory));
                            if (!realBudgetSpendingList.isEmpty()){
                                for (RealBudgetSpending realBudgetSpending : realBudgetSpendingList){
                                    oldBudgetCategory.getRealBudgetSpendings().add(realBudgetSpending);
                                }
                            }
                            categories.set(categories.indexOf(oldBudgetCategory), oldBudgetCategory);
                        }
                    }else if (distributorCostCenter.getAccountingAccounts().getIdBudgetSubSubcategories() == 0){
                        BudgetCategory budgetCategory = new BudgetCategory();
                        budgetCategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getBudgetCategory());
                        budgetCategory.setIdBudgetCategory(distributorCostCenter.getAccountingAccounts().getIdBudgetCategory());
                        List<Budgets> budgetsList = budgetsService.findByIdDistributorCostCenter(distributorCostCenter.getIdDistributorCostCenter(), null, null);
                        List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                        for (Budgets budget : budgetsList){
                            List<RealBudgetSpending> realBudgetSpendings = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
                            for (RealBudgetSpending realBudgetSpending : realBudgetSpendings){
                                realBudgetSpendingList.add(realBudgetSpending);
                            }
                        }

                        BudgetSubcategory budgetSubcategory = new BudgetSubcategory();
                        budgetSubcategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getBudgetSubcategory());
                        budgetSubcategory.setIdBudgetSubcategory(distributorCostCenter.getAccountingAccounts().getIdBudgetSubcategory());
                        if(!realBudgetSpendingList.isEmpty()){
                            budgetSubcategory.setRealBudgetSpendings(realBudgetSpendingList);
                        }
                        budgetSubcategory.setBudgetSubSubCategories(new ArrayList<>());

                        if (!categories.contains(budgetCategory)) {
                            List<BudgetSubcategory> budgetSubcategories = new ArrayList<>();
                            budgetSubcategories.add(budgetSubcategory);
                            budgetCategory.setBudgetSubcategories(budgetSubcategories);
                            categories.add(budgetCategory);
                        } else {
                            BudgetCategory oldBudgetCategory = categories.get(categories.indexOf(budgetCategory));
                            oldBudgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                            categories.set(categories.indexOf(oldBudgetCategory), oldBudgetCategory);
                        }
                    }else {
                        BudgetCategory budgetCategory = new BudgetCategory();
                        budgetCategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getBudgetCategory());
                        budgetCategory.setIdBudgetCategory(distributorCostCenter.getAccountingAccounts().getIdBudgetCategory());

                        BudgetSubcategory budgetSubcategory = new BudgetSubcategory();
                        budgetSubcategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getBudgetSubcategory());
                        budgetSubcategory.setIdBudgetSubcategory(distributorCostCenter.getAccountingAccounts().getIdBudgetSubcategory());

                        List<Budgets> budgetsList = budgetsService.findByIdDistributorCostCenter(distributorCostCenter.getIdDistributorCostCenter(), null, null);
                        List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                        for (Budgets budget : budgetsList){
                            List<RealBudgetSpending> realBudgetSpendings = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
                            for (RealBudgetSpending realBudgetSpending : realBudgetSpendings){
                                realBudgetSpendingList.add(realBudgetSpending);
                            }
                        }

                        BudgetSubSubCategory budgetSubSubCategory = new BudgetSubSubCategory();

                        budgetSubSubCategory.setName(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getBudgetSubSubcategory());
                        budgetSubSubCategory.setIdBudgetSubSubcategory(distributorCostCenter.getAccountingAccounts().getIdBudgetSubSubcategories());
                        budgetSubSubCategory.setRealBudgetSpendingList(realBudgetSpendingList);

                        if (!categories.contains(budgetCategory)) {
                            List<BudgetSubSubCategory> budgetSubSubCategories = new ArrayList<>();
                            budgetSubSubCategories.add(budgetSubSubCategory);
                            budgetSubcategory.setBudgetSubSubCategories(budgetSubSubCategories);
                            List<BudgetSubcategory> budgetSubcategories = new ArrayList<>();
                            budgetSubcategories.add(budgetSubcategory);
                            budgetCategory.setBudgetSubcategories(budgetSubcategories);
                            categories.add(budgetCategory);
                        } else {
                            BudgetCategory oldBudgetCategory = categories.get(categories.indexOf(budgetCategory));
                            if (oldBudgetCategory.getBudgetSubcategories().contains(budgetSubcategory)){
                                BudgetSubcategory oldBudgetSubcategory = oldBudgetCategory.getBudgetSubcategories().get(oldBudgetCategory.getBudgetSubcategories().indexOf(budgetSubcategory));
                                oldBudgetSubcategory.getBudgetSubSubCategories().add(budgetSubSubCategory);
                                oldBudgetCategory.getBudgetSubcategories().set(oldBudgetCategory.getBudgetSubcategories().indexOf(oldBudgetSubcategory), oldBudgetSubcategory);
                            }else {
                                List<BudgetSubSubCategory> budgetSubSubCategories = new ArrayList<>();
                                budgetSubSubCategories.add(budgetSubSubCategory);
                                budgetSubcategory.setBudgetSubSubCategories(budgetSubSubCategories);
                                oldBudgetCategory.getBudgetSubcategories().add(budgetSubcategory);

                            }
                            categories.set(categories.indexOf(oldBudgetCategory), oldBudgetCategory);
                        }
                    }
                }
            }
        }

        return categories;
    }

    public List<BussinessLine> prueba2(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCCs, Integer  year) {

        List<DistributorCostCenter> distributorCostCenterList = distributorCostCenterService.getAllByBusinessLineDistributorCC(idsBussinessLines, idsDistributors, idsCCs);
        List<BussinessLine> bussinessLines = new ArrayList<>();
        for (DistributorCostCenter distributorCostCenter : distributorCostCenterList){
            if (distributorCostCenter != null){
                if (distributorCostCenter.getAccountingAccounts() != null){
                    if (distributorCostCenter.getAccountingAccounts().getIdBudgetSubcategory() == 0){

                        BussinessLine bussinessLine = new BussinessLine();
                        bussinessLine.setIdBussinessLine(distributorCostCenter.getIdBussinessLine());
                        bussinessLine.setName(distributorCostCenter.getcBussinessLine().getAcronym());

                        Distributor distributor = new Distributor();
                        distributor.setIdDistributor(distributorCostCenter.getIdDistributor());
                        distributor.setName(distributorCostCenter.getDistributors().getAcronyms());

                        CostCenter costCenter = new CostCenter();
                        costCenter.setIdCostCenter(distributorCostCenter.getIdCostCenter());
                        costCenter.setName(distributorCostCenter.getCostCenter().getName());
                        costCenter.setAcronym(distributorCostCenter.getCostCenter().getAcronym());

                        BudgetCategory budgetCategory = new BudgetCategory();
                        budgetCategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getBudgetCategory());
                        budgetCategory.setFirstLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-000-0000"));
                        budgetCategory.setIdBudgetCategory(distributorCostCenter.getAccountingAccounts().getIdBudgetCategory());

                        budgetCategory.setRealBudgetSpendings(new ArrayList<>());
                        budgetCategory.setBudgetSubcategories(new ArrayList<>());
                        List<Budgets> budgetsList = budgetsService.findByIdDistributorCostCenter(distributorCostCenter.getIdDistributorCostCenter(), null, null);
                        List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                        for (Budgets budget : budgetsList){
                            List<RealBudgetSpending> realBudgetSpendings = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
                            for (RealBudgetSpending realBudgetSpending : realBudgetSpendings){
                                BigDecimal totalAmountLastYear = realBudgetSpendingService.getTotalBudgetAmount(realBudgetSpending.getIdBudget(),year-1);
                                if (totalAmountLastYear != null){
                                    realBudgetSpending.setTotalLastYearAmount(totalAmountLastYear);
                                }else {
                                    realBudgetSpending.setTotalLastYearAmount(new BigDecimal(0.00));
                                }
                                BigDecimal realTotalBudgetAmount = realBudgetSpendingHistoryService.getRealTotalBudgetAmount(realBudgetSpending.getIdBudget(),year);
                                if (realTotalBudgetAmount != null){
                                    realBudgetSpending.setRealTotalBudgetAmount(realTotalBudgetAmount);
                                }else {
                                    realBudgetSpending.setRealTotalBudgetAmount(new BigDecimal(0.00));
                                }
                                realBudgetSpendingList.add(realBudgetSpending);
                            }
                        }

                        if (!bussinessLines.contains(bussinessLine)){
                            for (RealBudgetSpending realBudgetSpending : realBudgetSpendingList){
                                budgetCategory.getRealBudgetSpendings().add(realBudgetSpending);
                            }
                            costCenter.getBudgetCategories().add(budgetCategory);
                            distributor.getCostCenterList().add(costCenter);
                            bussinessLine.getDistributorList().add(distributor);
                            bussinessLines.add(bussinessLine);
                        }else {
                            BussinessLine oldBussinessLine = bussinessLines.get(bussinessLines.indexOf(bussinessLine));
                            if (oldBussinessLine.getDistributorList().contains(distributor)){
                                Distributor oldDistributor = oldBussinessLine.getDistributorList().get(oldBussinessLine.getDistributorList().indexOf(distributor));
                                if (oldDistributor.getCostCenterList().contains(costCenter)){
                                    CostCenter oldCostCenter = oldDistributor.getCostCenterList().get(oldDistributor.getCostCenterList().indexOf(costCenter));
                                    if (oldCostCenter.getBudgetCategories().contains(budgetCategory)){
                                        BudgetCategory oldBudgetCategory = oldCostCenter.getBudgetCategories().get(oldCostCenter.getBudgetCategories().indexOf(budgetCategory));
                                        if (!realBudgetSpendingList.isEmpty()){
                                            for (RealBudgetSpending realBudgetSpending : realBudgetSpendingList){
                                                if (oldBudgetCategory.getRealBudgetSpendings() != null){
                                                    oldBudgetCategory.getRealBudgetSpendings().add(realBudgetSpending);
                                                }else {
                                                    oldBudgetCategory.setRealBudgetSpendings(new ArrayList<>());
                                                    oldBudgetCategory.getRealBudgetSpendings().add(realBudgetSpending);
                                                }
                                            }
                                        }
                                        oldCostCenter.getBudgetCategories().set(oldCostCenter.getBudgetCategories().indexOf(oldBudgetCategory), oldBudgetCategory);
                                        oldDistributor.getCostCenterList().set(oldDistributor.getCostCenterList().indexOf(oldCostCenter), oldCostCenter);
                                        oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                        bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                    }else {
                                        for (RealBudgetSpending realBudgetSpending : realBudgetSpendingList){
                                            budgetCategory.getRealBudgetSpendings().add(realBudgetSpending);
                                        }
                                        oldCostCenter.getBudgetCategories().add(budgetCategory);
                                        oldDistributor.getCostCenterList().set(oldDistributor.getCostCenterList().indexOf(oldCostCenter), oldCostCenter);
                                        oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                        bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                    }
                                }else {
                                    for (RealBudgetSpending realBudgetSpending : realBudgetSpendingList){
                                        budgetCategory.getRealBudgetSpendings().add(realBudgetSpending);
                                    }
                                    costCenter.getBudgetCategories().add(budgetCategory);
                                    oldDistributor.getCostCenterList().add(costCenter);
                                    oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                    bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                }
                            }else {
                                for (RealBudgetSpending realBudgetSpending : realBudgetSpendingList){
                                    budgetCategory.getRealBudgetSpendings().add(realBudgetSpending);
                                }
                                costCenter.getBudgetCategories().add(budgetCategory);
                                distributor.getCostCenterList().add(costCenter);
                                oldBussinessLine.getDistributorList().add(distributor);
                                bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                            }
                        }
                    }else if (distributorCostCenter.getAccountingAccounts().getIdBudgetSubSubcategories() == 0){

                        BussinessLine bussinessLine = new BussinessLine();
                        bussinessLine.setIdBussinessLine(distributorCostCenter.getIdBussinessLine());
                        bussinessLine.setName(distributorCostCenter.getcBussinessLine().getAcronym());

                        Distributor distributor = new Distributor();
                        distributor.setIdDistributor(distributorCostCenter.getIdDistributor());
                        distributor.setName(distributorCostCenter.getDistributors().getAcronyms());

                        CostCenter costCenter = new CostCenter();
                        costCenter.setIdCostCenter(distributorCostCenter.getIdCostCenter());
                        costCenter.setName(distributorCostCenter.getCostCenter().getName());
                        costCenter.setAcronym(distributorCostCenter.getCostCenter().getAcronym());

                        BudgetCategory budgetCategory = new BudgetCategory();
                        budgetCategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getBudgetCategory());
                        budgetCategory.setFirstLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-000-0000"));
                        budgetCategory.setIdBudgetCategory(distributorCostCenter.getAccountingAccounts().getIdBudgetCategory());

                        List<Budgets> budgetsList = budgetsService.findByIdDistributorCostCenter(distributorCostCenter.getIdDistributorCostCenter(), null, null);
                        List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                        for (Budgets budget : budgetsList){
                            List<RealBudgetSpending> realBudgetSpendings = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
                            for (RealBudgetSpending realBudgetSpending : realBudgetSpendings){
                                BigDecimal totalAmountLastYear = realBudgetSpendingService.getTotalBudgetAmount(realBudgetSpending.getIdBudget(),year-1);
                                if (totalAmountLastYear != null){
                                    realBudgetSpending.setTotalLastYearAmount(totalAmountLastYear);
                                }else {
                                    realBudgetSpending.setTotalLastYearAmount(new BigDecimal(0.00));
                                }
                                BigDecimal realTotalBudgetAmount = realBudgetSpendingHistoryService.getRealTotalBudgetAmount(realBudgetSpending.getIdBudget(),year);
                                if (realTotalBudgetAmount != null){
                                    realBudgetSpending.setRealTotalBudgetAmount(realTotalBudgetAmount);
                                }else {
                                    realBudgetSpending.setRealTotalBudgetAmount(new BigDecimal(0.00));
                                }
                                realBudgetSpendingList.add(realBudgetSpending);
                            }
                        }

                        BudgetSubcategory budgetSubcategory = new BudgetSubcategory();
                        budgetSubcategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getBudgetSubcategory());
                        if (distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel().length() == 1){
                            if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 1){
                                budgetSubcategory.setSecondLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-00")
                                        .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-000")
                                        .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                            } else if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 2){
                                budgetSubcategory.setSecondLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-00")
                                        .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-00")
                                        .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                            }
                        }else{
                            if (distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel().length() == 2){
                                if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 1){
                                    budgetSubcategory.setSecondLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-0")
                                            .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-000")
                                            .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                                } else if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 2){
                                    budgetSubcategory.setSecondLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-0")
                                            .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-00")
                                            .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                                }
                            }
                        }
                        budgetSubcategory.setIdBudgetSubcategory(distributorCostCenter.getAccountingAccounts().getIdBudgetSubcategory());
                        budgetSubcategory.setRealBudgetSpendings(realBudgetSpendingList);
                        budgetSubcategory.setBudgetSubSubCategories(new ArrayList<>());

                        if (!bussinessLines.contains(bussinessLine)){
                            budgetCategory.setRealBudgetSpendings(new ArrayList<>());
                            budgetCategory.setBudgetSubcategories(new ArrayList<>());
                            budgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                            costCenter.getBudgetCategories().add(budgetCategory);
                            distributor.getCostCenterList().add(costCenter);
                            bussinessLine.getDistributorList().add(distributor);
                            bussinessLines.add(bussinessLine);
                        }else {
                            BussinessLine oldBussinessLine = bussinessLines.get(bussinessLines.indexOf(bussinessLine));
                            if (oldBussinessLine.getDistributorList().contains(distributor)){
                                Distributor oldDistributor = oldBussinessLine.getDistributorList().get(oldBussinessLine.getDistributorList().indexOf(distributor));
                                if (oldDistributor.getCostCenterList().contains(costCenter)){
                                    CostCenter oldCostCenter = oldDistributor.getCostCenterList().get(oldDistributor.getCostCenterList().indexOf(costCenter));
                                    if (oldCostCenter.getBudgetCategories().contains(budgetCategory)){
                                        BudgetCategory oldBudgetCategory = oldCostCenter.getBudgetCategories().get(oldCostCenter.getBudgetCategories().indexOf(budgetCategory));
                                        if (oldBudgetCategory.getBudgetSubcategories().contains(budgetSubcategory)){
                                            BudgetSubcategory oldBudgetSubcategory = oldBudgetCategory.getBudgetSubcategories().get(oldBudgetCategory.getBudgetSubcategories().indexOf(budgetSubcategory));
                                            if (!realBudgetSpendingList.isEmpty()){
                                                for(RealBudgetSpending realBudgetSpending : realBudgetSpendingList){
                                                    if (oldBudgetSubcategory.getRealBudgetSpendings() != null) {
                                                        oldBudgetSubcategory.getRealBudgetSpendings().add(realBudgetSpending);
                                                    }else {
                                                        oldBudgetSubcategory.setRealBudgetSpendings(new ArrayList<>());
                                                        oldBudgetSubcategory.getRealBudgetSpendings().add(realBudgetSpending);
                                                    }
                                                }
                                            }
                                            oldBudgetCategory.getBudgetSubcategories().set(oldBudgetCategory.getBudgetSubcategories().indexOf(oldBudgetSubcategory), oldBudgetSubcategory);
                                            oldCostCenter.getBudgetCategories().set(oldCostCenter.getBudgetCategories().indexOf(oldBudgetCategory), oldBudgetCategory);
                                            oldDistributor.getCostCenterList().set(oldDistributor.getCostCenterList().indexOf(oldCostCenter), oldCostCenter);
                                            oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                            bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                        }else {
                                            if (oldBudgetCategory.getBudgetSubcategories() != null){
                                                oldBudgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                            }else {
                                                List<BudgetSubcategory> budgetSubcategories = new ArrayList<>();
                                                budgetSubcategories.add(budgetSubcategory);
                                                oldBudgetCategory.setBudgetSubcategories(budgetSubcategories);
                                            }
                                            oldCostCenter.getBudgetCategories().set(oldCostCenter.getBudgetCategories().indexOf(oldBudgetCategory), oldBudgetCategory);
                                            oldDistributor.getCostCenterList().set(oldDistributor.getCostCenterList().indexOf(oldCostCenter), oldCostCenter);
                                            oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                            bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                        }
                                    }else {
                                        budgetCategory.setBudgetSubcategories(new ArrayList<>());
                                        budgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                        oldCostCenter.getBudgetCategories().add(budgetCategory);
                                        oldDistributor.getCostCenterList().set(oldDistributor.getCostCenterList().indexOf(oldCostCenter), oldCostCenter);
                                        oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                        bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                    }
                                }else {
                                    budgetCategory.setBudgetSubcategories(new ArrayList<>());
                                    budgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                    costCenter.getBudgetCategories().add(budgetCategory);
                                    oldDistributor.getCostCenterList().add(costCenter);
                                    oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                    bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                }
                            }else {
                                budgetCategory.setBudgetSubcategories(new ArrayList<>());
                                budgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                costCenter.getBudgetCategories().add(budgetCategory);
                                distributor.getCostCenterList().add(costCenter);
                                oldBussinessLine.getDistributorList().add(distributor);
                                bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                            }
                        }
                    }else {

                        BussinessLine bussinessLine = new BussinessLine();
                        bussinessLine.setIdBussinessLine(distributorCostCenter.getIdBussinessLine());
                        bussinessLine.setName(distributorCostCenter.getcBussinessLine().getAcronym());

                        Distributor distributor = new Distributor();
                        distributor.setIdDistributor(distributorCostCenter.getIdDistributor());
                        distributor.setName(distributorCostCenter.getDistributors().getAcronyms());

                        CostCenter costCenter = new CostCenter();
                        costCenter.setIdCostCenter(distributorCostCenter.getIdCostCenter());
                        costCenter.setName(distributorCostCenter.getCostCenter().getName());
                        costCenter.setAcronym(distributorCostCenter.getCostCenter().getAcronym());

                        BudgetCategory budgetCategory = new BudgetCategory();
                        budgetCategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getBudgetCategory());
                        budgetCategory.setIdBudgetCategory(distributorCostCenter.getAccountingAccounts().getIdBudgetCategory());

                        BudgetSubcategory budgetSubcategory = new BudgetSubcategory();
                        budgetSubcategory.setName(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getBudgetSubcategory());
                        if (distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel().length() == 1){
                            if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 1){
                                budgetSubcategory.setSecondLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-00")
                                        .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-000")
                                        .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                            } else if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 2){
                                budgetSubcategory.setSecondLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-00")
                                        .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-00")
                                        .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                            }
                        }else{
                            if (distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel().length() == 2){
                                if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 1){
                                    budgetSubcategory.setSecondLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-0")
                                            .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-000")
                                            .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                                } else if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 2){
                                    budgetSubcategory.setSecondLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-0")
                                            .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-00")
                                            .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                                }
                            }
                        }
                        budgetSubcategory.setIdBudgetSubcategory(distributorCostCenter.getAccountingAccounts().getIdBudgetSubcategory());

                        List<Budgets> budgetsList = budgetsService.findByIdDistributorCostCenter(distributorCostCenter.getIdDistributorCostCenter(), null, null);
                        List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                        for (Budgets budget : budgetsList){
                            List<RealBudgetSpending> realBudgetSpendings = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
                            for (RealBudgetSpending realBudgetSpending : realBudgetSpendings){
                                BigDecimal totalAmountLastYear = realBudgetSpendingService.getTotalBudgetAmount(realBudgetSpending.getIdBudget(),year-1);
                                if (totalAmountLastYear != null){
                                    realBudgetSpending.setTotalLastYearAmount(totalAmountLastYear);
                                }else {
                                    realBudgetSpending.setTotalLastYearAmount(new BigDecimal(0.00));
                                }
                                BigDecimal realTotalBudgetAmount = realBudgetSpendingHistoryService.getRealTotalBudgetAmount(realBudgetSpending.getIdBudget(),year);
                                if (realTotalBudgetAmount != null){
                                    realBudgetSpending.setRealTotalBudgetAmount(realTotalBudgetAmount);
                                }else {
                                    realBudgetSpending.setRealTotalBudgetAmount(new BigDecimal(0.00));
                                }
                                realBudgetSpendingList.add(realBudgetSpending);
                            }
                        }

                        BudgetSubSubCategory budgetSubSubCategory = new BudgetSubSubCategory();

                        budgetSubSubCategory.setName(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getBudgetSubSubcategory());
                        budgetSubSubCategory.setIdBudgetSubSubcategory(distributorCostCenter.getAccountingAccounts().getIdBudgetSubSubcategories());
                        budgetSubSubCategory.setRealBudgetSpendingList(realBudgetSpendingList);

                                if (distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel().length() == 1) {

                                    if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 1) {
                                        budgetSubSubCategory.setThirdLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-00")
                                                .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-000")
                                                .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                                    }else if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 2) {
                                        budgetSubSubCategory.setThirdLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-00")
                                                .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-00")
                                                .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                                    }
                                }else{
                                    if (distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel().length() == 2) {

                                        if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 1) {
                                            budgetSubSubCategory.setThirdLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-0")
                                                    .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-000")
                                                    .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));


                                        }else if (distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel().length() == 2) {
                                            budgetSubSubCategory.setThirdLevel(distributorCostCenter.getAccountingAccounts().getBudgetCategory().getFirstLevel().concat("-0")
                                                    .concat(distributorCostCenter.getAccountingAccounts().getBudgetSubcategory().getSecondLevel()).concat("-00")
                                                    .concat(distributorCostCenter.getAccountingAccounts().getcBudgetSubSubcategories().getThirdLevel()));

                                        }
                                    }
                                }


                        if (!bussinessLines.contains(bussinessLine)){
                            budgetSubcategory.setRealBudgetSpendings(new ArrayList<>());
                            budgetSubcategory.setBudgetSubSubCategories(new ArrayList<>());
                            budgetSubcategory.getBudgetSubSubCategories().add(budgetSubSubCategory);
                            budgetCategory.setRealBudgetSpendings(new ArrayList<>());
                            budgetCategory.setBudgetSubcategories(new ArrayList<>());
                            budgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                            costCenter.getBudgetCategories().add(budgetCategory);
                            distributor.getCostCenterList().add(costCenter);
                            bussinessLine.getDistributorList().add(distributor);
                            bussinessLines.add(bussinessLine);
                        }else {
                            BussinessLine oldBussinessLine = bussinessLines.get(bussinessLines.indexOf(bussinessLine));
                            if (oldBussinessLine.getDistributorList().contains(distributor)){
                                Distributor oldDistributor = oldBussinessLine.getDistributorList().get(oldBussinessLine.getDistributorList().indexOf(distributor));
                                if (oldDistributor.getCostCenterList().contains(costCenter)){
                                    CostCenter oldCostCenter = oldDistributor.getCostCenterList().get(oldDistributor.getCostCenterList().indexOf(costCenter));
                                    if (oldCostCenter.getBudgetCategories().contains(budgetCategory)){
                                        BudgetCategory oldBudgetCategory = oldCostCenter.getBudgetCategories().get(oldCostCenter.getBudgetCategories().indexOf(budgetCategory));
                                        if (oldBudgetCategory.getBudgetSubcategories().contains(budgetSubcategory)){
                                            BudgetSubcategory oldBudgetSubcategory = oldBudgetCategory.getBudgetSubcategories().get(oldBudgetCategory.getBudgetSubcategories().indexOf(budgetSubcategory));
                                            if (oldBudgetSubcategory.getBudgetSubSubCategories() != null){
                                                oldBudgetSubcategory.getBudgetSubSubCategories().add(budgetSubSubCategory);
                                            }else {
                                                oldBudgetSubcategory.setBudgetSubSubCategories(new ArrayList<>());
                                                oldBudgetSubcategory.getBudgetSubSubCategories().add(budgetSubSubCategory);
                                            }

                                            oldBudgetCategory.getBudgetSubcategories().set(oldBudgetCategory.getBudgetSubcategories().indexOf(oldBudgetSubcategory), oldBudgetSubcategory);
                                            oldCostCenter.getBudgetCategories().set(oldCostCenter.getBudgetCategories().indexOf(oldBudgetCategory), oldBudgetCategory);
                                            oldDistributor.getCostCenterList().set(oldDistributor.getCostCenterList().indexOf(oldCostCenter), oldCostCenter);
                                            oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                            bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                        }else {
                                            budgetSubcategory.setBudgetSubSubCategories(new ArrayList<>());
                                            budgetSubcategory.getBudgetSubSubCategories().add(budgetSubSubCategory);
                                            if (oldBudgetCategory.getBudgetSubcategories() != null){
                                                oldBudgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                            }else {
                                                oldBudgetCategory.setBudgetSubcategories(new ArrayList<>());
                                                oldBudgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                            }
                                            oldCostCenter.getBudgetCategories().set(oldCostCenter.getBudgetCategories().indexOf(oldBudgetCategory), oldBudgetCategory);
                                            oldDistributor.getCostCenterList().set(oldDistributor.getCostCenterList().indexOf(oldCostCenter), oldCostCenter);
                                            oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                            bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                        }
                                    }else {
                                        budgetSubcategory.setBudgetSubSubCategories(new ArrayList<>());
                                        budgetSubcategory.getBudgetSubSubCategories().add(budgetSubSubCategory);
                                        budgetCategory.setBudgetSubcategories(new ArrayList<>());
                                        budgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                        oldCostCenter.getBudgetCategories().add(budgetCategory);
                                        oldDistributor.getCostCenterList().set(oldDistributor.getCostCenterList().indexOf(oldCostCenter), oldCostCenter);
                                        oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                        bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                    }
                                }else {
                                    budgetSubcategory.setBudgetSubSubCategories(new ArrayList<>());
                                    budgetSubcategory.getBudgetSubSubCategories().add(budgetSubSubCategory);
                                    budgetCategory.setBudgetSubcategories(new ArrayList<>());
                                    budgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                    costCenter.getBudgetCategories().add(budgetCategory);
                                    oldDistributor.getCostCenterList().add(costCenter);
                                    oldBussinessLine.getDistributorList().set(oldBussinessLine.getDistributorList().indexOf(oldDistributor), oldDistributor);
                                    bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                                }
                            }else {
                                budgetSubcategory.setBudgetSubSubCategories(new ArrayList<>());
                                budgetSubcategory.getBudgetSubSubCategories().add(budgetSubSubCategory);
                                budgetCategory.setBudgetSubcategories(new ArrayList<>());
                                budgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                                costCenter.getBudgetCategories().add(budgetCategory);
                                distributor.getCostCenterList().add(costCenter);
                                oldBussinessLine.getDistributorList().add(distributor);
                                bussinessLines.set(bussinessLines.indexOf(oldBussinessLine),oldBussinessLine);
                            }
                        }
                    }
                }
            }
        }

        return bussinessLines;
    }

}
