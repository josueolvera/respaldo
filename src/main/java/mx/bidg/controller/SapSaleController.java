package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private ObjectMapper mapper;

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

    @RequestMapping(value = "/prueba", method = RequestMethod.GET)
    public ResponseEntity<String> prueba(@RequestParam(name= "fromDate", required=true) String fromDate, @RequestParam(name="toDate", required=true) String toDate ) throws IOException {

        List<CommissionAmountGroup> all =commissionAmountGroupService.findAll();

        if (all.size() > 0 ){
            for (CommissionAmountGroup cAG : all){
                commissionAmountGroupService.delete(cAG);
            }
        }

        LocalDateTime ofDate = (fromDate == null || fromDate.equals("")) ? null :
                LocalDateTime.parse(fromDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (toDate == null || toDate.equals("")) ? null :
                LocalDateTime.parse(toDate, DateTimeFormatter.ISO_DATE_TIME);


        List<CalculationRoles> calculationRolesList = calculationRolesService.findAll();

        for (CalculationRoles role : calculationRolesList){
            //rol 1 Asesor de credito rol 2 Supervisor
            if (role.getIdCalculationRole() == 1 || role.getIdCalculationRole() == 2){
                List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                    List sapSales = sapSaleService.findByAgreementGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                    CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                    commissionAmountGroupService.obtainAmountsbyGroup(sapSales, agreementsGroups);

                    List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg());

                    agreementsGroupConditionService.setTabulator(agreementsGroupConditionList);
                }
                //rol 3 Auxiliar
            }else if (role.getIdCalculationRole() == 3){
                List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                    List sapSales = sapSaleService.findByBranchGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                    CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                    commissionAmountGroupService.obtainAmountsbyBranch(sapSales, agreementsGroups);

                    List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg());

                    agreementsGroupConditionService.setTabulator(agreementsGroupConditionList);
                }
                //rol 4 Gerente de Sucursal
            }else if (role.getIdCalculationRole() == 4){
                List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                    List sapSales = sapSaleService.findByBranchGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                    CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                    commissionAmountGroupService.obtainAmountsbyBranch(sapSales, agreementsGroups);

                    List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg());

                    agreementsGroupConditionService.setTabulator(agreementsGroupConditionList);
                }
                //rol 5 Gerente de zonal
            }else if (role.getIdCalculationRole() == 5){
                List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                    List sapSales = sapSaleService.findByZonaGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                    CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                    commissionAmountGroupService.obtainAmountsbyZona(sapSales, agreementsGroups);

                    List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg());

                    agreementsGroupConditionService.setTabulator(agreementsGroupConditionList);
                }
                //rol 6 Gerente de regional
            }else if (role.getIdCalculationRole() == 6){
                List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                    List sapSales = sapSaleService.findByRegionGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                    CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                    commissionAmountGroupService.obtainAmountsbyRegion(sapSales, agreementsGroups);

                    List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg());

                    agreementsGroupConditionService.setTabulator(agreementsGroupConditionList);
                }
                //rol 7 Director Comercial
            }else if (role.getIdCalculationRole() == 7){
                List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRole(role.getIdCalculationRole());
                for (RolesGroupAgreements groupAgreements : rolesGroupAgreementsList){
                    List sapSales = sapSaleService.findByDistributorGroup(groupAgreements.getIdAg(), ofDate, untilDate);

                    CAgreementsGroups agreementsGroups = cAgreementsGroupsService.findById(groupAgreements.getIdAg());

                    commissionAmountGroupService.obtainAmountsbyDistributor(sapSales, agreementsGroups);

                    List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.conditions(groupAgreements.getIdAg());

                    agreementsGroupConditionService.setTabulator(agreementsGroupConditionList);
                }
            }
        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionAmountGroupService.findAll()), HttpStatus.OK);
    }

}
