package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import mx.bidg.config.JsonViews;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.Users;
import mx.bidg.service.DwEmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDwEmployees
            (
                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
                    @RequestParam(name = "idZona", required = false) Integer idZona,
                    @RequestParam(name = "idArea", required = false) Integer idArea
            ) throws IOException {

        List<DwEmployees> dwEmployees =
                dwEmployeesService.findByDistributorRegionZonaBranchAndArea(
                        idDistributor,
                        idRegion,
                        idZona,
                        idBranch,
                        idArea
                );

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployees),
                HttpStatus.OK
        );
    }

//    @RequestMapping(value = "/create-report", method = RequestMethod.GET)
//    public ResponseEntity<String> createReport
//            (
//                    @RequestParam(name = "status", required = false) Integer status,
//                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
//                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
//                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
//                    @RequestParam(name = "idArea", required = false) Integer idArea,
//                    @RequestParam(name = "idRole", required = false) Integer idRole,
//                    @RequestParam(name = "startDate", required = false) String startDate,
//                    @RequestParam(name = "endDate", required = false) String endDate,
//                    @RequestParam(name = "reportFileName") String reportFileName,
//                    HttpServletResponse response
//            ) throws IOException {
//
//        List<DwEmployees> dwEmployees;
//
//        if (status == null &&
//                idDistributor == null &&
//                idRegion == null &&
//                idBranch == null &&
//                idArea == null &&
//                idRole == null &&
//                startDate == null &&
//                endDate == null
//                ) {
//            dwEmployees = dwEmployeesService.findAll();
//        } else {
//            dwEmployees =
//                    dwEmployeesService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(
//                            status,
//                            idDistributor,
//                            idRegion,
//                            idBranch,
//                            idArea,
//                            idRole,
//                            startDate,
//                            endDate
//                    );
//
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDateTime dateTime = LocalDateTime.now();
//
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + reportFileName + "_" + dateTime.format(formatter) + ".xlsx"+ "\"");
//        OutputStream outputStream = response.getOutputStream();
//        dwEmployeesService.createReport(dwEmployees, outputStream);
//        outputStream.flush();
//        outputStream.close();
//
//        return new ResponseEntity<>(
//                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployees),
//                HttpStatus.OK
//        );
//    }

    @RequestMapping(value = "/{idDwEmployee}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDwEmployeeById(@PathVariable Integer idDwEmployee) throws IOException {

        DwEmployees dwEmployee = dwEmployeesService.findByIdDw(idDwEmployee);

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployee),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/change-employee-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> changeEmployeeStatus(@RequestBody Integer idDwEmployee, HttpSession session) throws IOException {

        Users user = (Users) session.getAttribute("user");

        dwEmployeesService.changeEmployeeStatus(idDwEmployee, user);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@RequestBody String data, HttpSession session) throws IOException {

        Users user = (Users) session.getAttribute("user");

        DwEmployees dwEmployee = dwEmployeesService.update(data, user);

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployee),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/validate-role/{idDwEnterprise}/{idRole}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>  validateRole(@PathVariable Integer idDwEnterprise, @PathVariable Integer idRole) throws IOException {
        if (!dwEmployeesService.validateExistRole(idDwEnterprise, idRole)) {
            return new ResponseEntity<>("El puesto no esta ocupado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El puesto ya esta ocupado", HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/advisers-by-branch/{idDwEnterprise}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findDwEmployeeByDwEnterpirseAndRoleAdvisers(@PathVariable Integer idDwEnterprise) throws IOException {

     List<DwEmployees> dwEmployees = dwEmployeesService.findDwEmployeeByDwEnterpirseAndRoleAdvisers(idDwEnterprise);

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployees),
                HttpStatus.OK
        );
    }
    
    
}
