package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.DwEmployees;
import mx.bidg.service.DwEmployeesService;
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
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
@Controller
@RequestMapping("/dw-employees")
public class DwEmployeesController {

    @Autowired
    private DwEmployeesService dwEmployeesService;

    private ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDwEmployees
            (
                    @RequestParam(name = "status", required = false) Integer status,
                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
                    @RequestParam(name = "idArea", required = false) Integer idArea,
                    @RequestParam(name = "idRole", required = false) Integer idRole,
                    @RequestParam(name = "startDate", required = false) String startDate,
                    @RequestParam(name = "endDate", required = false) String endDate
            ) throws IOException {

        List<DwEmployees> dwEmployees;

        if (status == null &&
                idDistributor == null &&
                idRegion == null &&
                idBranch == null &&
                idArea == null &&
                idRole == null &&
                startDate == null &&
                endDate == null
                ) {
            dwEmployees = dwEmployeesService.findAll();
        } else {
            dwEmployees =
                    dwEmployeesService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(
                            status,
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
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployees),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/create-report", method = RequestMethod.GET)
    public ResponseEntity<String> createReport
            (
                    @RequestParam(name = "status", required = false) Integer status,
                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
                    @RequestParam(name = "idArea", required = false) Integer idArea,
                    @RequestParam(name = "idRole", required = false) Integer idRole,
                    @RequestParam(name = "startDate", required = false) String startDate,
                    @RequestParam(name = "endDate", required = false) String endDate,
                    @RequestParam(name = "reportFileName") String reportFileName,
                    HttpServletResponse response
            ) throws IOException {

        List<DwEmployees> dwEmployees;

        if (status == null &&
                idDistributor == null &&
                idRegion == null &&
                idBranch == null &&
                idArea == null &&
                idRole == null &&
                startDate == null &&
                endDate == null
                ) {
            dwEmployees = dwEmployeesService.findAll();
        } else {
            dwEmployees =
                    dwEmployeesService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(
                            status,
                            idDistributor,
                            idRegion,
                            idBranch,
                            idArea,
                            idRole,
                            startDate,
                            endDate
                    );

        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime dateTime = LocalDateTime.now();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + reportFileName + "_" + dateTime.format(formatter) + ".xlsx"+ "\"");
        OutputStream outputStream = response.getOutputStream();
        dwEmployeesService.createReport(dwEmployees, outputStream);
        outputStream.flush();
        outputStream.close();

        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployees),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idDwEmployee}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDwEmployeeById(@PathVariable Integer idDwEmployee) throws IOException {

        DwEmployees dwEmployee = dwEmployeesService.findByIdDw(idDwEmployee);

        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployee),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/change-employee-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<String> changeEmployeeStatus(@RequestBody Integer idDwEmployee) throws IOException {

        dwEmployeesService.changeEmployeeStatus(idDwEmployee);

            return new ResponseEntity<>("OK", HttpStatus.OK);
        }

}
