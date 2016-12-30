package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Rafael Viveros
 * Created on 21/06/16.
 */
@Controller
@RequestMapping("sql-queries")
public class SqlQueriesController {

    @Autowired
    private SqlQueriesService sqlQueriesService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Environment env;

    @Autowired
    private CalculationReportService calculationReportService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private PayrollService payrollService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<SqlQueries> sqlQueries = sqlQueriesService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sqlQueries),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        SqlQueries query = new SqlQueries();
        query.setQueryName(node.get("queryName").asText());
        query.setSqlQuery(node.get("sqlQuery").asText());
        query.setHeaders(node.get("headers").asText());
        query.setSaved(Boolean.TRUE);
        sqlQueriesService.save(query);

        Set<SqlQueryParameters> queryParameters = new HashSet<>();
        for (JsonNode queryNode : node.get("queryParameters")) {
            SqlQueryParameters param = new SqlQueryParameters();
            param.setDataTypes(CDataTypes.VARCHAR);
            param.setParameterName(queryNode.get("parameterName").asText());
            param.setParameterValue(queryNode.get("parameterValue").asText());
            param.setSqlQuery(query);
            queryParameters.add(param);
        }
        query.setQueryParameters(queryParameters);

        
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(query),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idSqlQuery}/build", method = RequestMethod.GET)
    public void buildSqlQueryCSV(
            @PathVariable int idSqlQuery, @RequestParam(name = "file_name", required = true) String fileName,
            HttpServletResponse response
    ) throws Exception {
        OutputStream outputStream = response.getOutputStream();
        SqlQueries query = sqlQueriesService.findById(idSqlQuery);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +".csv\"");

        sqlQueriesService.buildCSVfrom(query, outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping(value = "/querys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> querys() throws IOException{
        List<SqlQueries> querys = sqlQueriesService.findByAllWithFlag();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(querys), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idQuery}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> query(@PathVariable Integer idQuery) throws IOException{
        SqlQueries query = sqlQueriesService.findQuery(idQuery);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(query), HttpStatus.OK);
    }

    @RequestMapping(value = "/execute/{idSqlQuery}", method = RequestMethod.GET)
    public ResponseEntity<String> executeSqlQuery(
            @PathVariable int idSqlQuery, @RequestParam(name = "file_name", required = true) String fileName
            , @RequestParam(name = "startDate", required = true) String startDate
            , @RequestParam(name = "endDate", required = true) String endDate
            , @RequestParam(name = "applicationDate1", required = true) String applicationDate1
            , @RequestParam(name = "applicationDate2", required = true) String applicationDate2
            , @RequestParam(name = "file_name_nec", required = true) String fileNameNec
            , HttpServletResponse response
            , HttpSession session
    ) throws Exception {

        Users user = (Users) session.getAttribute("user");

        LocalDateTime applicationDateStart = (applicationDate1 == null || applicationDate1.equals("")) ? null :
                LocalDateTime.parse(applicationDate1, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime applicationDateEnd = (applicationDate2 == null || applicationDate2.equals("")) ? null :
                LocalDateTime.parse(applicationDate2, DateTimeFormatter.ISO_DATE_TIME);

        Date endDates = Date.from(applicationDateEnd.atZone(ZoneId.systemDefault()).toInstant());

        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTime(endDates);
        int week = cal.get(Calendar.WEEK_OF_YEAR);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);



        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDates);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String SAVE_PATH = env.getRequiredProperty("report_commission.documents_dir");

        File dir = new File(SAVE_PATH);
        if (! dir.exists()) {
            dir.mkdir();
        }

        String destinationFile = SAVE_PATH+fileName+".xlsx";
        String destinationFileNec = SAVE_PATH+fileNameNec+".xlsx";

        List querys;

        SqlQueries report = sqlQueriesService.findQuery(idSqlQuery);
        report.setCalculate(0);
        report = sqlQueriesService.update(report);

        CalculationReport calculationReport = new CalculationReport();
        CalculationReport calculationReportNec = new CalculationReport();

        calculationReport.setFileName(fileName);
        calculationReport.setFilePath(destinationFile);
        calculationReport.setCalculationDate(applicationDateEnd);
        calculationReport.setUsername(user.getUsername());
        calculationReport.setStatus(0);
        calculationReport.setSqlQueries(report);
        calculationReport.setShowWindow(1);
        calculationReport.setSend(0);
        calculationReportService.save(calculationReport);

        calculationReportNec.setFileName(fileNameNec);
        calculationReportNec.setFilePath(destinationFileNec);
        calculationReportNec.setCalculationDate(applicationDateEnd);
        calculationReportNec.setUsername(user.getUsername());
        calculationReportNec.setStatus(0);
        calculationReportNec.setSqlQueries(report);
        calculationReportNec.setShowWindow(2);
        calculationReportNec.setSend(0);
        calculationReportService.save(calculationReportNec);

        FileOutputStream fileOutputStream = new FileOutputStream(new File(destinationFile));
        FileOutputStream fileOutputStreamNec = new FileOutputStream(new File(destinationFileNec));

        OutputStream outputStream = response.getOutputStream();
        SqlQueries query = sqlQueriesService.findById(idSqlQuery);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +".xlsx\"");

        querys = sqlQueriesService.executeAPocedureFrom(query, outputStream, startDate, endDate, applicationDateStart, applicationDateEnd, format.format(calendar.getTime()), fileOutputStream, fileOutputStreamNec);

        fileOutputStream.close();
        fileOutputStreamNec.close();
        outputStream.flush();
        outputStream.close();

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(querys), HttpStatus.OK);
    }

    @RequestMapping(value= "/notification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> sendNotification(@RequestParam(name = "file_name", required = true) String fileName
            ,@RequestParam(name = "file_name_nec", required = true) String fileNameNec)throws IOException{

        CalculationReport calculationReport = calculationReportService.findByName(fileName);
        calculationReport.setSend(1);
        calculationReport = calculationReportService.update(calculationReport);

        CalculationReport calculationReportNec = calculationReportService.findByName(fileNameNec);
        calculationReportNec.setSend(1);
        calculationReportNec = calculationReportService.update(calculationReportNec);

        if (calculationReport != null){
            if (calculationReport.getIdQuery() == 1){
                EmailTemplates emailTemplate = emailTemplatesService.findByName("calculation_report_roster_corp");
                emailTemplate.addProperty("calculationReport", calculationReport);

                emailDeliveryService.deliverEmail(emailTemplate);
            }else{
                EmailTemplates emailTemplate = emailTemplatesService.findByName("calculation_report_roster_distribution");
                emailTemplate.addProperty("calculationReport", calculationReport);

                emailDeliveryService.deliverEmail(emailTemplate);
            }
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-reports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteReports(@RequestParam(name = "file_name", required = true) String fileName
            , @RequestParam(name = "file_name_nec", required = true) String fileNameNec) throws IOException{

        List<CalculationReport> calculationReportList = calculationReportService.deleteReportsAndRegister(fileName,fileNameNec);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(calculationReportList), HttpStatus.OK);
    }

    @RequestMapping(value = "/report-cost", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity<String>getTypeReportCost()throws Exception{
        List<SqlQueries> list= sqlQueriesService.findByReportCost();
        return new ResponseEntity<String>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list),HttpStatus.OK);
    }

    @RequestMapping(value = "/execute-report/{idSqlQuery}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> executeSqlQueryReportCost(
            @PathVariable int idSqlQuery
            ,@RequestParam(name = "fileName",required = true)String fileName
            ,@RequestParam(name = "startDate", required = true) String startDate
            ,@RequestParam(name = "endDate", required = true) String endDate
            , HttpServletResponse response
            ) throws Exception{

        OutputStream outputStream = response.getOutputStream();
        SqlQueries query = sqlQueriesService.findQuery(idSqlQuery);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +".xlsx\"");

        List querys = sqlQueriesService.executeProcedureReportCost(query,startDate,endDate,"0000-00-00");

        payrollService.reportCost(outputStream, querys);
        outputStream.flush();
        outputStream.close();
        return new ResponseEntity<String>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(querys),HttpStatus.OK);
    }
}
