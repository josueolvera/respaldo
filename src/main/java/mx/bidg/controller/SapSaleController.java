package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gerardo8 on 29/04/16.
 */
@Controller
@RequestMapping("/sap-sale")
public class SapSaleController {
    @Autowired
    private SapSaleService sapSaleService;

    @Autowired
    private CommissionAmountGroupService commissionAmountGroupService;

    @Autowired
    private CAgreementsGroupsService cAgreementsGroupsService;

    @Autowired
    private AgreementsGroupConditionService agreementsGroupConditionService;

    @Autowired
    private CalculationRolesService calculationRolesService;

    @Autowired
    private  RolesGroupAgreementsService rolesGroupAgreementsService;

    @Autowired
    private  DwBranchsService dwBranchsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BonusCommisionableEmployeeService bonusCommisionableEmployeeService;

    @Autowired
    private MultilevelEmployeeService multilevelEmployeeService;

    @Autowired
    private EmployeesHistoryService employeesHistoryService;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findSapSales() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(sapSaleService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findSapSaleById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(sapSaleService.findById(id));
    }

    @RequestMapping(value = "/excel",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> saveExcelSapSale(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException, InvalidFormatException {
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sapSaleService.saveFromExcel(file)), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/update-excel",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> updateExcelSapSale(@RequestParam("file") MultipartFile file) throws Exception {

        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sapSaleService.updateFromExcel(file)), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/check-existing-sale",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<Boolean> checkExistingSale(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(sapSaleService.existsSales(file));
    }

    @RequestMapping(value = "/prueba/{idDateCalculation}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> prueba(@PathVariable Integer idDateCalculation
            , @RequestParam(name= "fromDate", required=true) String fromDate
            , @RequestParam(name="toDate", required=true) String toDate
            , @RequestParam(name="fromJoinDate", required=false) String fromJoinDate
            , @RequestParam(name="toJoinDate", required=false) String toJoinDate
            ) throws IOException {

        List<CommissionAmountGroup> all = commissionAmountGroupService.findAll();

        if (all.size() > 0 ){
            for (CommissionAmountGroup cAG : all){
                commissionAmountGroupService.delete(cAG);
            }
        }

        List<BonusCommisionableEmployee> bonusCommisionableEmployees = bonusCommisionableEmployeeService.findAll();

        if (!bonusCommisionableEmployees.isEmpty()){
            for (BonusCommisionableEmployee bCE : bonusCommisionableEmployees){
                bonusCommisionableEmployeeService.delete(bCE);
            }
        }

        LocalDateTime ofDate = (fromDate == null || fromDate.equals("")) ? null :
                LocalDateTime.parse(fromDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (toDate == null || toDate.equals("")) ? null :
                LocalDateTime.parse(toDate, DateTimeFormatter.ISO_DATE_TIME);

//        LocalDateTime ofJoinDate = (fromJoinDate == null || fromJoinDate.equals("")) ? null :
//                LocalDateTime.parse(fromJoinDate, DateTimeFormatter.ISO_DATE_TIME);
//        LocalDateTime untilJoinDate = (toJoinDate == null || toJoinDate.equals("")) ? null :
//                LocalDateTime.parse(toJoinDate, DateTimeFormatter.ISO_DATE_TIME);


        List<CalculationRoles> calculationRolesList = calculationRolesService.findAll();

        if(idDateCalculation == 1) {
            for (CalculationRoles role : calculationRolesList) {
                //rol 1 Asesor de credito
                if (role.getIdCalculationRole() == 1) {
                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                    for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList) {
                        List sapSales = sapSaleService.findByAgreementGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                        commissionAmountGroupService.obtainAmountsbyGroup(sapSales, agreementsGroups, ofDate, untilDate);

                        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg(), idDateCalculation);

                        agreementsGroupConditionService.setTabulator(agreementsGroupConditionList);

//                        if (groupAgreements.getIdAg() == 19) {
//                            agreementsGroupConditionService.bonusJoinDate(ofJoinDate, untilJoinDate);
//                        }
                    }
                }
//                else if (role.getIdCalculationRole() == 5) {
//                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
//                    for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList) {
//                        List sapSales = sapSaleService.findByZonaGroup(groupAgreements.getIdAg(), ofDate, untilDate);
//
//                        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());
//
//                        commissionAmountGroupService.obtainAmountsbyZona(sapSales, agreementsGroups, ofDate, untilDate);
//
//                        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg(), idDateCalculation);
//
//                        agreementsGroupConditionService.setTabulator(agreementsGroupConditionList);
//                    }
//                }
            }
        }else if (idDateCalculation == 2){
            for (CalculationRoles role : calculationRolesList){
                //rol 1 Asesor de credito
                if (role.getIdCalculationRole() == 1){
                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                    for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                        List sapSales = sapSaleService.findByAgreementGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                        commissionAmountGroupService.obtainAmountsbyGroup(sapSales, agreementsGroups, ofDate, untilDate);

                        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg(), idDateCalculation);

                        agreementsGroupConditionService.obtainCommissionByPromotor(agreementsGroupConditionList);
                    }
                    //rol 3 Auxiliar
                }else if (role.getIdCalculationRole() == 3){
                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                    for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                        List sapSales = sapSaleService.findByBranchGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                        commissionAmountGroupService.obtainAmountsbyAuxiliarGroup(sapSales, agreementsGroups , ofDate, untilDate);

                        List<CommissionAmountGroup> commissionAmountAuxiliarGroupList = commissionAmountGroupService.obtainAuxiliar();

                        for(CommissionAmountGroup commissionAmount : commissionAmountAuxiliarGroupList){
                            DwBranchs dwBranchs = dwBranchsService.findById(commissionAmount.getIdBranch());
                            commissionAmount.setIndexReprocessing(dwBranchs.getIndexReprocessing());
                            commissionAmountGroupService.update(commissionAmount);
                        }

                        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg(), idDateCalculation);

