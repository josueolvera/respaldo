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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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

    @Autowired
    private CDistributorsService cDistributorsService;

    @Autowired
    private CActionTypesService cActionTypesService;

    @Autowired
    private PerceptionsDeductionsService perceptionsDeductionsService;

    @Autowired
    private CPerceptionsDeductionsService cPerceptionsDeductionsService;

    @Autowired
    private SqlQueriesService sqlQueriesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEmployeesHistories
            (
                    @RequestParam(name="status", required = false, defaultValue = "1") Integer status,
                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
                    @RequestParam(name = "idZona", required = false) Integer idZona,
                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
                    @RequestParam(name = "idArea", required = false) Integer idArea,
                    @RequestParam(name = "idRole", required = false) Integer idRole,
                    @RequestParam(name = "fullname", required = false) String fullname,
                    @RequestParam(name = "rfc", required = false) String rfc,
                    @RequestParam(name = "startDate", required = false) String startDate,
                    @RequestParam(name = "endDate", required = false) String endDate,
                    @RequestParam(name = "idReport", required = false) Integer idReport,
                    @RequestParam(name = "idEmployee", required = false) Integer idEmployee
            ) throws IOException {

        List<CDistributors> distributors;

        if (idDistributor != null){
            distributors = new ArrayList<>();
            distributors.add(new CDistributors(idDistributor));
        }else{
            if (idReport == null){
                distributors =  cDistributorsService.findAll();
            } else if(idReport.equals(1)){
                distributors = cDistributorsService.getDistributorForSaem(null, true);
            } else if(idReport.equals(2)){
                distributors = cDistributorsService.getDistributorForSaem(null, false);
            } else{
                distributors = cDistributorsService.findAll();
            }
        }
        List<EmployeesHistory> employeesHistories = new ArrayList();
        employeesHistories = employeesHistoryService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
        (status,distributors, idRegion, idZona,idBranch, idArea, idRole, fullname, rfc, startDate, endDate, idEmployee);

        for(EmployeesHistory employeesHistory : employeesHistories){
            if (employeesHistory != null){
                if (employeesHistory.getIdDwEnterprise() != null){
                    DwEnterprises dwEnterprises = dwEnterprisesService.findById(employeesHistory.getIdDwEnterprise());
                    employeesHistory.setDwEnterprisesR(dwEnterprises);
                }
                if (employeesHistory.getIdRole() != null){
                    CRoles roles = cRolesService.findById(employeesHistory.getIdRole());
                    employeesHistory.setRolesR(roles);
                }
                if(employeesHistory.getIdActionType() != null){
                    CActionTypes actionTypes = cActionTypesService.findById(employeesHistory.getIdActionType());
                    employeesHistory.setActionTypesR(actionTypes);
                }
            }
        }
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeesHistories),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idEmployeeHistory}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEmployeeHistoryById(@PathVariable Integer idEmployeeHistory) throws IOException {

        EmployeesHistory employeeHistory = employeesHistoryService.findById(idEmployeeHistory);

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeeHistory),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/reactivation/{idEH}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> reactivation(@PathVariable Integer idEH, @RequestBody String data, HttpSession session) throws IOException{
        JsonNode node = mapper.readTree(data);
        Users user = (Users) session.getAttribute("user");
        EmployeesHistory employeesHistory = employeesHistoryService.findById(idEH);
        Employees employees = employeesService.findById(employeesHistory.getIdEmployee());
        employees.setStatus(1);
        employees = employeesService.update(employees);

        EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(employees.getIdEmployee());

        DwEmployees dwEmployees = new DwEmployees();

        dwEmployees.setEmployee(employees);

        List<DwEnterprises> dwEnterprisesList;

        if(node.get("dwEnterprise").get("distributor").get("idDistributor").asInt() == 2 || node.get("dwEnterprise").get("distributor").get("idDistributor").asInt() == 3){

            if( node.get("dwEnterprise").get("zona").get("idZonas").asInt() == 0 && node.get("dwEnterprise").get("branch").get("idBranch").asInt() == 0){

                dwEnterprisesList = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(
                        node.get("dwEnterprise").get("distributor").get("idDistributor").asInt(),
                        node.get("dwEnterprise").get("region").get("idRegion").asInt(),0,0,
                        node.get("dwEnterprise").get("area").get("idArea").asInt());

            } else if (node.get("dwEnterprise").get("branch").get("idBranch").asInt() == 0){

                dwEnterprisesList = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(
                        node.get("dwEnterprise").get("distributor").get("idDistributor").asInt(),
                        node.get("dwEnterprise").get("region").get("idRegion").asInt(),
                        node.get("dwEnterprise").get("zona").get("idZonas").asInt(),0,
                        node.get("dwEnterprise").get("area").get("idArea").asInt());

            } else {

                dwEnterprisesList = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(
                        node.get("dwEnterprise").get("distributor").get("idDistributor").asInt(),
                        node.get("dwEnterprise").get("region").get("idRegion").asInt(),
                        node.get("dwEnterprise").get("zona").get("idZonas").asInt(),
                        node.get("dwEnterprise").get("branch").get("idBranch").asInt(),
                        node.get("dwEnterprise").get("area").get("idArea").asInt());
            }
        } else {
            dwEnterprisesList = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(node.get("dwEnterprise").get("distributor").get("idDistributor").asInt(),
                    null,null,node.get("dwEnterprise").get("branch").get("idBranch").asInt(),
                    node.get("dwEnterprise").get("area").get("idArea").asInt());
        }

        dwEmployees.setDwEnterprise(dwEnterprisesList.get(0));
        dwEmployees.setRole(cRolesService.findById(node.get("role").get("idRole").asInt()));
        dwEmployees.setCreationDate(LocalDateTime.now());
        dwEmployees = dwEmployeesService.save(dwEmployees);
        CActionTypes cActionType = CActionTypes.REACTIVACION;
        EmployeesHistory employeesHistories = employeesHistoryService.save(dwEmployees,cActionType,employeesAccounts.getAccount(),user);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeesHistories),HttpStatus.OK);
    }

    @RequestMapping(value = "/create-report", method = RequestMethod.GET)
    public ResponseEntity<String> createReport
            (
                    @RequestParam(name="status", required = false, defaultValue = "1") Integer status,
                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
                    @RequestParam(name = "idZona", required = false) Integer idZona,
                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
                    @RequestParam(name = "idArea", required = false) Integer idArea,
                    @RequestParam(name = "idRole", required = false) Integer idRole,
                    @RequestParam(name = "fullname", required = false) String fullname,
                    @RequestParam(name = "rfc", required = false) String rfc,
                    @RequestParam(name = "startDate", required = false) String startDate,
                    @RequestParam(name = "endDate", required = false) String endDate,
                    @RequestParam(name = "reportFileName") String reportFileName,
                    @RequestParam(name = "idReport", required = false) Integer idReport,
                    @RequestParam(name = "idEmployee", required = false) Integer idEmployee,
                    HttpServletResponse response
            ) throws IOException {

        List<CDistributors> cDistributorsList;
        List<EmployeesHistory> employeesHistories = new ArrayList();
        OutputStream outputStream;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime dateTime = LocalDateTime.now();

        switch (idReport){
            case 1:
                if (idDistributor != null){
                    cDistributorsList = new ArrayList<>();
                    cDistributorsList.add(new CDistributors(idDistributor));
                }else{
                    cDistributorsList = cDistributorsService.getDistributorForSaem(null, true);
                }

                employeesHistories = employeesHistoryService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
                        (status,cDistributorsList, idRegion, idZona,idBranch, idArea, idRole, fullname, rfc,startDate, endDate,idEmployee);

                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + reportFileName + "_" + dateTime.format(formatter) + ".xlsx"+ "\"");
                outputStream = response.getOutputStream();
                dwEmployeesService.createReportDistributors(employeesHistories, outputStream);
                outputStream.flush();
                outputStream.close();

                break;
            case 2:
                if (idDistributor != null){
                    cDistributorsList = new ArrayList<>();
                    cDistributorsList.add(new CDistributors(idDistributor));
                }else{
                    cDistributorsList = cDistributorsService.getDistributorForSaem(null, false);
                }

                employeesHistories = employeesHistoryService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
                        (status,cDistributorsList, idRegion, idZona,idBranch, idArea, idRole, fullname, rfc,startDate, endDate, idEmployee);

                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + reportFileName + "_" + dateTime.format(formatter) + ".xlsx"+ "\"");
                outputStream = response.getOutputStream();
                dwEmployeesService.createReportCompanys(employeesHistories, outputStream);
                outputStream.flush();
                outputStream.close();

                break;
            case 3:
                cDistributorsList = cDistributorsService.findAll();

                employeesHistories = employeesHistoryService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
                        (status,cDistributorsList, idRegion, idZona,idBranch, idArea, idRole, fullname, rfc,startDate, endDate, idEmployee);

                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + reportFileName + "_" + dateTime.format(formatter) + ".xlsx"+ "\"");
                outputStream = response.getOutputStream();
                dwEmployeesService.createReportBpo(employeesHistories, outputStream);
                outputStream.flush();
                outputStream.close();

                break;
        }
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeesHistories),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/get-perception",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createReport
            (
                    @RequestParam (name = "idEmployee",required = false)Integer idEmployee,
                    @RequestParam (name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "startDate", required = false) String startDate,
                    @RequestParam(name = "endDate", required = false) String endDate) throws IOException{

        LocalDateTime ofDate = (startDate == null || startDate.equals("")) ? null :
                LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (endDate == null || endDate.equals("")) ? null :
                LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);
                    List<EmployeesHistory>resultFilter = new ArrayList();
                    BigDecimal suma = new BigDecimal(0);
                    BigDecimal resta = new BigDecimal(0);
                    BigDecimal total = new BigDecimal(0);
                    EmployeesHistory employee;
                    if(idEmployee!=null && ofDate!=null && untilDate!=null){
                        List<PerceptionsDeductions> range = perceptionsDeductionsService.findByIdEmployeeAndStartDateEndDate(idEmployee,ofDate,untilDate);
                        for(PerceptionsDeductions p: range){
                            employee = employeesHistoryService.findIdEmployee(idEmployee);
                            CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                            if(cp.getIdTypeOperation()==2){
                                suma = suma.add(p.getAmount());
                            }else if(cp.getIdTypeOperation()==1){
                                resta = resta.add(p.getAmount());
                            }
                        total = suma.subtract(resta);
                        employee.setTotalSumPerception(suma);
                        employee.setTotalSumDeduction(resta);
                        employee.setTotal(total);
                        resultFilter.add(employee);
                        }
                }else if(idDistributor!=null&& ofDate!=null&&untilDate!=null){
                    List<PerceptionsDeductions> range = perceptionsDeductionsService.findByStartDateEndDate(ofDate,untilDate);
                        for (PerceptionsDeductions p: range){
                            List<EmployeesHistory>getDistributors = employeesHistoryService.findIdEmployeeAndIdDistributor(p.getIdEmployee(),idDistributor);
                            for(EmployeesHistory eh: getDistributors) {
                                employee = employeesHistoryService.findIdEmployee(eh.getIdEmployee());
                                CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                                if (cp.getIdTypeOperation() == 2) {
                                    suma = suma.add(p.getAmount());
                                } else if (cp.getIdTypeOperation() == 1) {
                                    resta = resta.add(p.getAmount());
                                }
                                total = suma.subtract(resta);
                                employee.setTotalSumPerception(suma);
                                employee.setTotalSumDeduction(resta);
                                employee.setTotal(total);
                                resultFilter.add(employee);
                            }
                        }
                    } else if(idEmployee!=null){
                        employee= employeesHistoryService.findIdEmployee(idEmployee);
                        List<PerceptionsDeductions> perceptionsDeductions =  perceptionsDeductionsService.findByIdEmployee(idEmployee);
                        for(PerceptionsDeductions p : perceptionsDeductions){
                            CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                            if(cp.getIdTypeOperation()==2){
                                suma = suma.add(p.getAmount());
                            }else if(cp.getIdTypeOperation()==1){
                                resta = resta.add(p.getAmount());
                            }
                    }
                    total = suma.subtract(resta);
                    employee.setTotalSumPerception(suma);
                    employee.setTotalSumDeduction(resta);
                    employee.setTotal(total);
                    resultFilter.add(employee);
                    }else if(idDistributor!=null){
                        employee=null;
                        List <EmployeesHistory> employeesHist = employeesHistoryService.findByIdDistributor(idDistributor);
                        for(EmployeesHistory e : employeesHist) {
                            employee=e;
                            List<PerceptionsDeductions> perceptionsForEmployee = perceptionsDeductionsService.findByIdEmployee(e.getIdEmployee());
                            for(PerceptionsDeductions p: perceptionsForEmployee){
                            CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                            if(cp.getIdTypeOperation()==2){
                                suma = suma.add(p.getAmount());
                            }else if(cp.getIdTypeOperation()==1){
                                resta = resta.add(p.getAmount());
                            }
                        }
                        total = suma.subtract(resta);
                        employee.setTotalSumPerception(suma);
                        employee.setTotalSumDeduction(resta);
                        employee.setTotal(total);
                        resultFilter.add(employee);
            }
        }else if(ofDate!=null && untilDate!=null){
            List<PerceptionsDeductions> range = perceptionsDeductionsService.findByStartDateEndDate(ofDate,untilDate);
            for(PerceptionsDeductions p: range){
                employee = employeesHistoryService.findIdEmployee(p.getIdEmployee());
                CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                if(cp.getIdTypeOperation()==2){
                    suma = suma.add(p.getAmount());
                }else if(cp.getIdTypeOperation()==1){
                    resta = resta.add(p.getAmount());
                }
                total = suma.subtract(resta);
                employee.setTotalSumPerception(suma);
                employee.setTotalSumDeduction(resta);
                employee.setTotal(total);
                resultFilter.add(employee);
            }
        }
            return new ResponseEntity<>(
                        mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(resultFilter),
                        HttpStatus.OK);
    }


    @RequestMapping(value = "/report-by-employee",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>reportEmployee(@RequestParam (name = "idEmployee",required = false)Integer idEmployee,
                                                @RequestParam (name = "idDistributor", required = false) Integer idDistributor,
                                                @RequestParam(name = "startDate", required = false) String startDate,
                                                @RequestParam(name = "endDate", required = false) String endDate
                                                ,@RequestParam(name = "fileName",required = true)String fileName
                                                , HttpServletResponse response) throws IOException{
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +".xlsx\"");
        LocalDateTime ofDate = (startDate == null || startDate.equals("")) ? null :
                LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (endDate == null || endDate.equals("")) ? null :
                LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);
        List<EmployeesHistory>resultFilter = new ArrayList();
        BigDecimal suma = new BigDecimal(0);
        BigDecimal resta = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
        EmployeesHistory employee;
        if(idEmployee!=null && ofDate!=null && untilDate!=null){
            List<PerceptionsDeductions> range = perceptionsDeductionsService.findByIdEmployeeAndStartDateEndDate(idEmployee,ofDate,untilDate);
            for(PerceptionsDeductions p: range){
                employee = employeesHistoryService.findIdEmployee(idEmployee);
                CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                if(cp.getIdTypeOperation()==2){
                    suma = suma.add(p.getAmount());
                }else if(cp.getIdTypeOperation()==1){
                    resta = resta.add(p.getAmount());
                }
                total = suma.subtract(resta);
                employee.setTotalSumPerception(suma);
                employee.setTotalSumDeduction(resta);
                employee.setTotal(total);
                resultFilter.add(employee);
            }
            perceptionsDeductionsService.reporUnfold(resultFilter,outputStream);
        }else if(idDistributor!=null&& ofDate!=null&&untilDate!=null){
            List<PerceptionsDeductions> range = perceptionsDeductionsService.findByStartDateEndDate(ofDate,untilDate);
            for (PerceptionsDeductions p: range){
                List<EmployeesHistory>getDistributors = employeesHistoryService.findIdEmployeeAndIdDistributor(p.getIdEmployee(),idDistributor);
                for(EmployeesHistory eh: getDistributors) {
                    employee = employeesHistoryService.findIdEmployee(eh.getIdEmployee());
                    CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                    if (cp.getIdTypeOperation() == 2) {
                        suma = suma.add(p.getAmount());
                    } else if (cp.getIdTypeOperation() == 1) {
                        resta = resta.add(p.getAmount());
                    }
                    total = suma.subtract(resta);
                    employee.setTotalSumPerception(suma);
                    employee.setTotalSumDeduction(resta);
                    employee.setTotal(total);
                    resultFilter.add(employee);
                }
            }
            perceptionsDeductionsService.reporUnfold(resultFilter,outputStream);
        } else if(idEmployee!=null){
            employee= employeesHistoryService.findIdEmployee(idEmployee);
            List<PerceptionsDeductions> perceptionsDeductions =  perceptionsDeductionsService.findByIdEmployee(idEmployee);
            for(PerceptionsDeductions p : perceptionsDeductions){
                CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                if(cp.getIdTypeOperation()==2){
                    suma = suma.add(p.getAmount());
                }else if(cp.getIdTypeOperation()==1){
                    resta = resta.add(p.getAmount());
                }
            }
            total = suma.subtract(resta);
            employee.setTotalSumPerception(suma);
            employee.setTotalSumDeduction(resta);
            employee.setTotal(total);
            resultFilter.add(employee);
            perceptionsDeductionsService.reporUnfold(resultFilter,outputStream);
        }else if(idDistributor!=null){
            employee=null;
            List <EmployeesHistory> employeesHist = employeesHistoryService.findByIdDistributor(idDistributor);
            for(EmployeesHistory e : employeesHist) {
                employee=e;
                List<PerceptionsDeductions> perceptionsForEmployee = perceptionsDeductionsService.findByIdEmployee(e.getIdEmployee());
                for(PerceptionsDeductions p: perceptionsForEmployee){
                    CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                    if(cp.getIdTypeOperation()==2){
                        suma = suma.add(p.getAmount());
                    }else if(cp.getIdTypeOperation()==1){
                        resta = resta.add(p.getAmount());
                    }
                }
                total = suma.subtract(resta);
                employee.setTotalSumPerception(suma);
                employee.setTotalSumDeduction(resta);
                employee.setTotal(total);
                resultFilter.add(employee);
            }
            perceptionsDeductionsService.reporUnfold(resultFilter,outputStream);
        }else if(ofDate!=null && untilDate!=null) {
            List<PerceptionsDeductions> range = perceptionsDeductionsService.findByStartDateEndDate(ofDate, untilDate);
            for (PerceptionsDeductions p : range) {
                employee = employeesHistoryService.findIdEmployee(p.getIdEmployee());
                CPerceptionsDeductions cp = cPerceptionsDeductionsService.findById(p.getIdCPd());
                if (cp.getIdTypeOperation() == 2) {
                    suma = suma.add(p.getAmount());
                } else if (cp.getIdTypeOperation() == 1) {
                    resta = resta.add(p.getAmount());
                }
                total = suma.subtract(resta);
                employee.setTotalSumPerception(suma);
                employee.setTotalSumDeduction(resta);
                employee.setTotal(total);
                resultFilter.add(employee);
            }
            perceptionsDeductionsService.reporUnfold(resultFilter, outputStream);
        }
        outputStream.flush();
        outputStream.close();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(resultFilter),HttpStatus.OK);
    }

    @RequestMapping(value = "/execute-report", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> executeSqlQueryReportPD(@RequestParam (name = "idEmployee",required = false)Integer idEmployee,
                                                          @RequestParam (name = "idDistributor", required = false) Integer idDistributor,
                                                          @RequestParam(name = "startDate", required = false) String startDate,
                                                          @RequestParam(name = "endDate", required = false) String endDate,
                                                          @RequestParam(name = "fileName",required = true)String fileName,
                                                          HttpServletResponse response)throws Exception{
        OutputStream outputStream = response.getOutputStream();
        SqlQueries query = sqlQueriesService.findQuery(11);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +".xlsx\"");
        if(idEmployee!=null && startDate!=null && endDate!=null) {
            List querys = sqlQueriesService.executeProcedurePD(query, idEmployee, 0, startDate, endDate);
            perceptionsDeductionsService.reportPD(outputStream,querys);
            outputStream.flush();
            outputStream.close();
            return  new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(querys),HttpStatus.OK);
        }
        if (idDistributor != null && startDate != null && endDate != null) {
            List querys = sqlQueriesService.executeProcedurePD(query, 0, idDistributor, startDate, endDate);
            perceptionsDeductionsService.reportPD(outputStream, querys);
            outputStream.flush();
            outputStream.close();
            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(querys), HttpStatus.OK);
        }
        if(idEmployee!=null){
            List querys = sqlQueriesService.executeProcedurePD(query, idEmployee,0, "0000-00-00", "0000-00-00");
            perceptionsDeductionsService.reportPD(outputStream,querys);
            outputStream.flush();
            outputStream.close();
            return  new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(querys),HttpStatus.OK);
        }
        if(idDistributor!=null){
            List querys = sqlQueriesService.executeProcedurePD(query, 0, idDistributor, "0000-00-00", "0000-00-00");
            perceptionsDeductionsService.reportPD(outputStream,querys);
            outputStream.flush();
            outputStream.close();
            return  new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(querys),HttpStatus.OK);
        }
        else {
            List querys = sqlQueriesService.executeProcedurePD(query, idEmployee, idDistributor, startDate, endDate);
            perceptionsDeductionsService.reportPD(outputStream,querys);
            outputStream.flush();
            outputStream.close();
            return  new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(querys),HttpStatus.OK);
        }
    }

}
