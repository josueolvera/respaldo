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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Service
@Transactional
public class TravelExpensesServiceImpl implements TravelExpensesService {

    @Autowired
    private TravelExpensesDao travelExpensesDao;

    @Autowired
    private ChecksDao checksDao;

    @Autowired
    private DwEnterprisesDao dwEnterprisesDao;

    @Autowired
    private CRequestStatusDao cRequestStatusDao;

    @Autowired
    private FoliosService foliosService;

    @Autowired
    private BudgetsDao budgetsDao;

    @Autowired
    private RequestsDao requestsDao;

    @Autowired
    private TravelExpenseConceptDao travelExpenseConceptDao;

    @Autowired
    private RequestTypesProductDao requestTypesProductDao;

    @Autowired
    private RolesCostCenterDao rolesCostCenterDao;

    @Autowired
    private BudgetHelper budgetHelper;

    @Autowired
    private AccountingAccountsDao accountingAccountsDao;

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

           /* if (budgetYear != null) {
                Double total = 0D;
                JsonNode currencyNode = jsonNode.get("currency");
                CCurrencies currency = mapper.treeToValue(currencyNode, CCurrencies.class);
                JsonNode travelExpenseConceptListNode = jsonNode.get("travelExpenseConceptList");

                for(JsonNode node : travelExpenseConceptListNode) {
                    total += node.get("amount").decimalValue().doubleValue();
                }

                if (currency.equals(CCurrencies.USD)) {
                    total *= (currency.getRate().doubleValue()/10);
                }

               /* if (budgetHelper.checkWhetherIsOutOfBudget(budgetYear, month, total)) {

                    LocalDateTime startDate = LocalDateTime.parse(travelExpenseNode.get("startDate").asText() + " 00:00",formatter);
                    LocalDateTime endDate = LocalDateTime.parse(travelExpenseNode.get("endDate").asText() + " 00:00",formatter);

                    JsonNode requestNode = jsonNode.get("request");

                    Requests request = new Requests();
                    request.setPurpose(requestNode.get("purpose").asText());
                    request.setFolio(foliosService.createNew(new CTables(51)));
                    request.setUserRequest(user);
                    request.setCreationDate(now);
                    request.setApplyingDate(startDate);
                    request.setRequestStatus(CRequestStatus.PENDIENTE);
                    request.setBudgetYear(budgetYear);
                    request.setIdAccessLevel(1);

                    request = requestsDao.save(request);

                    TravelExpenses travelExpense = new TravelExpenses();
                    travelExpense.setCreationDate(now);
                    travelExpense.setStartDate(startDate);
                    travelExpense.setEndDate(endDate);
                    travelExpense.setDestination(travelExpenseNode.get("destination").asText());
                    travelExpense.setEstimatedKm(travelExpenseNode.get("estimatedKm").asInt());
                    travelExpense.setTravelType(travelType);
                    travelExpense.setRequest(request);

                    List<TravelExpenseConcept> travelExpenseConceptList = new ArrayList<>();

                    for(JsonNode node : travelExpenseConceptListNode) {
                        CBudgetConcepts concept = mapper.treeToValue(node.get("concept"), CBudgetConcepts.class);
                        TravelExpenseConcept travelExpenseConcept = new TravelExpenseConcept();
                        travelExpenseConcept.setCreationDate(now);
                        travelExpenseConcept.setTravelExpense(travelExpense);
                        travelExpenseConcept.setAmount(node.get("amount").decimalValue());
                        travelExpenseConcept.setCurrency(currency);
                        travelExpenseConcept.setBudgetConcept(concept);
                        travelExpenseConceptList.add(travelExpenseConcept);
                    }

                    travelExpense.setTravelExpenseConceptList(travelExpenseConceptList);
                    travelExpense = travelExpensesDao.save(travelExpense);

                    return travelExpense;
                } else {
                    throw new ValidationException("Fuera de presupuesto","Su solicitud esta fuera de presupuesto");
                }
            } else {
                throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
            }
        } else {
            throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
        }*/
        }
        throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
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

    @Override
    public TravelExpenses changeRequestStatus(Integer idTravelExpense, String data) throws IOException {
        TravelExpenses travelExpense = travelExpensesDao.findById(idTravelExpense);
        JsonNode node = mapper.readTree(data);


        if(travelExpense != null) {
            CRequestStatus cRequestStatus = cRequestStatusDao.findById(node.get("status").asInt());

            if (node.hasNonNull("justification")) {
                travelExpense.getRequest().setPurpose(node.get("justification").asText());
            }

            travelExpense.getRequest().setRequestStatus(cRequestStatus);
            travelExpense = travelExpensesDao.update(travelExpense);

            if (travelExpense.getRequest().getRequestStatus().equals(CRequestStatus.APROBADA)) {
                checksDao.save(new Checks(travelExpense, travelExpense.getEndDate().plusDays(3), travelExpense.getTotalAmount()));
            }
        }

        return travelExpense;
    }
}
