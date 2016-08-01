package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo8 on 27/06/16.
 */
@Controller
@RequestMapping("/employees-history")
public class EmployeesHistoryController {

    @Autowired
    private EmployeesHistoryService employeesHistoryService;

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private EmployeesAccountsService employeesAccountsService;

    @Autowired
    private DwEmployeesService dwEmployeesService;

    @Autowired
    private DwEnterprisesService dwEnterprisesService;

    @Autowired
    private CRolesService cRolesService;

    private ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEmployeesHistories
            (
                    @RequestParam(name="status", required = false, defaultValue = "1") Integer status,
                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
                    @RequestParam(name = "idArea", required = false) Integer idArea,
                    @RequestParam(name = "idRole", required = false) Integer idRole,
                    @RequestParam(name = "startDate", required = false) String startDate,
                    @RequestParam(name = "endDate", required = false) String endDate
            ) throws IOException {

        List<EmployeesHistory> employeesHistories = new ArrayList();
        employeesHistories = employeesHistoryService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
        (status,idDistributor, idRegion, idBranch, idArea, idRole, startDate, endDate);
        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeesHistories),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idEmployeeHistory}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEmployeeHistoryById(@PathVariable Integer idEmployeeHistory) throws IOException {

        EmployeesHistory employeeHistory = employeesHistoryService.findById(idEmployeeHistory);

        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeeHistory),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/reactivation/{idEH}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> reactivation(@PathVariable Integer idEH, @RequestBody String data) throws IOException{
        JsonNode node = map.readTree(data);
        EmployeesHistory employeesHistory = employeesHistoryService.findById(idEH);
        Employees employees = employeesService.findById(employeesHistory.getIdEmployee());
        employees.setStatus(1);
        employees = employeesService.update(employees);

        EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(employees.getIdEmployee());

        DwEmployees dwEmployees = new DwEmployees();

        dwEmployees.setEmployee(employees);
        dwEmployees.setDwEnterprise(dwEnterprisesService.findByBranchAndArea(node.get("branch").get("idBranch").asInt(), node.get("area").get("idArea").asInt()));
        dwEmployees.setRole(cRolesService.findById(node.get("role").get("idRole").asInt()));
        dwEmployees.setCreationDate(LocalDateTime.now());
        dwEmployees = dwEmployeesService.save(dwEmployees);
        CActionTypes cActionType = CActionTypes.REACTIVACION;
        EmployeesHistory employeesHistories = employeesHistoryService.save(dwEmployees,cActionType,employeesAccounts.getAccount());
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeesHistories),HttpStatus.OK);
    }
}
