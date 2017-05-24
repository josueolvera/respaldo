package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CCostCenter;
import mx.bidg.model.CDistributors;
import mx.bidg.model.DistributorCostCenter;
import mx.bidg.pojos.BussinessLine;
import mx.bidg.service.*;
import mx.bidg.utils.BudgetHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desarrollador on 25/03/2017.
 */
@Controller
@RequestMapping("distributor-cost-center")
public class DistributorCostCenterController {

    @Autowired
    private DistributorCostCenterService distributorCostCenterService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CDistributorsService cDistributorsService;

    @Autowired
    private CCostCenterService cCostCenterService;

    @Autowired
    private BudgetHelper budgetHelper;

    @Autowired
    private BudgetsService budgetsService;

    @Autowired
    private Environment env;

    @Autowired
    private AccountingAccountsService accountingAccountsService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<DistributorCostCenter> distributorCostCenterList = distributorCostCenterService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorCostCenterList), HttpStatus.OK);
    }

    @RequestMapping(value = "/bussiness-line/{idBussinessLine}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findIdsDistributorsByBussinessLine(@PathVariable Integer idBussinessLine) throws IOException{
        List<Integer> idsDistributors =  distributorCostCenterService.getIdsDistributorByBusinessLine(idBussinessLine);
        List<CDistributors> distributorsList = new ArrayList<>();
        for (Integer idDistributor : idsDistributors){
            CDistributors distributor = cDistributorsService.findById(idDistributor);
            distributorsList.add(distributor);
        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/b-distributor/{idDistributor}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByIdsCostCenterByBDistributor(@PathVariable Integer idDistributor, @RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);

        List<Integer> idsBussinessLines = new ArrayList<>();
        for (JsonNode jsonNode : node.get("bussinessLinesSelected")){
            Integer idBussinessLine = jsonNode.get("idBusinessLine").asInt();
            idsBussinessLines.add(idBussinessLine);
        }

        List<Integer> idsCostCenters = distributorCostCenterService.getIdsCostCentersByBDistributor(idDistributor, idsBussinessLines);
        List<CCostCenter> costCenterList = new ArrayList<>();
        for (Integer idCostCenter :  idsCostCenters){
            CCostCenter costCenter = cCostCenterService.findById(idCostCenter);
            costCenterList.add(costCenter);
        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(costCenterList), HttpStatus.OK);
    }

    @RequestMapping(value ="/prueba", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> prueba (@RequestBody String data)throws IOException{


        JsonNode node = mapper.readTree(data);

        List<Integer> idsBussinessLines = new ArrayList<>();
        List<Integer> idsDistributors = new ArrayList<>();
        List<Integer> idsCCs = new ArrayList<>();


        for (JsonNode jsonNode : node.get("bussinessLinesSelected")){
            Integer idBussinessLine = jsonNode.get("idBusinessLine").asInt();
            idsBussinessLines.add(idBussinessLine);
        }

        for (JsonNode jsonNode : node.get("costCenterSelected")){
            Integer idCostCenter = jsonNode.get("idCostCenter").asInt();
            idsCCs.add(idCostCenter);
        }

        for (JsonNode jsonNode : node.get("distributorsSelected")){
            Integer idDistributor = jsonNode.get("idDistributor").asInt();
            idsDistributors.add(idDistributor);
        }

        List<BussinessLine> bussinessLines = budgetHelper.prueba2(idsBussinessLines, idsDistributors, idsCCs, node.get("year").asInt());

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(bussinessLines), HttpStatus.OK);
    }

    @RequestMapping(value = "/report", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> getBudgetByCategory(@RequestBody String data) throws Exception {

        JsonNode node = mapper.readTree(data);

        List<Integer> idsBussinessLines = new ArrayList<>();
        List<Integer> idsDistributors = new ArrayList<>();
        List<Integer> idsCCs = new ArrayList<>();


        for (JsonNode jsonNode : node.get("bussinessLinesSelected")){
            Integer idBussinessLine = jsonNode.get("idBusinessLine").asInt();
            idsBussinessLines.add(idBussinessLine);
        }

        for (JsonNode jsonNode : node.get("costCenterSelected")){
            Integer idCostCenter = jsonNode.get("idCostCenter").asInt();
            idsCCs.add(idCostCenter);
        }

        for (JsonNode jsonNode : node.get("distributorsSelected")){
            Integer idDistributor = jsonNode.get("idDistributor").asInt();
            idsDistributors.add(idDistributor);
        }

        List<BussinessLine> bussinessLines = budgetHelper.prueba2(idsBussinessLines, idsDistributors, idsCCs, node.get("year").asInt());

        String SAVE_PATH = env.getRequiredProperty("budget_temporal.documents_dir");

        File dir = new File(SAVE_PATH);
        if (! dir.exists()) {
            dir.mkdir();
        }

        File fileExists = new File(SAVE_PATH + node.get("reportName").asText());

        if (fileExists.exists()){
            fileExists.delete();
        }

        FileOutputStream outputStream = new FileOutputStream(SAVE_PATH + node.get("reportName").asText());

        budgetsService.createReport(bussinessLines, node.get("year").asInt(), outputStream);
        outputStream.close();

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(SAVE_PATH + node.get("reportName").asText()));
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void download(
            @RequestParam (name = "name", required = true) String name,
            HttpServletResponse response
    ) throws Exception {

        File file = new File(name);

        if (! file.canRead()) {
            throw new ValidationException(
                    "El archivo "+ name +" no existe",
                    "El documento solicitado no existe"
            );
        }

        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() +".xlsx" + "\"");
        response.setContentLengthLong(file.length());

        byte[] buffer = new  byte[1024];
        int len;

        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @RequestMapping(value = "/delete-report", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteReport(
            @RequestParam (name = "name", required = true) String name) throws Exception {
        File file = new File(name);

        if (file.exists()){
            file.delete();
        }
    }

    @RequestMapping(value = "/cost-center/{idCostCenter}/{idModuleStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findIdsAccountingAccountsByCostCenterAndModuleStatus(@PathVariable Integer idCostCenter,@PathVariable Integer idModuleStatus) throws IOException{
        List<Integer> idsAccountingAccounts =  distributorCostCenterService.getIdsAccountingAccountsByCostCenterAndModuleStatus(idCostCenter, idModuleStatus);
        List<AccountingAccounts> accountingAccountsList = new ArrayList<>();
        for (Integer idAccountingAccount : idsAccountingAccounts){
            AccountingAccounts accountingAccount = accountingAccountsService.findById(idAccountingAccount);
            accountingAccountsList.add(accountingAccount);
        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccountsList), HttpStatus.OK);
    }
}
