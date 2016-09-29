package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.FoliosService;
import mx.bidg.service.TravelExpensesService;
import mx.bidg.utils.BudgetHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Service
@Transactional
public class TravelExpensesServiceImpl implements TravelExpensesService {

    @Autowired
    TravelExpensesDao travelExpensesDao;

    @Autowired
    DwEnterprisesDao dwEnterprisesDao;

    @Autowired
    FoliosService foliosService;

    @Autowired
    BudgetsDao budgetsDao;

    @Autowired
    RequestsDao requestsDao;

    @Autowired
    RequestConceptDao requestConceptDao;

    @Autowired
    RequestTypesProductDao requestTypesProductDao;

    @Autowired
    BudgetYearDao budgetYearDao;

    @Autowired
    RolesCostCenterDao rolesCostCenterDao;

    @Autowired
    BudgetHelper budgetHelper;

    @Autowired
    AccountingAccountsDao accountingAccountsDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public TravelExpenses save(String data, Users user) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();

        int month = now.getMonthValue();
        int year = now.getYear();

        JsonNode jsonNode = mapper.readTree(data);
        JsonNode travelExpenseNode = jsonNode.get("travelExpense");
        CTravelTypes travelType = mapper.treeToValue(travelExpenseNode.get("travelType"), CTravelTypes.class);
        CCostCenter costCenter = mapper.treeToValue(jsonNode.get("costCenter"), CCostCenter.class);

        AccountingAccounts accountingAccount;

        if (travelType.equals(CTravelTypes.NACIONALES)) {
            accountingAccount = accountingAccountsDao.findByCategoryAndSubcategory(CBudgetCategories.GASTOS_DE_VIAJE.getIdBudgetCategory(), CBudgetSubcategories.NACIONALES.getIdBudgetSubcategory());
        } else {
            accountingAccount = accountingAccountsDao.findByCategoryAndSubcategory(CBudgetCategories.GASTOS_DE_VIAJE.getIdBudgetCategory(), CBudgetSubcategories.INTERNACIONALES.getIdBudgetSubcategory());
        }

        List<RolesCostCenter> rolesCostCenterList = rolesCostCenterDao.findByRole(user.getDwEmployee().getIdRole());
        Budgets budget = budgetsDao.findByAccountingAccountAndCostCenter(accountingAccount.getIdAccountingAccount(), costCenter.getIdCostCenter());

        if (budget != null) {

            BudgetYear budgetYear = budgetYearDao.findByBudgetAndYear(budget.getIdBudget(), year);

            if (budgetYear != null) {
                Double total = 0D;
                JsonNode currencyNode = jsonNode.get("currency");
                CCurrencies currency = mapper.treeToValue(currencyNode, CCurrencies.class);
                JsonNode requestConceptListNode = jsonNode.get("requestConceptList");

                for(JsonNode node : requestConceptListNode) {
                    total += node.get("amount").decimalValue().doubleValue();
                }

                if (currency.equals(CCurrencies.USD)) {
                    total *= (currency.getRate().doubleValue()/10);
                }

                if (budgetHelper.checkWhetherIsOutOfBudget(budgetYear, month, total)) {

                    JsonNode requestNode = jsonNode.get("request");

                    Requests request = new Requests();
                    request.setPurpose(requestNode.get("purpose").asText());
                    request.setFolio(foliosService.createNew(new CTables(51)));
                    request.setUserRequest(user);
                    request.setCreationDate(now);
                    request.setRequestStatus(CRequestStatus.PENDIENTE);
//                    request.setBudget(budget);
                    request.setIdAccessLevel(1);

                    request = requestsDao.save(request);

                    LocalDateTime startDate = LocalDateTime.parse(travelExpenseNode.get("startDate").asText() + " 00:00",formatter);
                    LocalDateTime endDate = LocalDateTime.parse(travelExpenseNode.get("endDate").asText() + " 00:00",formatter);

                    TravelExpenses travelExpense = new TravelExpenses();
                    travelExpense.setCreationDate(now);
                    travelExpense.setStartDate(startDate);
                    travelExpense.setEndDate(endDate);
                    travelExpense.setDestination(travelExpenseNode.get("destination").asText());
                    travelExpense.setEstimatedKm(travelExpenseNode.get("estimatedKm").asInt());
                    travelExpense.setTravelType(travelType);
                    travelExpense.setRequest(request);

                    for(JsonNode node : requestConceptListNode) {
                        CBudgetConcepts concept = mapper.treeToValue(node.get("concept"), CBudgetConcepts.class);
                        RequestConcept requestConcept = new RequestConcept();
                        requestConcept.setCreationDate(now);
                        requestConcept.setRequest(request);
                        requestConcept.setAmount(node.get("amount").decimalValue());
                        requestConcept.setCurrency(currency);
                        requestConcept.setBudgetConcept(concept);
                        requestConceptDao.save(requestConcept);
                    }

                    travelExpensesDao.save(travelExpense);

                    return travelExpense;
                } else {
                    throw new ValidationException("Fuera de presupuesto","Su solicitud esta fuera de presupuesto");
                }
            } else {
                throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
            }
        } else {
            throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
        }
    }

    @Override
    public TravelExpenses update(TravelExpenses travelExpenses) {
        travelExpensesDao.update(travelExpenses);
        return travelExpenses;
    }

    @Override
    public TravelExpenses findById(Integer idTravelExpenses) {
        return travelExpensesDao.findById(idTravelExpenses);
    }

    @Override
    public List<TravelExpenses> findAll() {
        return travelExpensesDao.findAll();
    }

    @Override
    public Boolean delete(TravelExpenses travelExpenses) {
        travelExpensesDao.delete(travelExpenses);
        return true;
    }

    @Override
    public List<TravelExpenses> getTravelExpenses(Integer idUser) {
        return travelExpensesDao.getTravelExpenses(idUser);
    }
}
