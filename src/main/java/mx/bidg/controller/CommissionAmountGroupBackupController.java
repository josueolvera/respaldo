package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CommissionAmountGroup;
import mx.bidg.model.CommissionAmountGroupBackup;
import mx.bidg.service.CommissionAmountGroupBackupService;
import mx.bidg.service.CommissionAmountGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by josueolvera on 22/09/16.
 */
@Controller
@RequestMapping("backup-commission")
public class CommissionAmountGroupBackupController {

    @Autowired
    CommissionAmountGroupService commissionAmountGroupService;

    @Autowired
    CommissionAmountGroupBackupService commissionAmountGroupBackupService;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CommissionAmountGroupBackup> commissionAmountGroupBackupList = commissionAmountGroupBackupService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionAmountGroupBackupList), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> beginBackup() throws IOException{
        List<CommissionAmountGroup> commissionAmountGroupList = commissionAmountGroupService.findAll();

        for(CommissionAmountGroup commissionAmountGroup : commissionAmountGroupList){
            CommissionAmountGroupBackup commissionAmountGroupBackup = new CommissionAmountGroupBackup();
            commissionAmountGroupBackup.setIdBranch(commissionAmountGroup.getIdBranch());
            commissionAmountGroupBackup.setIdDistributor(commissionAmountGroup.getIdDistributor());
            commissionAmountGroupBackup.setApplicationsNumber(commissionAmountGroup.getApplicationsNumber());
            commissionAmountGroupBackup.setAmount(commissionAmountGroup.getAmount());
            commissionAmountGroupBackup.setClaveSap(commissionAmountGroup.getClaveSap());
            commissionAmountGroupBackup.setCommission(commissionAmountGroup.getCommission());
            commissionAmountGroupBackup.setFromDate(commissionAmountGroup.getFromDate());
            commissionAmountGroupBackup.setToDate(commissionAmountGroup.getToDate());
            commissionAmountGroupBackup.setGoal(commissionAmountGroup.getGoal());
            commissionAmountGroupBackup.setGroupName(commissionAmountGroup.getGroupName());
            commissionAmountGroupBackup.setIdAg(commissionAmountGroup.getIdAg());
            commissionAmountGroupBackup.setIdComissionAmountGroupBackup(commissionAmountGroup.getIdComissionAmountGroup());
            commissionAmountGroupBackup.setIdRegion(commissionAmountGroup.getIdRegion());
            commissionAmountGroupBackup.setIdZona(commissionAmountGroup.getIdZona());
            commissionAmountGroupBackup.setIndexReprocessing(commissionAmountGroup.getIndexReprocessing());
            commissionAmountGroupBackup.setPttoPromReal(commissionAmountGroup.getPttoPromReal());
            commissionAmountGroupBackup.setPttoPromVta(commissionAmountGroup.getPttoPromVta());
            commissionAmountGroupBackup.setScope(commissionAmountGroup.getScope());
            commissionAmountGroupBackup.setTabulator(commissionAmountGroup.getTabulator());
            commissionAmountGroupBackup.setIdRole(commissionAmountGroup.getIdRole());
            commissionAmountGroupBackup.setIdEmployee(commissionAmountGroup.getIdEmployee());
            commissionAmountGroupBackup.setTabulator(commissionAmountGroup.getTabulator());
            commissionAmountGroupBackup.setBonusCommissionableAmount(commissionAmountGroup.getBonusCommissionableAmount());
            commissionAmountGroupBackup.setAdjustment(commissionAmountGroup.getAdjustment());

            commissionAmountGroupBackupService.save(commissionAmountGroupBackup);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionAmountGroupBackupService.findAll()), HttpStatus.OK);
    }
}
