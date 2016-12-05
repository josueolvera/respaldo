package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.FoliosService;
import mx.bidg.service.PlaneTicketsService;
import mx.bidg.utils.BudgetHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private CRequestStatusDao cRequestStatusDao;

    @Autowired
    private FoliosService foliosService;

    @Autowired
    private BudgetsDao budgetsDao;

    @Autowired
    private BudgetYearDao budgetYearDao;

    @Autowired
    private RolesCostCenterDao rolesCostCenterDao;

    @Autowired
    private BudgetHelper budgetHelper;

    @Autowired
    private AccountingAccountsDao accountingAccountsDao;

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
        Budgets budget = budgetsDao.findByAccountingAccountAndCostCenter(accountingAccount.getIdAccountingAccount(), rolesCostCenterList.get(0).getCostCenter().getIdCostCenter());

        if (budget != null) {

            BudgetYear budgetYear = budgetYearDao.findByBudgetAndYear(budget.getIdBudget(), year);

            if (budgetYear != null) {

                if (budgetHelper.checkWhetherIsOutOfBudget(budgetYear, month, 0D)) {

                    Requests request = new Requests();
                    request.setFolio(foliosService.createNew(new CTables(51)));
                    request.setUserRequest(user);
                    request.setCreationDate(now);
                    request.setApplyingDate(LocalDateTime.parse(startDate + " 00:00",formatter));
                    request.setRequestStatus(CRequestStatus.SIN_CONFIRMACION);
                    request.setBudgetYear(budgetYear);
                    request.setIdAccessLevel(1);

                    request = requestsDao.save(request);

                    PlaneTickets planeTicket = new PlaneTickets();

                    planeTicket.setPlaneTicketType(planeTicketType);
                    planeTicket.setCreationDate(now);
                    planeTicket.setRequest(request);
                    planeTicket = planeTicketsDao.save(planeTicket);

                    return planeTicket;

                } else {
                    throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
                }

            } else {
                throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
            }
        } else {
            throw new ValidationException("Sin presupuesto","No tiene presupuesto asignado para este tipo de solicitud");
        }
    }

    @Override
    public PlaneTickets update(PlaneTickets planeTicket) {
        return planeTicketsDao.update(planeTicket);
    }

    @Override
    public Boolean delete(PlaneTickets planeTicket) {
        return planeTicketsDao.delete(planeTicket);
    }

    @Override
    public List<PlaneTickets> getPlaneTickets(Integer idUser) {
        return planeTicketsDao.getPlaneTickets(idUser);
    }

    @Override
    public PlaneTickets changeRequestStatus(Integer idPlaneTicket, String data) throws IOException {
        PlaneTickets planeTicket = planeTicketsDao.findById(idPlaneTicket);
        JsonNode node = mapper.readTree(data);


        if(planeTicket != null) {
            CRequestStatus cRequestStatus = cRequestStatusDao.findById(node.get("status").asInt());

            if (node.hasNonNull("justification")) {
                planeTicket.getRequest().setPurpose(node.get("justification").asText());
            }

            planeTicket.getRequest().setRequestStatus(cRequestStatus);
            planeTicket = planeTicketsDao.update(planeTicket);

        }

        return planeTicket;
    }
}
