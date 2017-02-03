package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.pojos.RejectReportPojo;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Desarrollador on 16/12/2016.
 */
@Controller
@RequestMapping("authorization-reports")
public class AuthorizationReportsController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AuthorizationReportsService authorizationReportsService;

    @Autowired
    SqlQueriesService sqlQueriesService;

    @Autowired
    CalculationReportService calculationReportService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    CDistributorsService cDistributorsService;

    @Autowired
    EmployeesHistoryService employeesHistoryService;

    @Autowired
    PerceptionsDeductionsService perceptionsDeductionsService;

    @Autowired
    OutsourcingService outsourcingService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll () throws IOException{
        List<AuthorizationReports> authorizationReportsList = authorizationReportsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationReportsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> rejectReport(@RequestBody String data) throws IOException{

        JsonNode jNode = mapper.readTree(data);

        List<AuthorizationReports> authorizationReportsList = authorizationReportsService.findByIdSqlQuery(jNode.get("reportRole").get("idQuery").asInt());

        AuthorizationReports authorizationReports = authorizationReportsService.findByIdRoleAndIdQuery(jNode.get("reportRole").get("idRole").asInt(), jNode.get("reportRole").get("idQuery").asInt());

        for (AuthorizationReports authorizationReport : authorizationReportsList){
            authorizationReport.setAuthorization(0);
            authorizationReportsService.update(authorizationReport);
        }

        String fileName = jNode.get("reportRole").get("calculationReport").get("fileName").asText();
        String fileNameNec = jNode.get("reportRole").get("calculationReport").get("fileName").asText()+"_NEC";

        CalculationReport calculationReport = calculationReportService.findByName(fileName);

        calculationReportService.deleteReportsAndRegister(fileName, fileNameNec);

        RejectReportPojo rejectReportPojo = new RejectReportPojo();

        rejectReportPojo.setCalculationReport(calculationReport);
        rejectReportPojo.setAuthorizationReports(authorizationReports);
        rejectReportPojo.setRejectReason(jNode.get("reason").asText());

        if (jNode.get("reportRole").get("calculationReport").get("idQuery").asInt() == 1){
            EmailTemplates emailTemplate = emailTemplatesService.findByName("reject_report_roster_corp");
            emailTemplate.addProperty("rejectReportPojo", rejectReportPojo);

            emailDeliveryService.deliverEmail(emailTemplate);
        }else{
            EmailTemplates emailTemplate = emailTemplatesService.findByName("reject_report_roster_distribution");
            emailTemplate.addProperty("rejectReportPojo", rejectReportPojo);

            emailDeliveryService.deliverEmail(emailTemplate);
        }

        List<AuthorizationReports> authorizationList = authorizationReportsService.findAllFlagsWithReportsNotAuthorized();

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationList), HttpStatus.OK);
    }

    @RequestMapping( value = "/authorize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> authorize(@RequestBody String data)throws IOException{

        JsonNode jnode = mapper.readTree(data);

        AuthorizationReports authorizationReports = authorizationReportsService.findByIdRoleAndIdQuery(jnode.get("reportRole").get("idRole").asInt(), jnode.get("reportRole").get("idQuery").asInt());
        authorizationReports.setAuthorization(1);
        authorizationReports.setAuthorizationDate(LocalDateTime.now());
        authorizationReports = authorizationReportsService.update(authorizationReports);

        List<AuthorizationReports> allPersonsByAuthorized = authorizationReportsService.findByIdSqlQuery(jnode.get("reportRole").get("idQuery").asInt());
        List<AuthorizationReports> authorizedPerson = authorizationReportsService.findByIdQueryAndAuthorized(jnode.get("reportRole").get("idQuery").asInt());

        if (!allPersonsByAuthorized.isEmpty()){
            if (!authorizedPerson.isEmpty()){
                int sizeAll = allPersonsByAuthorized.size()-1;
                int sizePersonAuthorized = authorizedPerson.size();

                String fileNames = jnode.get("reportRole").get("calculationReport").get("fileName").asText();
                CalculationReport report = calculationReportService.findByName(fileNames);
                authorizationReports.setCalculationReport(report);

                if (sizePersonAuthorized <= sizeAll){

                    if (authorizationReports.getIdQuery() == 1){
                        EmailTemplates emailTemplate = emailTemplatesService.findByName("authiorize_manager_report_corp");
                        emailTemplate.addProperty("authorizationReports", authorizationReports);

                        emailDeliveryService.deliverEmail(emailTemplate);
                    }else{
                        EmailTemplates emailTemplate = emailTemplatesService.findByName("authiorize_manager_report");
                        emailTemplate.addProperty("authorizationReports", authorizationReports);

                        emailDeliveryService.deliverEmail(emailTemplate);
                    }
                }else {
                    String fileName = jnode.get("reportRole").get("calculationReport").get("fileName").asText();
                    String fileNameNec = jnode.get("reportRole").get("calculationReport").get("fileName").asText()+"_NEC";

                    CalculationReport calculationReport = calculationReportService.findByName(fileName);
                    CalculationReport calculationReportNec = calculationReportService.findByName(fileNameNec);

                    calculationReport.setStatus(1);
                    calculationReport = calculationReportService.update(calculationReport);

                    calculationReportNec.setStatus(1);
                    calculationReportNec = calculationReportService.update(calculationReportNec);

                    SqlQueries sqlQuery = sqlQueriesService.findQuery(jnode.get("reportRole").get("idQuery").asInt());
                    sqlQuery.setCalculate(1);
                    sqlQuery =  sqlQueriesService.update(sqlQuery);

                    if (calculationReport.getStatus() == 1){
                        if (calculationReport.getIdQuery() == 1){
                            List<CDistributors> cDistributorsList = cDistributorsService.getDistributorForSaem(null, false);
                            if (!cDistributorsList.isEmpty()){
                                List<EmployeesHistory> employeesHistories = employeesHistoryService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(0,cDistributorsList,null,null,null,null,null,null,null,null,null,null);
                                if (!employeesHistories.isEmpty()){
                                    List<PerceptionsDeductions> perceptionsDeductionsList = perceptionsDeductionsService.findByAllEmployeesAndInitialDateAndFinalDate(employeesHistories, calculationReport.getFinalDate(), calculationReport.getFinalDate());
                                    if (!perceptionsDeductionsList.isEmpty()){
                                        for (PerceptionsDeductions perceptionsDeductions : perceptionsDeductionsList){
                                            perceptionsDeductions.setStatus(false);
                                            perceptionsDeductionsService.update(perceptionsDeductions);
                                        }
                                    }
                                    List<Outsourcing> outsourcingList = outsourcingService.findByAllEmployeesAndApplicationDate(employeesHistories, calculationReport.getFinalDate(), calculationReport.getFinalDate());
                                    if (!outsourcingList.isEmpty()){
                                        for (Outsourcing outsourcing : outsourcingList){
                                            outsourcing.setStatus(0);
                                            outsourcingService.update(outsourcing);
                                        }
                                    }
                                }
                            }
                        }else{
                            List<CDistributors> distribucionList = cDistributorsService.getDistributorForSaem(null, true);
                            if (!distribucionList.isEmpty()){
                                List<EmployeesHistory> employeesHistories = employeesHistoryService.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(0,distribucionList,null,null,null,null,null,null,null,null,null,null);
                                if (!employeesHistories.isEmpty()){
                                    List<PerceptionsDeductions> perceptionsDeductionsList = perceptionsDeductionsService.findByAllEmployeesAndInitialDateAndFinalDate(employeesHistories, calculationReport.getFinalDate(), calculationReport.getFinalDate());
                                    if (!perceptionsDeductionsList.isEmpty()){
                                        for (PerceptionsDeductions perceptionsDeductions : perceptionsDeductionsList){
                                            perceptionsDeductions.setStatus(false);
                                            perceptionsDeductionsService.update(perceptionsDeductions);
                                        }
                                    }
                                    List<Outsourcing> outsourcingList = outsourcingService.findByAllEmployeesAndApplicationDate(employeesHistories, calculationReport.getFinalDate(), calculationReport.getFinalDate());
                                    if (!outsourcingList.isEmpty()){
                                        for (Outsourcing outsourcing : outsourcingList){
                                            outsourcing.setStatus(0);
                                            outsourcingService.update(outsourcing);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    for (AuthorizationReports authorizationReport : allPersonsByAuthorized){
                        authorizationReport.setAuthorization(0);
                        authorizationReportsService.update(authorizationReport);
                    }
                    if (authorizationReports.getIdQuery() == 1){
                        EmailTemplates emailTemplate = emailTemplatesService.findByName("authiorized_report_corp");
                        emailTemplate.addProperty("authorizationReports", authorizationReports);

                        emailDeliveryService.deliverEmail(emailTemplate);
                    }else{
                        EmailTemplates emailTemplate = emailTemplatesService.findByName("authiorized_report");
                        emailTemplate.addProperty("authorizationReports", authorizationReports);

                        emailDeliveryService.deliverEmail(emailTemplate);
                    }
                }
            }
        }

        List<AuthorizationReports> authorizationList = authorizationReportsService.findAllFlagsWithReportsNotAuthorized();

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationList), HttpStatus.OK);
    }
}
