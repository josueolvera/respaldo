package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.EmployeesHistory;
import mx.bidg.service.EmployeesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by gerardo8 on 27/06/16.
 */
@Controller
@RequestMapping("/employees-history")
public class EmployeesHistoryController {

    @Autowired
    private EmployeesHistoryService employeesHistoryService;

    private ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEmployeesHistories
            (
                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
                    @RequestParam(name = "idArea", required = false) Integer idArea,
                    @RequestParam(name = "idRole", required = false) Integer idRole,
                    @RequestParam(name = "startDate", required = false) String startDate,
                    @RequestParam(name = "endDate", required = false) String endDate
            ) throws IOException {

        List<EmployeesHistory> employeesHistories;

        if (idDistributor == null &&
                idRegion == null &&
                idBranch == null &&
                idArea == null &&
                idRole == null &&
                startDate == null &&
                endDate == null
                ) {
            employeesHistories = employeesHistoryService.findAll();
        } else {
            employeesHistories =
                    employeesHistoryService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(
                            idDistributor,
                            idRegion,
                            idBranch,
                            idArea,
                            idRole,
                            startDate,
                            endDate
                    );

        }

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
}
