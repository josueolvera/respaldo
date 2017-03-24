package mx.bidg.utils;

import mx.bidg.model.*;
import mx.bidg.pojos.BudgetCategory;
import mx.bidg.pojos.BudgetSubcategory;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private CCostCenterService cCostCenterService;

    @Autowired
    private AuthorizationCostCenterService authorizationCostCenterService;

    @Autowired
    private BudgetAccountingAccountsService budgetAccountingAccountsService;

    @Autowired
    private DistributorCostCenterService distributorCostCenterService;

    /*public List<BudgetCategory> getOrderedBudget(Integer idCostCenter,Integer  idBudgetType,Integer  idBudgetNature,Integer  idBudgetCategory,Integer  year) {

        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, idBudgetType, idBudgetNature, idBudgetCategory);
        List<BudgetCategory> budgetCategories = new ArrayList<>();

        for (Budgets budget : budgets) {
            BudgetCategory budgetCategory = new BudgetCategory();
            //budgetCategory.setName(budget.getAccountingAccount().getBudgetCategory().getBudgetCategory());
            //budgetCategory.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());

            List<RealBudgetSpending> realBudgetSpendingList = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
            budgetCategory.setRealBudgetSpendings(realBudgetSpendingList);
            budgetCategories.add(budgetCategory);
        }

        return budgetCategories;
    }*/

    /*public Boolean checkWhetherIsOutOfBudget(BudgetYear budgetYear, Integer month, Double amount) {

        Double budgetAmount = 0D;
        Double budgetExpendedAmount = 0D;

        switch (month) {
            case 1:
                budgetAmount += budgetYear.getJanuaryAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getJanuaryExpendedAmount().doubleValue();
                break;
            case 2:
                budgetAmount += budgetYear.getFebruaryAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getFebruaryExpendedAmount().doubleValue();
                break;
            case 3:
                budgetAmount += budgetYear.getMarchAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getMarchExpendedAmount().doubleValue();
                break;
            case 4:
                budgetAmount += budgetYear.getAprilAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getAprilExpendedAmount().doubleValue();
                break;
            case 5:
                budgetAmount += budgetYear.getMayAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getMayExpendedAmount().doubleValue();
                break;
            case 6:
                budgetAmount += budgetYear.getJuneAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getJuneExpendedAmount().doubleValue();
                break;
            case 7:
                budgetAmount += budgetYear.getJulyAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getJulyExpendedAmount().doubleValue();
                break;
            case 8:
                budgetAmount += budgetYear.getAugustAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getAugustExpendedAmount().doubleValue();
                break;
            case 9:
                budgetAmount += budgetYear.getSeptemberAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getSeptemberExpendedAmount().doubleValue();
                break;
            case 10:
                budgetAmount += budgetYear.getOctoberAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getOctoberExpendedAmount().doubleValue();
                break;
            case 11:
                budgetAmount += budgetYear.getNovemberAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getNovemberExpendedAmount().doubleValue();
                break;
            case 12:
                budgetAmount += budgetYear.getDecemberAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getDecemberExpendedAmount().doubleValue();
        }

        return amount <= (budgetAmount - budgetExpendedAmount);
    }*/
    public List<Budgets> findIdCostCneter(Integer idCostCenter){
        //List<Budgets> budgets = budgetsService.findByIdCostCenter(idCostCenter);
        return null;
    }

    public List<RealBudgetSpending> getOrderedBudget(Integer idCostCenter,Integer  idBudgetType,Integer  idBudgetNature,Integer  year) {
        List<RealBudgetSpending>realBudgetSpendings = new ArrayList<>();
        List<DistributorCostCenter> distributorCostCenters = distributorCostCenterService.findByCostCenter(idCostCenter);
        for (DistributorCostCenter d: distributorCostCenters){
            BudgetCategory budgetCategory = new BudgetCategory();
            List<Budgets> budgets = budgetsService.findByIdDistributorCostCenter(d.getIdDistributorCostCenter(),idBudgetType,idBudgetNature);
            for (Budgets budget: budgets){
                budgetCategory.setName(budget.getConceptBudget().getNameConcept());
                budgetCategory.setIdBudgetCategory(budget.getIdConceptBudget());
                RealBudgetSpending r = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(),year);
                realBudgetSpendings.add(r);
            }
        }
        return realBudgetSpendings;
    }

    public List<RealBudgetSpending> getOrderBudget(Integer idCostCenter,Integer  year) {
        List<RealBudgetSpending>realBudgetSpendings = new ArrayList<>();
        List<DistributorCostCenter> distributorCostCenters = distributorCostCenterService.findByCostCenter(idCostCenter);
        for (DistributorCostCenter d: distributorCostCenters){
            List<Budgets> budgets = budgetsService.findByIdDistributor(d.getIdDistributorCostCenter());
            for (Budgets budget: budgets){
                RealBudgetSpending r = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(),year);
                realBudgetSpendings.add(r);
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

    public List<BudgetCategory> getOrderBudgetAuthorized(Integer idCostCenter,Integer  year) {
        /*List<Budgets> budgets = budgetsService.findByIdCostCenter(idCostCenter);
        List<BudgetCategory> budgetCategories = new ArrayList<>();
        for (Budgets budget: budgets){
            //if(budget.isValidation()){

            //}else {
              //  budget.setValidation(true);
            //}
            BudgetCategory budgetCategory = new BudgetCategory();
            budgetCategory.setName(budget.getConceptBudget().getNameConcept());
            budgetCategory.setIdBudgetCategory(budget.getIdConceptBudget());
            List<RealBudgetSpending>realBudgetSpendingList = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
            /*for(RealBudgetSpending r: realBudgetSpendingList){
                r.setAuthorized(true);
                realBudgetSpendingService.update(r);
            }
            //budgetCategory.setRealBudgetSpendings(realBudgetSpendingList);
            budgetsService.update(budget);
            budgetCategories.add(budgetCategory);
        }*/
        return null;
    }

    /*public List<BudgetCategory> modifyOrderedBudget(Integer idCostCenter, Integer  year, Users user) {
        List<Budgets> budgets = budgetsService.findByIdCostCenter(idCostCenter);
        CCostCenter c = cCostCenterService.findById(idCostCenter);
        AuthorizationCostCenter a = new AuthorizationCostCenter();
        a.setCostCenter(c);
        a.setValidation(true);
        a.setYear(year);
        a.setUsers(user);
        authorizationCostCenterService.save(a);
        List<BudgetCategory> budgetCategories = new ArrayList<>();
        for (Budgets budget: budgets){
            BudgetCategory budgetCategory = new BudgetCategory();
            budgetCategory.setName(budget.getConceptBudget().getNameConcept());
            budgetCategory.setIdBudgetCategory(budget.getIdConceptBudget());
            List<RealBudgetSpending>realBudgetSpendingList = realBudgetSpendingService.findByBudgetAndYear(budget.getIdBudget(), year);
            budgetCategory.setRealBudgetSpendings(realBudgetSpendingList);
            budgetCategories.add(budgetCategory);
            budgetsService.update(budget);
        }
        return budgetCategories;
    }*/

    public List<BudgetCategory> getOrderedBudgetNoAuthorized(Integer idCostCenter,Integer  idBudgetType,Integer  idBudgetNature,Integer  year, boolean authorized) {
        List<Budgets> budgets = budgetsService.getBudgetsfindNatureTypeAndCostCenter(idCostCenter,idBudgetType,idBudgetNature);
        List<BudgetCategory> budgetCategories = new ArrayList<>();
        for (Budgets budget: budgets){
            BudgetCategory budgetCategory = new BudgetCategory();
            budgetCategory.setName(budget.getConceptBudget().getNameConcept());
            budgetCategory.setIdBudgetCategory(budget.getIdConceptBudget());
            List<RealBudgetSpending>realBudgetSpendingList = realBudgetSpendingService.findByBudgetAndYearAndNoAuthorized(budget.getIdBudget(), year, authorized);
            // budgetCategory.setRealBudgetSpendings(realBudgetSpendingList);
            budgetCategories.add(budgetCategory);
        }
        return budgetCategories;
    }

    public List<BudgetCategory> getAuthorizationBudget(Integer idBussinessLine,Integer idDistributor,Integer idCostCenter, Integer year){
        System.out.println("Bussiness line: "+idBussinessLine+" Distributor: "+idDistributor+" CostCenter: "+idCostCenter+"year "+year);
        List<DistributorCostCenter> dcc = distributorCostCenterService.findByIdBussinessAndDistributorAndCostCenter(idBussinessLine,idDistributor,idCostCenter);
        System.out.println("Tamaño de la lista distributor: "+ dcc.size());
        List<BudgetCategory> categories = new ArrayList<>();
        for(DistributorCostCenter distributorCostCenter: dcc){
            if(distributorCostCenter.getAccountingAccounts()!=null){
                //En este momento la cuenta contable es primer nivel del año ingresado
                if(distributorCostCenter.getAccountingAccounts().getIdBudgetSubcategory()==0){
                    System.out.println("ES primer nivel");
                    BudgetCategory budgetCategory = new BudgetCategory();
                    List<Budgets> budgetsList = budgetsService.findByIdDistributor(distributorCostCenter.getIdDistributorCostCenter());
                    List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                    for(Budgets b: budgetsList){
                        RealBudgetSpending r = realBudgetSpendingService.findByIdBudgetAndYear(b.getIdBudget(),year);
                        realBudgetSpendingList.add(r);
                    }
                    budgetCategory.setLevelOne(realBudgetSpendingList);
                    //budgetCategory.setSecondLevel(new ArrayList<>());
                    categories.add(budgetCategory);
                    break;
                }
                //En este momento la cuenta contable es segundo nivel del año ingresado
                if(distributorCostCenter.getAccountingAccounts().getIdSubSubcategoies()==0){
                    System.out.println("Es segundo nivel");
                    BudgetCategory budgetCategory = new BudgetCategory();
                    BudgetSubcategory bs = new BudgetSubcategory();
                    List<BudgetSubcategory> budgetSubcategoryList = new ArrayList<>();
                    List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                    List<Budgets> budgetsList = budgetsService.findByIdDistributor(distributorCostCenter.getIdDistributorCostCenter());
                    for(Budgets b: budgetsList){
                        RealBudgetSpending r = realBudgetSpendingService.findByIdBudgetAndYear(b.getIdBudget(),year);
                        realBudgetSpendingList.add(r);
                        bs.setSecondLevel(realBudgetSpendingList);
                        bs.setThirdLevel(new ArrayList<>());
                        budgetSubcategoryList.add(bs);
                    }
                    //budgetCategory.setLevelOne(new ArrayList<>());
                    budgetCategory.setSecondLevel(budgetSubcategoryList);
                    categories.add(budgetCategory);
                    break;
                }
            }
        }
        return categories;
    }

}
