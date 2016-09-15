package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.FoliosService;
import mx.bidg.service.PlaneTicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gerardo8 on 13/07/16.
 */
@Service
@Transactional
public class PlaneTicketsServiceImpl implements PlaneTicketsService {

    @Autowired
    private RequestsDao requestsDao;

    @Autowired
    private PlaneTicketsDao planeTicketsDao;

    @Autowired
    FoliosService foliosService;

    @Autowired
    BudgetsDao budgetsDao;

    @Autowired
    BudgetYearConceptDao budgetYearConceptDao;

    @Autowired
    RequestTypesProductDao requestTypesProductDao;

    @Autowired
    AccountingAccountsDao accountingAccountsDao;

    @Autowired
    RolesCostCenterDao rolesCostCenterDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<PlaneTickets> findAll() {
        return planeTicketsDao.findAll();
    }

    @Override
    public PlaneTickets findById(Integer id) {
        return planeTicketsDao.findById(id);
    }

    @Override
    public PlaneTickets save(String data, Users user) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();

        Integer month = now.getMonthValue();
        Integer year = now.getYear();

        JsonNode node = mapper.readTree(data);

        CPlaneTicketsTypes planeTicketType = mapper.treeToValue(node.get("planeTicketType"),CPlaneTicketsTypes.class);
        String startDate = node.get("startDate").asText();

        AccountingAccounts accountingAccount = accountingAccountsDao.findByCategoryAndSubcategory(CBudgetCategories.GASTOS_DE_VIAJE.getIdBudgetCategory(), CBudgetSubcategories.NACIONALES.getIdBudgetSubcategory());
        List<RolesCostCenter> rolesCostCenterList = rolesCostCenterDao.findByRole(user.getDwEmployee().getIdRole());

        CCostCenter costCenter = rolesCostCenterList.get(0).getCostCenter();

        Budgets budget = budgetsDao.findByAccountingAccountAndCostCenter(accountingAccount.getIdAccountingAccount(), costCenter.getIdCostCenter());

//        if (budget != null) {
//
//            BudgetYearConcept budgetYearConcept = budgetYearConceptDao.findByBudgetAndYear(budget.getIdBudget(), year);
//
//            if (budgetYearConcept != null) {
//
//                CRequestsCategories requestsCategory = new CRequestsCategories(CRequestsCategories.BOLETOS_DE_AVION);
//                RequestTypesProduct requestTypesProduct =
//                        requestTypesProductDao.findByCombination(
//                                requestsCategory,
//                                CRequestTypes.GASTOS_DE_VIAJE,
//                                productType
//                        );
//
//                Requests request = new Requests();
//                request.setFolio(foliosService.createNew(new CTables(51)));
//                request.setRequestTypeProduct(requestTypesProduct);
//                request.setUserRequest(user);
//                request.setCreationDate(now);
//                request.setRequestStatus(CRequestStatus.PENDIENTE);
//                request.setBudgetYearConcept(budgetMonthBranch);
//                request.setIdAccessLevel(1);
//
//                request = requestsDao.save(request);
//
//                PlaneTickets planeTicket = new PlaneTickets();
//
//                planeTicket.setPlaneTicketType(planeTicketType);
//                planeTicket.setCreationDate(now);
//                planeTicket.setRequest(request);
//                planeTicket.setStartDate(LocalDateTime.parse(startDate + " 00:00",formatter));
//                planeTicket = planeTicketsDao.save(planeTicket);
//
//                return planeTicket;
//
//            } else {
//                throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
//            }
//        } else {
//            throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
//        }
        PlaneTickets planeTickets = null;
        return planeTickets;
    }

    @Override
    public PlaneTickets update(PlaneTickets planeTicket) {
        return planeTicketsDao.update(planeTicket);
    }

    @Override
    public Boolean delete(PlaneTickets planeTicket) {
        return planeTicketsDao.delete(planeTicket);
    }
}
