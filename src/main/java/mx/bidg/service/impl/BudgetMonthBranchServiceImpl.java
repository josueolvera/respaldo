/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.BudgetMonthBranchDao;
import mx.bidg.model.AccessLevel;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CMonths;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.RequestTypesBudgets;
import mx.bidg.service.BudgetMonthBranchService;
import mx.bidg.service.BudgetsService;
import mx.bidg.service.DwEnterprisesService;
import mx.bidg.service.RequestTypesBudgetsService;
import mx.bidg.utils.MoneyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class BudgetMonthBranchServiceImpl implements BudgetMonthBranchService {

    @Autowired
    BudgetMonthBranchDao budgetMonthBranchDao;

    @Autowired
    RequestTypesBudgetsService requestTypesBudgetsService;

    @Autowired
    BudgetsService budgetsService;

    @Autowired
    DwEnterprisesService dwEnterprisesService;
    
    @Autowired
    CBudgetConceptsService cBudgetConceptsService;

    ObjectMapper map = new ObjectMapper();

    //En construccion
    @Override
    public BudgetMonthBranch getByRequestType(CRequestTypes cRequestTypes, Integer idGroup, Integer idArea,
            Integer idBudgetCategories, Integer idBudgetSubcategories, Integer idBranchs) {

        List<RequestTypesBudgets> requestTypesBudgetsList = requestTypesBudgetsService.findByRequestType(cRequestTypes);
        Budgets budget = budgetsService.findByCombination(idGroup, idArea, idBudgetCategories, idBudgetSubcategories);

        List<Budgets> budgetList = new ArrayList<>();

        for (RequestTypesBudgets rtb : requestTypesBudgetsList) {
            budgetList.add(rtb.getIdBudget());
        }

        return null;
    }

    @Override
    public List<BudgetMonthBranch> saveList(String data) throws Exception {

        List<BudgetMonthBranch> bmbList = new ArrayList<>();
        JsonNode jsonConcepts = map.readTree(data);

        for (JsonNode jsonRequest : jsonConcepts) {

            Budgets budget = new Budgets(jsonRequest.get("idBudget").asInt());
            Integer idDwEnterprise = jsonRequest.get("dwEnterprise").asInt();
            DwEnterprises dwEnterprise = dwEnterprisesService.findById(idDwEnterprise);
            Integer year = jsonRequest.get("year").asInt();

            String conceptName = jsonRequest.get("conceptName").asText();

            CBudgetConcepts concept = new CBudgetConcepts();
            concept.setBudgetConcept(conceptName);
            
            concept = cBudgetConceptsService.save(concept);

            BudgetMonthConcepts budgetMonthConcepts = new BudgetMonthConcepts();
            budgetMonthConcepts.setIdAccessLevel(1);
            budgetMonthConcepts.setIdBudgetConcept(concept);
            ArrayList<BudgetMonthConcepts> budgetMonthConceptsList = new ArrayList<>();
            BudgetMonthBranch budgetMonthBranch = new BudgetMonthBranch();
            BigDecimal amountConcept;
            BigDecimal amount;
            Integer idMonth;

            for (JsonNode conceptMonth : jsonRequest.get("conceptMonth")) {

                amountConcept = MoneyConverter.obtainNumber(conceptMonth.get("amountConcept").asText());
                budgetMonthConcepts.setAmount(amountConcept);

                idMonth = conceptMonth.get("month").asInt();

                budgetMonthBranch = budgetMonthBranchDao.findByCombination(budget, new CMonths(idMonth),
                        dwEnterprise, year);
//                System.out.println("Valor de BudgetMonthBranch findByCombination: " + budgetMonthBranch.toString());

                if (budgetMonthBranch == null) {

                    budgetMonthBranch = new BudgetMonthBranch();
                    budgetMonthBranch.setAmount(amountConcept);
                    budgetMonthBranch.setExpendedAmount(new BigDecimal(0));
                    budgetMonthBranch.setIdAccessLevel(new AccessLevel(1));
                    budgetMonthBranch.setIdBudget(budget);
                    budgetMonthBranch.setIdDwEnterprise(dwEnterprise);
                    budgetMonthBranch.setMonth(new CMonths(idMonth));
                    budgetMonthBranch.setYear(year);
                    budgetMonthBranchDao.save(budgetMonthBranch);

                    budgetMonthConcepts.setIdBudgetMonthBranch(budgetMonthBranch);
                    budgetMonthConceptsList.add(budgetMonthConcepts);
//                    budgetMonthBranch.setBudgetMonthConceptsList(budgetMonthConceptsList);
                    
                } else {

                    amount = budgetMonthBranch.getAmount();
                    budgetMonthConceptsList.addAll(budgetMonthBranch.getBudgetMonthConceptsList());
                    budgetMonthConceptsList.add(budgetMonthConcepts);

                    budgetMonthBranch.setAmount(amount.add(amountConcept));
                    budgetMonthBranch.setBudgetMonthConceptsList(budgetMonthConceptsList);
                    budgetMonthBranchDao.update(budgetMonthBranch);

                }
                
                bmbList.add(budgetMonthBranch);

            }

        }

        return bmbList;
    }

    @Override
    public BudgetMonthBranch findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year) {
        return budgetMonthBranchDao.findByCombination(new Budgets(budget),
                new CMonths(month), new DwEnterprises(dwEnterprise), year);
    }

    @Override
    public BudgetMonthBranch findFromRequest(String data) throws Exception {

        JsonNode jsonRequest = map.readTree(data);
        int idGroup = jsonRequest.get("idGroup").asInt();
        int idDistributor = jsonRequest.get("idDistributor").asInt();
        int idRegion = jsonRequest.get("idRegion").asInt();
        int idBranch = jsonRequest.get("idBranch").asInt();
        int idArea = jsonRequest.get("idArea").asInt();
        int month = jsonRequest.get("idMonth").asInt();
        int year = jsonRequest.get("year").asInt();
        int idCategory = jsonRequest.get("idCategory").asInt();
        int idSubcategory = jsonRequest.get("idSubcategory").asInt();

        DwEnterprises dwEnterprise = dwEnterprisesService.findByCombination(idGroup, idDistributor, idRegion, idBranch, idArea);
        Budgets budget = budgetsService.findByCombination(idGroup, idArea, idCategory, idSubcategory);

        if (dwEnterprise == null || budget == null) {
            return null;
        }

        BudgetMonthBranch budgetMonthBranch = budgetMonthBranchDao.findByCombination(budget, new CMonths(month), dwEnterprise, year);

        return budgetMonthBranch;
    }

}
