package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.pojos.FilePojo;
import mx.bidg.service.FoliosService;
import mx.bidg.service.PlaneTicketsService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    FoliosService foliosService;

    @Autowired
    BudgetsDao budgetsDao;

    @Autowired
    BudgetMonthBranchDao budgetMonthBranchDao;

    @Autowired
    RequestTypesProductDao requestTypesProductDao;

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

        int month = now.getMonthValue();
        int year = now.getYear();

        CMonths cMonth = new CMonths(month);

        JsonNode node = mapper.readTree(data);

        CPlaneTicketsTypes planeTicketType = mapper.treeToValue(node.get("planeTicketType"),CPlaneTicketsTypes.class);
        String startDate = node.get("startDate").asText();

        DwEnterprises dwEnterprise = user.getDwEmployee().getDwEnterprise();

        Budgets budget = budgetsDao.findByCombination(
                dwEnterprise.getDistributor(),
                dwEnterprise.getArea(),
                CBudgetCategories.GASTOS_DE_VIAJE,
                CBudgetSubcategories.NACIONALES
        );

        CProductTypes productType = CProductTypes.NACIONALES;

        if (budget != null) {
            BudgetMonthBranch budgetMonthBranch = budgetMonthBranchDao.findByCombination(budget,cMonth,dwEnterprise,year);

            if (budgetMonthBranch != null) {

                CRequestsCategories requestsCategory = new CRequestsCategories(CRequestsCategories.BOLETOS_DE_AVION);
                RequestTypesProduct requestTypesProduct =
                        requestTypesProductDao.findByCombination(
                                requestsCategory,
                                CRequestTypes.GASTOS_DE_VIAJE,
                                productType
                        );

                Requests request = new Requests();
                request.setFolio(foliosService.createNew(new CTables(51)));
                request.setRequestTypeProduct(requestTypesProduct);
                request.setUserRequest(user);
                request.setCreationDate(now);
                request.setRequestStatus(CRequestStatus.PENDIENTE);
                request.setBudgetMonthBranch(budgetMonthBranch);
                request.setIdAccessLevel(1);

                request = requestsDao.save(request);

                PlaneTickets planeTicket = new PlaneTickets();

                planeTicket.setPlaneTicketType(planeTicketType);
                planeTicket.setCreationDate(now);
                planeTicket.setRequest(request);
                planeTicket.setStartDate(LocalDateTime.parse(startDate + " 00:00",formatter));
                planeTicket = planeTicketsDao.save(planeTicket);

                return planeTicket;

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
}