                        agreementsGroupConditionService.obtainCommissionByReprocessingToAuxiluiar(agreementsGroupConditionList);
                    }
                    //rol 4 Gerente de Sucursal
                }else if (role.getIdCalculationRole() == 4){
                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                    for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                        List sapSales = sapSaleService.findByBranchGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                        commissionAmountGroupService.obtainAmountsbyBranch(sapSales, agreementsGroups, ofDate, untilDate);

                        List<CommissionAmountGroup> commissionAmountGroupList = commissionAmountGroupService.obtainBranchManager();

                        for(CommissionAmountGroup commissionAmountGroup : commissionAmountGroupList){
                            DwBranchs dwBranchs = dwBranchsService.findById(commissionAmountGroup.getIdBranch());
                            commissionAmountGroup.setGoal(dwBranchs.getBranchGoal());
                            commissionAmountGroup.setPttoPromReal(dwBranchs.getPttoPromReal());
                            commissionAmountGroup.setPttoPromVta(dwBranchs.getPttoPromVta());
                            BigDecimal scope = commissionAmountGroup.getAmount().divide(dwBranchs.getBranchGoal(), 2, BigDecimal.ROUND_HALF_UP);
                            commissionAmountGroup.setScope(scope);
                            commissionAmountGroupService.update(commissionAmountGroup);
                        }

                        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg(), idDateCalculation);

                        agreementsGroupConditionService.obtainCommissionByGoalBranchToBranchManagaer(agreementsGroupConditionList);
                    }
                    //rol 5 Gerente de zonal
                }else if (role.getIdCalculationRole() == 5){
                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                    for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                        List sapSales = sapSaleService.findByZonaGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                        commissionAmountGroupService.obtainAmountsbyZona(sapSales, agreementsGroups, ofDate, untilDate);

                        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg(), idDateCalculation);

                        agreementsGroupConditionService.obtainCommissionByGoalBranchToZona(agreementsGroupConditionList);
                    }
                    //rol 6 Gerente de regional
                }else if (role.getIdCalculationRole() == 6){
                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                    for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                        List sapSales = sapSaleService.findByRegionGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                        commissionAmountGroupService.obtainAmountsbyRegion(sapSales, agreementsGroups, ofDate, untilDate);

                        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg(), idDateCalculation);

                        agreementsGroupConditionService.obtainCommissionByGoalBranchToRegion(agreementsGroupConditionList);
                    }
                    //rol 7 Director Comercial
                }else if (role.getIdCalculationRole() == 7){
                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                    for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                        List sapSales = sapSaleService.findByDistributorGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                        commissionAmountGroupService.obtainAmountsbyDistributor(sapSales, agreementsGroups, ofDate, untilDate);

                        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg(), idDateCalculation);

                        agreementsGroupConditionService.obtainCommissionByGoalBranchToDistributor(agreementsGroupConditionList);
                    }
                    //ROLE 8 supervisor
                }else if (role.getIdCalculationRole() == 8){
                    List<CommissionAmountGroup> commissionBranchGoal = commissionAmountGroupService.getBranchsWithScopeGoalAndTabulator();
                    List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());

                    for (CommissionAmountGroup commissionAmountGroup : commissionBranchGoal){
                        List<Integer> multilevelEmployees = multilevelEmployeeService.findByIdBranch(commissionAmountGroup.getIdBranch());
                        if (!multilevelEmployees.isEmpty()){
                            for (Integer idES : multilevelEmployees){

                                List sapSales = sapSaleService.findBySupervisorAndRleGroup(idES, rolesGroupAgreementsList.get(0).getIdAg(), ofDate, untilDate);

                                if (!sapSales.isEmpty()){
                                    CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(rolesGroupAgreementsList.get(0).getIdAg());
                                    commissionAmountGroupService.obtainAmountsbySupervisor(sapSales, agreementsGroups, ofDate, untilDate);
                                }else{
                                    CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(rolesGroupAgreementsList.get(0).getIdAg());
                                    EmployeesHistory employeesHistory = employeesHistoryService.findByIdEmployeeAndLastRegister(idES);

                                    CommissionAmountGroup amountGroup = new CommissionAmountGroup();
                                    amountGroup.setAmount(new BigDecimal(0));
                                    amountGroup.setCommission(new BigDecimal(0));
                                    amountGroup.setTabulator(new BigDecimal(0));
                                    amountGroup.setApplicationsNumber(new BigDecimal(0));
                                    amountGroup.setClaveSap(employeesHistory.getClaveSap());
                                    amountGroup.setIdEmployee(employeesHistory.getIdEmployee());
                                    amountGroup.setIdRole(employeesHistory.getIdRole());
                                    amountGroup.setIdAg(agreementsGroups.getIdAg());
                                    amountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
                                    amountGroup.setFromDate(ofDate);
                                    amountGroup.setToDate(untilDate);

                                    commissionAmountGroupService.save(amountGroup);
                                }

                                List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(rolesGroupAgreementsList.get(0).getIdAg(), idDateCalculation);
                                agreementsGroupConditionService.obtainCommissionBySupervisor(agreementsGroupConditionList);
                            }
                        }
                    }
                }
            }
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionAmountGroupService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(value = "/send-to-validation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> sendSalesToValidation(@RequestBody String data)throws IOException{
        JsonNode node = mapper.readTree(data);

        EmailTemplates template = emailTemplatesService.findByName("validation_sales");
        template.addProperty("initial", node.get("initial").asText());
        template.addProperty("final", node.get("final").asText());
        emailDeliveryService.deliverEmail(template);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(template), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAllSaleStatus() throws IOException{
        List<String> saleStatus = sapSaleService.getAllSaleStatus();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(saleStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-sales", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getSalesByStatus(@RequestBody String data) throws Exception {
        JsonNode node = mapper.readTree(data);

        List<String> statusSelected = new ArrayList<>();

        for (JsonNode jNode :  node.get("selectedStatus")){
            statusSelected.add(jNode.asText());
        }

        List<SapSale> sapSales = sapSaleService.findAllSalesByStatusAndDates(statusSelected, node.get("startDate").asText(), node.get("endDate").asText());

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sapSales), HttpStatus.OK);

    }

    @RequestMapping(value = "/assign-employee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> assingEmployee(@RequestBody String data) throws IOException{

        JsonNode node = mapper.readTree(data);
        SapSale sapSale = sapSaleService.assignSaleToEmployee(node.get("idSale").asText(), node.get("claveSap").asText());

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sapSale), HttpStatus.OK);
    }

    @RequestMapping(value = "/reject-sales", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> rejectSales(@RequestBody String data, HttpSession session)throws IOException{
        JsonNode node = mapper.readTree(data);

        Users user = (Users) session.getAttribute("user");

        EmailTemplates template = emailTemplatesService.findByName("reject_sales");
        template.addProperty("reason", node.get("reason").asText());
        template.addProperty("initialDate", node.get("startDate").asText());
        template.addProperty("finalDate", node.get("endDate").asText());
        template.addProperty("users", user);
        emailDeliveryService.deliverEmail(template);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(template), HttpStatus.OK);
    }
}
